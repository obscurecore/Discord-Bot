package ru.ruslan;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.handle.impl.obj.User;

public class CEWBot {
    public static final Logger LOGGER = LoggerFactory.getLogger(CEWBot.class);
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final File JARPATH = new File(".").getAbsoluteFile();
    public static Config config;
    public static Map<Long, Collection<Channel>> channels;
    public static IDiscordClient client;

    public static void main(final String[] args) throws ConfigException {

        loadConfigs();
        client = createClient("NzA5NzA5ODE2NDk4NDg3MzQ4.Xr_bcg.tvnKrAiQilJDwt8CKTE--ZVBxn0", true);

        /**
         * The EventDispatcher stores a registry of listeners which are used on every event being dispatched.
         * Dispatching of these events happens asynchronously
         * either on user provided threadpools, or a default threadpool.
         */

        final EventDispatcher dispatcher = client.getDispatcher();

        /**
         * When registering a listener, the client has the option of specifying the thread pool
         * for that particular listener, this way, different listeners are effectively
         * isolated in terms of threads, avoiding possible thread starvation.
         */

        dispatcher.registerListener(new EventListener());
        LOGGER.info("Running CEWBot");
    }

    public static IDiscordClient createClient(final String token, final boolean login) {
        final ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.withToken(token);
        if (login)
            return clientBuilder.login();
        else
            return clientBuilder.build();
    }

    public static void loadConfigs() throws ConfigException {
        Writer writer = null;

        try {
            final File cfgFile = new File(JARPATH, "config.json");
            // Cause Writer create file
            if (!cfgFile.exists()) {
                writer = Files.newBufferedWriter(cfgFile.toPath());
                // Serialize
                GSON.toJson(Config.getDefault(), writer);
                writer.close();
            }
            // Deserialize
            config = GSON.fromJson(new BufferedReader(new FileReader(cfgFile)), Config.class);

        } catch (JsonSyntaxException | JsonIOException | IOException e) {
            throw new ConfigException("Config load  error", e);
        }

        try {
            final File channelFile = new File(JARPATH, "channels.json");
            final Type type = new TypeToken<Map<Long, Collection<Long>>>() {
            }.getType();
            if (!channelFile.exists()) {
                writer = Files.newBufferedWriter(channelFile.toPath());
                GSON.toJson(new HashMap<Long, Collection<Channel>>(), writer);
                writer.close();
            }

            channels = GSON.fromJson(new BufferedReader(new FileReader(channelFile)), type);
        } catch (JsonSyntaxException | JsonIOException | IOException e) {
            throw new ConfigException("Channel load error", e);
        }
    }

    public static void saveConfigs() throws ConfigException {
        Writer writer = null;

        try {
            final File cfgFile = new File(JARPATH, "config.json");
            writer = Files.newBufferedWriter(cfgFile.toPath());
            GSON.toJson(Config.getDefault(), writer);
            writer.close();

        } catch (JsonIOException | IOException e) {
            throw new ConfigException("Config save error", e);
        }

        try {
            final File channelFile = new File(JARPATH, "channels.json");
            writer = Files.newBufferedWriter(channelFile.toPath());
            final Type type = new TypeToken<Map<Long, Collection<Channel>>>() {
            }.getType();

            GSON.toJson(channels, writer);
            writer.close();

        } catch (JsonIOException | IOException e) {
            throw new ConfigException("Channel save error", e);
        }
    }
}