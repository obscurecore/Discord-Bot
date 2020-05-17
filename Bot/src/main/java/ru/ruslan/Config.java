package ru.ruslan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Config  {

	public String token="";
	public int Delay;
	public  List<Channel> channels;


	public static Config getDefault() {
		final Config c = new Config();
		c.token = "";
		c.Delay = 1;
		c.channels = Arrays.asList();
		return c;
	}

}