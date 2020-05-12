package ru.ruslan.events;


import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class HelloEvent extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent e){
        String[] args = e.getMessage().getContentRaw().split(" ");
        String name = e.getMember().getUser().getName(); //Get the name of the user who sent the message
        if(args[0].equalsIgnoreCase("hi")){
            if(!e.getMember().getUser().isBot()){ //Checks to see if the user who triggered the event is a bot or not. This prevents an endless loop of the message hi being sent.
                e.getChannel().sendMessage("hi " + name).queue(); //Say hi plus their name
            }
        }
    }
}