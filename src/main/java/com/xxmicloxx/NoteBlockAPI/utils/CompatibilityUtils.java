package com.xxmicloxx.NoteBlockAPI.utils;

import java.util.ArrayList;
import java.util.List;

import com.velocitypowered.api.proxy.Player;
import com.xxmicloxx.NoteBlockAPI.VelocityUtils;
import dev.simplix.protocolize.api.Location;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.SoundCategory;
import dev.simplix.protocolize.api.player.ProtocolizePlayer;
import dev.simplix.protocolize.data.Sound;

import com.xxmicloxx.NoteBlockAPI.model.CustomInstrument;

/**
 * Fields/methods for reflection &amp; version checking
 */
public class CompatibilityUtils {

	private static List<String> unknownSounds = new ArrayList<>();

	/**
	 * Plays a sound using NMS &amp; reflection
	 * @param player
	 * @param location
	 * @param sound
	 * @param category
	 * @param volume
	 * @param pitch
	 * 
	 * @deprecated use {@link #playSound(Player, Location, String, SoundCategory, float, float, float)}
	 */
	public static void playSound(Player player, Location location, String sound, SoundCategory category, float volume, float pitch) {
		playSound(player, location, sound, category, volume, pitch, 0);
	}

	/**
	 * Plays a sound using NMS &amp; reflection
	 * @param player
	 * @param location
	 * @param sound
	 * @param category
	 * @param volume
	 * @param pitch
	 * @deprecated use {@link #playSound(Player, Location, String, SoundCategory, float, float, float)}
	 */
	public static void playSound(Player player, Location location, String sound, SoundCategory category, float volume, float pitch, boolean stereo) {
		playSound(player, location, sound, category, volume, pitch, stereo ? 2 : 0);
	}

	/**
	 * Plays a sound using NMS &amp; reflection
	 * @param player
	 * @param location
	 * @param sound
	 * @param category
	 * @param volume
	 * @param pitch
	 * 
	 * @deprecated use {@link #playSound(Player, Location, Sound, SoundCategory, float, float, float)}
	 */
	public static void playSound(Player player, Location location, Sound sound, SoundCategory category, float volume, float pitch) {
		playSound(player, location, sound, category, volume, pitch, 0);
	}
	
	/**
	 * Plays a sound using NMS &amp; reflection
	 * @param player
	 * @param location
	 * @param sound
	 * @param category
	 * @param volume
	 * @param pitch
	 * @deprecated use {@link #playSound(Player, Location, Sound, SoundCategory, float, float, float)}
	 */
	public static void playSound(Player player, Location location, Sound sound,  SoundCategory category, float volume, float pitch, boolean stereo) {
		playSound(player, location, sound, category, volume, pitch, stereo ? 2 : 0);
	}

	/**
	 * Plays a sound using NMS &amp; reflection
	 * @param player
	 * @param location
	 * @param sound
	 * @param category
	 * @param volume
	 * @param pitch
	 * @param distance
	 */
	public static void playSound(Player player, Location location, String sound, SoundCategory category, float volume, float pitch, float distance) {
		playSoundUniversal(player, location, sound, category, volume, pitch, distance);
	}

	/**
	 * Plays a sound using NMS &amp; reflection
	 * @param player
	 * @param location
	 * @param sound
	 * @param category
	 * @param volume
	 * @param pitch
	 * @param distance
	 */
	public static void playSound(Player player, Location location, Sound sound, SoundCategory category, float volume, float pitch, float distance) {
		playSoundUniversal(player, location, sound, category, volume, pitch, distance);
	}

	private static void playSoundUniversal(Player player, Location location, Object sound, SoundCategory category, float volume, float pitch, float distance) {
		ProtocolizePlayer protocolizePlayer = Protocolize.playerProvider().player(player.getUniqueId());

		Sound protocolizeSound = null;
		if(sound instanceof String soundStr){
			try {
				String bestMatch = soundStr.replace(".", "_").toUpperCase();
				if(bestMatch.contains("ENTITY_FIREWORK_B"))
					bestMatch = bestMatch.replace("ENTITY_FIREWORK_B", "ENTITY_FIREWORK_ROCKET_B");
				protocolizeSound = Sound.valueOf(bestMatch);
			} catch (Exception e){
				if(!unknownSounds.contains(soundStr)){
					unknownSounds.add(soundStr);
					VelocityUtils.error("Could not find sound for: " + soundStr);
				}
			}
		} else if(sound instanceof Sound s){
			protocolizeSound = s;
		}

		if(protocolizePlayer != null && protocolizeSound != null){
			protocolizePlayer.playSound(MathUtils.stereoPan(location, distance), protocolizeSound, category, volume, pitch);
		}
	}

	/**
	 * Gets instruments which were added post-1.12
	 * @return ArrayList of instruments
	 * @deprecated Use {@link #getVersionCustomInstruments(float)}
	 */
	public static ArrayList<CustomInstrument> get1_12Instruments(){
		return getVersionCustomInstruments(0.0112f);
	}

	/**
	 * Return list of instuments which were added in specified version
	 * @param serverVersion 1.12 = 0.0112f, 1.14 = 0.0114f,...
	 * @return list of custom instruments, if no instuments were added in specified version returns empty list
	 */
	public static ArrayList<CustomInstrument> getVersionCustomInstruments(float serverVersion){
		ArrayList<CustomInstrument> instruments = new ArrayList<>();
		if (serverVersion == 0.0112f){
			instruments.add(new CustomInstrument((byte) 0, "Guitar", "block.note_block.guitar.ogg"));
			instruments.add(new CustomInstrument((byte) 0, "Flute", "block.note_block.flute.ogg"));
			instruments.add(new CustomInstrument((byte) 0, "Bell", "block.note_block.bell.ogg"));
			instruments.add(new CustomInstrument((byte) 0, "Chime", "block.note_block.icechime.ogg"));
			instruments.add(new CustomInstrument((byte) 0, "Xylophone", "block.note_block.xylobone.ogg"));
			return instruments;
		}

		if (serverVersion == 0.0114f){
			instruments.add(new CustomInstrument((byte) 0, "Iron Xylophone", "block.note_block.iron_xylophone.ogg"));
			instruments.add(new CustomInstrument((byte) 0, "Cow Bell", "block.note_block.cow_bell.ogg"));
			instruments.add(new CustomInstrument((byte) 0, "Didgeridoo", "block.note_block.didgeridoo.ogg"));
			instruments.add(new CustomInstrument((byte) 0, "Bit", "block.note_block.bit.ogg"));
			instruments.add(new CustomInstrument((byte) 0, "Banjo", "block.note_block.banjo.ogg"));
			instruments.add(new CustomInstrument((byte) 0, "Pling", "block.note_block.pling.ogg"));
			return instruments;
		}
		return instruments;
	}

	/**
	 * Return list of custom instruments based on song first custom instrument index and server version
	 * @param firstCustomInstrumentIndex
	 * @return
	 */
	public static ArrayList<CustomInstrument> getVersionCustomInstrumentsForSong(int firstCustomInstrumentIndex){
		ArrayList<CustomInstrument> instruments = new ArrayList<>();

		if (getServerVersion() < 0.0112f){
			if (firstCustomInstrumentIndex == 10) {
				instruments.addAll(getVersionCustomInstruments(0.0112f));
			} else if (firstCustomInstrumentIndex == 16){
				instruments.addAll(getVersionCustomInstruments(0.0112f));
				instruments.addAll(getVersionCustomInstruments(0.0114f));
			}
		} else if (getServerVersion() < 0.0114f){
			if (firstCustomInstrumentIndex == 16){
				instruments.addAll(getVersionCustomInstruments(0.0114f));
			}
		}

		return instruments;
	}

	/**
	 * Returns server version as float less than 1 with two digits for each version part
	 * @return e.g. 0.011401f for 1.14.1
	 */
	public static float getServerVersion(){
		return 0.012002f;
	}

}
