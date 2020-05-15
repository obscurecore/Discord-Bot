package ru.ruslan;

import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.ruslan.command.Command;

import java.util.*;

@SpringBootApplication
public class BotApplication {
    /**
     * A way to have all our commands in one place
     * so we can choose which one we want to fire for any given MessageCreateEvent
     * keys represent the name of the command, with the values being Command instances.
     */

    //Hardcode add behaviour
//    private static final Map<String, Command> commands = new HashMap<>();
//    static {
//        commands.put("ping", event -> event.getMessage().getChannel()
//                .flatMap(channel -> channel.createMessage("Pong!"))
//                .then());
//    }

    public static void main(String[] args) {

        // Get all actions in project
        ApplicationContext context = new ClassPathXmlApplicationContext("Command_config.xml");
        final Map<String, Command> commands = (Map) context.getBean("command_map");

        /**
         * In order to make bot appear online it has to login
         * login - returns Mono, which is an asynchronous and lazy
         * block - essentially means "request this action and wait for it to do finish"
         */
        final DiscordClient client = new DiscordClientBuilder(args[0]).build();

        //Reactive approach
        client.getEventDispatcher().on(MessageCreateEvent.class)
                .flatMap(event -> Mono.justOrEmpty(event.getMessage().getContent())
                        .flatMap(content -> Flux.fromIterable(commands.entrySet())
                                //Using ! as "prefix" to any command in the system.
                                .filter(entry -> content.startsWith("!"+entry.getKey()))
                                .flatMap(entry -> entry.getValue().execute(event))
                                .next()))
                .subscribe();



        client.login().block();

    }
}