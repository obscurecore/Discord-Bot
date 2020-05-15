package ru.ruslan;

import sx.blah.discord.handle.impl.obj.Channel;

import java.util.ArrayList;
import java.util.List;

public class Config {

	public String token;
	public int Delay;
	public List<Channel> channels;

	public static Config getDefault() {
		final Config c = new Config();
		c.token = "";
		c.Delay = 1;
		c.channels = new ArrayList<>();
		return c;
	}

}