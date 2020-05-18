package ru.ruslan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Config  {

	public String token;
	public int RDelay;
	public int timeFixDelay;
	public  List<Channel> channels;
	public String nptServer;

	public static Config getDefault() {
		final Config c = new Config();
		c.token = "";
		c.RDelay = 1;
		c.timeFixDelay = 400;
		c.channels = Arrays.asList();
		c.nptServer = "time.google.com";
		return c;
	}

}