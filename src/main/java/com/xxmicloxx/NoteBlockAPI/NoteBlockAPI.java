package com.xxmicloxx.NoteBlockAPI;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.scheduler.ScheduledTask;
import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;
import lombok.Getter;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * Main class; contains methods for playing and adjusting songs for players
 */
@Plugin(
		id = BuildConstants.ID,
		name = BuildConstants.NAME,
		version = BuildConstants.VERSION,
		description = BuildConstants.DESCRIPTION,
		authors = BuildConstants.DEVELOPERS,
		dependencies = {
				@Dependency(id = "protocolize")
		}
)
public class NoteBlockAPI {

	@Getter
	private static NoteBlockAPI plugin;

	@Getter
	private final ProxyServer server;

	@Getter
	private final Logger logger;

	@Getter
	private final Path dataDirectory;

	@Inject
	public NoteBlockAPI(ProxyServer _server, Logger _logger, @DataDirectory Path _dataDirectory) {
		server = _server;
		logger = _logger;
		dataDirectory = _dataDirectory;
		plugin = this;
		VelocityUtils.init(this);
	}
	
	private Map<UUID, ArrayList<SongPlayer>> playingSongs = new ConcurrentHashMap<UUID, ArrayList<SongPlayer>>();
	private Map<UUID, Byte> playerVolume = new ConcurrentHashMap<UUID, Byte>();

	private boolean disabling = false;

	/**
	 * Returns true if a Player is currently receiving a song
	 * @param player
	 * @return is receiving a song
	 */
	public static boolean isReceivingSong(Player player) {
		return isReceivingSong(player.getUniqueId());
	}

	/**
	 * Returns true if a Player with specified UUID is currently receiving a song
	 * @param uuid
	 * @return is receiving a song
	 */
	public static boolean isReceivingSong(UUID uuid) {
		ArrayList<SongPlayer> songs = plugin.playingSongs.get(uuid);
		return (songs != null && !songs.isEmpty());
	}

	/**
	 * Stops the song for a Player
	 * @param player
	 */
	public static void stopPlaying(Player player) {
		stopPlaying(player.getUniqueId());
	}

	/**
	 * Stops the song for a Player
	 * @param uuid
	 */
	public static void stopPlaying(UUID uuid) {
		ArrayList<SongPlayer> songs = plugin.playingSongs.get(uuid);
		if (songs == null) {
			return;
		}
		for (SongPlayer songPlayer : songs) {
			songPlayer.removePlayer(uuid);
		}
	}

	/**
	 * Sets the volume for a given Player
	 * @param player
	 * @param volume
	 */
	public static void setPlayerVolume(Player player, byte volume) {
		setPlayerVolume(player.getUniqueId(), volume);
	}

	/**
	 * Sets the volume for a given Player
	 * @param uuid
	 * @param volume
	 */
	public static void setPlayerVolume(UUID uuid, byte volume) {
		plugin.playerVolume.put(uuid, volume);
	}

	/**
	 * Gets the volume for a given Player
	 * @param player
	 * @return volume (byte)
	 */
	public static byte getPlayerVolume(Player player) {
		return getPlayerVolume(player.getUniqueId());
	}

	/**
	 * Gets the volume for a given Player
	 * @param uuid
	 * @return volume (byte)
	 */
	public static byte getPlayerVolume(UUID uuid) {
		Byte byteObj = plugin.playerVolume.get(uuid);
		if (byteObj == null) {
			byteObj = 100;
			plugin.playerVolume.put(uuid, byteObj);
		}
		return byteObj;
	}
	
	public static ArrayList<SongPlayer> getSongPlayersByPlayer(Player player){
		return getSongPlayersByPlayer(player.getUniqueId());
	}
	
	public static ArrayList<SongPlayer> getSongPlayersByPlayer(UUID player){
		return plugin.playingSongs.get(player);
	}
	
	public static void setSongPlayersByPlayer(Player player, ArrayList<SongPlayer> songs){
		setSongPlayersByPlayer(player.getUniqueId(), songs);
	}
	
	public static void setSongPlayersByPlayer(UUID player, ArrayList<SongPlayer> songs){
		plugin.playingSongs.put(player, songs);
	}

	@Subscribe
	public void onProxyInitialization(ProxyInitializeEvent event) {
		getServer().getCommandManager().register("test", new NBSCommand());
	}

	@Subscribe
	public void onProxyShutdown(ProxyShutdownEvent event) {
		disabling = true;
		getServer().getScheduler().tasksByPlugin(this).forEach(ScheduledTask::cancel);
	}

	public void doTask(Runnable runnable) {
		getServer().getScheduler().buildTask(this, runnable).schedule();
	}

	public boolean isDisabling() {
		return disabling;
	}
	
	public static NoteBlockAPI getAPI(){
		return plugin;
	}
}
