package ru.ruslan.command;
import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

public class Ping implements Command {
    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
       return event.getMessage().getChannel()
                .flatMap(channel -> channel.createMessage("Pong!")).then();
    }
}

