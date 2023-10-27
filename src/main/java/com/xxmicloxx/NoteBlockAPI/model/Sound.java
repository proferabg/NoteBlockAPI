package com.xxmicloxx.NoteBlockAPI.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Version independent Spigot sounds.
 *
 * Enum mapping to note names for different
 * Minecraft versions.
 * 
 * @see <a href="https://gist.github.com/NiklasEi/7bd0ffd136f8459df0940e4501d47a8a">https://gist.github.com/NiklasEi/7bd0ffd136f8459df0940e4501d47a8a</a>
 * @author NiklasEi
 */
public enum Sound {

	NOTE_PIANO("NOTE_PIANO", "BLOCK_NOTE_HARP", "BLOCK_NOTE_BLOCK_HARP"),
	NOTE_BASS("NOTE_BASS", "BLOCK_NOTE_BASS", "BLOCK_NOTE_BLOCK_BASS"),
	NOTE_BASS_DRUM("NOTE_BASS_DRUM", "BLOCK_NOTE_BASEDRUM", "BLOCK_NOTE_BLOCK_BASEDRUM"),
	NOTE_SNARE_DRUM("NOTE_SNARE_DRUM", "BLOCK_NOTE_SNARE", "BLOCK_NOTE_BLOCK_SNARE"),
	NOTE_STICKS("NOTE_STICKS", "BLOCK_NOTE_HAT", "BLOCK_NOTE_BLOCK_HAT"),
	NOTE_BASS_GUITAR("NOTE_BASS_GUITAR", "BLOCK_NOTE_GUITAR", "BLOCK_NOTE_BLOCK_GUITAR"),
	NOTE_FLUTE("NOTE_FLUTE", "BLOCK_NOTE_FLUTE", "BLOCK_NOTE_BLOCK_FLUTE"),
	NOTE_BELL("NOTE_BELL", "BLOCK_NOTE_BELL", "BLOCK_NOTE_BLOCK_BELL"),
	NOTE_CHIME("NOTE_CHIME", "BLOCK_NOTE_CHIME", "BLOCK_NOTE_BLOCK_CHIME"),
	NOTE_XYLOPHONE("NOTE_XYLOPHONE", "BLOCK_NOTE_XYLOPHONE", "BLOCK_NOTE_BLOCK_XYLOPHONE"),
	NOTE_PLING("NOTE_PLING", "BLOCK_NOTE_PLING", "BLOCK_NOTE_BLOCK_PLING"),
	NOTE_IRON_XYLOPHONE("BLOCK_NOTE_BLOCK_IRON_XYLOPHONE"),
	NOTE_COW_BELL("BLOCK_NOTE_BLOCK_COW_BELL"),
	NOTE_DIDGERIDOO("BLOCK_NOTE_BLOCK_DIDGERIDOO"),
	NOTE_BIT("BLOCK_NOTE_BLOCK_BIT"),
	NOTE_BANJO("BLOCK_NOTE_BLOCK_BANJO");

	private String[] versionDependentNames;
	private dev.simplix.protocolize.data.Sound cached = null;
	private static Map<String, dev.simplix.protocolize.data.Sound> cachedSoundMap = new HashMap<>();

	Sound(String... versionDependentNames) {
		this.versionDependentNames = versionDependentNames;
	}

	/**
	 * Attempts to retrieve the org.bukkit.Sound equivalent of a version dependent enum name
	 * @param protocolizeSoundName
	 * @return dev.simplix.protocolize.data.Sound enum
	 */
	public static dev.simplix.protocolize.data.Sound getFromProtocolizeName(String protocolizeSoundName) {
		dev.simplix.protocolize.data.Sound sound = cachedSoundMap.get(protocolizeSoundName.toUpperCase());
		if (sound != null)
			return sound;

		return dev.simplix.protocolize.data.Sound.valueOf(protocolizeSoundName);
	}

	private dev.simplix.protocolize.data.Sound getSound() {
		if (cached != null) return cached;
		for (String name : versionDependentNames) {
			try {
				return cached = dev.simplix.protocolize.data.Sound.valueOf(name);
			} catch (IllegalArgumentException ignore2) {
				// try next
			}
		}
		return null;
	}

	/**
	 * Get the bukkit sound for current server version
	 *
	 * Caches sound on first call
	 * @return corresponding {@link dev.simplix.protocolize.data.Sound}
	 */
	public dev.simplix.protocolize.data.Sound protocolizeSound() {
		if (getSound() != null) {
			return getSound();
		}
		throw new IllegalArgumentException("Found no valid sound name for " + this.name());
	}

	static {
		// Cache sound access.
		for (Sound sound : values())
			for (String soundName : sound.versionDependentNames)
				cachedSoundMap.put(soundName.toUpperCase(), sound.getSound());
	}
}