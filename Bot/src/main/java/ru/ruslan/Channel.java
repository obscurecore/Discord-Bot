package ru.ruslan;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class Channel {
    public final long id;
    public boolean cewAlert;
    public boolean cewPrediction;

    public Channel(final long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }


}