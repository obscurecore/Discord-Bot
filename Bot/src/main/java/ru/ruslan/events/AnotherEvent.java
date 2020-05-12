package ru.ruslan.events;


import net.dv8tion.jda.api.events.channel.category.CategoryCreateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AnotherEvent extends ListenerAdapter {
 
    //An event for everything
    public void onCategoryCreate(CategoryCreateEvent e){
        e.getGuild().getDefaultChannel().sendMessage("wot").queue();
    }
 
}