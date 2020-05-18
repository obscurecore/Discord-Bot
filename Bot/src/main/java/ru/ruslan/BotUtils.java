package ru.ruslan;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.Collection;
import java.util.Iterator;

public class BotUtils {

	public static void reply(final MessageReceivedEvent e, final String message) {
		e.getMessage().getChannel().sendMessage(message);
	}
	public static Channel getChannel(final long serverId, final long channelId) {
		final Collection<Channel> channels = CEWBot.channels.get(serverId);
		if (channels!=null)
			for (final Iterator<Channel> it = channels.iterator(); it.hasNext();) {
				final Channel c = it.next();
				if (c.getId()==channelId)
					return c;
			}
		return null;
	}
}