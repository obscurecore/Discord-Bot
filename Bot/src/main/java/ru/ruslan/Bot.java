package ru.ruslan;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import ru.ruslan.events.AnotherEvent;
import ru.ruslan.events.HelloEvent;

public class Bot {

    public static void main(String args[]) throws Exception{
        String token = " ";
        JDA jda = new JDABuilder(token).build();
        jda.addEventListener(new HelloEvent());
        jda.addEventListener(new AnotherEvent());

    }

}