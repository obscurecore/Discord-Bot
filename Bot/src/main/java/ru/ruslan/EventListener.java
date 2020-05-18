package ru.ruslan;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.EnumUtils;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.ChannelDeleteEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class EventListener {
    /**
     * Registers a listener
     * All events sent to this listener will be done asynchronously using a default thread pool configured
     */
    @EventSubscriber
    public void onMessageReceived(final MessageReceivedEvent e) {
        final String msg = e.getMessage().getContent();
        if (msg.startsWith("!cew")) {
            final String[] args = msg.split(" ");
            if (args.length <= 1)
                BotUtils.reply(e, "what！");
            else {
               final Command command = EnumUtils.getEnum(Command.class, args[1]);
                if (command != null)
                	command.onCommand(e, ArrayUtils.subarray(args, 2, args.length+1));
            }
        }
    }


    @EventSubscriber
    public void onChannelDelete(final ChannelDeleteEvent e) {
        final Collection<Channel> channels = CEWBot.channels.get(e.getChannel().getGuild().getLongID());
        if (channels != null) {
            final long id = e.getChannel().getLongID();
            channels.removeIf(channel -> channel.getId() == id);
            try {
                CEWBot.saveConfigs();
            } catch (final ConfigException ex) {
                CEWBot.LOGGER.error("Error on channel delete", ex);
            }
        }
    }


    public static enum Command {
        register {
            @Override
            public void onCommand(final MessageReceivedEvent e, final String[] args) {
                final long serverid = e.getGuild().getLongID();
                final long channelid = e.getChannel().getLongID();
                Collection<Channel> channels = CEWBot.channels.get(serverid);
                Channel channel = BotUtils.getChannel(serverid, channelid);
                if (channels==null)
                    channels = new ArrayList<>();
                if (channel==null)
                    channel = new Channel(channelid);

                if (args.length<=0)
                    channel.cewAlert = true;
                // validate number
                else if (args.length%2!=0)
                    BotUtils.reply(e, "The argument is missing");
                // change settings in channel
                else {
                    final Field[] fields = Channel.class.getFields();
                    for (int i = 0; i<args.length; i += 2) {
                        for (final Field line : fields) {
                            if (line.getName().equals(args[i]))
                                try {
                                    line.setBoolean(channel, BooleanUtils.toBoolean(args[i+1]));
                                } catch (IllegalArgumentException|IllegalAccessException ex) {
                                    BotUtils.reply(e, "Got an error.");
                                    CEWBot.LOGGER.error("Reflection error", ex);
                                }
                        }
                    }
                }
                channels.add(channel);
                CEWBot.channels.put(serverid, channels);
                try {
                    CEWBot.saveConfigs();
                } catch (final ConfigException ex) {
                    BotUtils.reply(e, "ConfigException");
                    CEWBot.LOGGER.error("Save error", ex);
                }
                BotUtils.reply(e, "Have set up a channel.");
            }
        },
        unregister {
            @Override
            public void onCommand(final MessageReceivedEvent e, final String[] args) {
                final Collection<Channel> channels = CEWBot.channels.get(e.getGuild().getLongID());
                if (channels!=null) {
                    final long id = e.getChannel().getLongID();
                    channels.removeIf(channel -> channel.getId() == id);
                    try {
                        CEWBot.saveConfigs();
                        BotUtils.reply(e, "Done");
                    } catch (final ConfigException ex) {
                        CEWBot.LOGGER.error("Error on channel delete", ex);
                    }
                } else
                    BotUtils.reply(e, "This channel has no settings");
            }
        },
        reload {
            @Override
            public void onCommand(final MessageReceivedEvent e, final String[] args) {
                try {
                    CEWBot.loadConfigs();
                    BotUtils.reply(e, "Done");
                } catch (final ConfigException ex) {
                    BotUtils.reply(e, "Error occurred");
                    CEWBot.LOGGER.error("Load error", ex);
                }
            }
        };

        public abstract void onCommand(MessageReceivedEvent e, String[] args);
    }
}