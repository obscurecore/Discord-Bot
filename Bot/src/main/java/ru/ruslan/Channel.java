package ru.ruslan;

import java.io.Serializable;

public class Channel  {
	private final long id;
	public boolean cewAlert;
	public boolean cewPrediction;

	public Channel(final long id) {
		this.id = id;
	}

	public long getId() {
		return this.id;
	}

}