package ru.ruslan;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class Channel {
    private final long id;
    public boolean cewAlert;
    public boolean cewPrediction;

    public Channel(final long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public static Channel getChannel(final long serverId, final long channelId) {
        final Collection<Channel> channels = CEWBot.channels.get(serverId);
        if (channels != null)
            for (final Iterator<Channel> it = channels.iterator(); it.hasNext(); ) {
                final Channel c = it.next();
                if (c.getId() == channelId)
                    return c;
            }
        return null;
    }

}