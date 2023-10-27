package com.xxmicloxx.NoteBlockAPI.event;

import com.velocitypowered.api.proxy.Player;

import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;
import lombok.Getter;

/**
 * Called whenever a Player enters or leave the range of a stationary SongPlayer
 *
 */
public class PlayerRangeStateChangeEvent {

	/**
	 * -- GETTER --
	 * Returns SongPlayer which range Player enters or leaves
     */
	@Getter
	private SongPlayer song;

	/**
	 * -- GETTER --
	 *  Returns Player which enter/leave SongPlayer range
	 *
     */
	@Getter
	private Player player;

	private final boolean state;

	public PlayerRangeStateChangeEvent(SongPlayer song, Player player, boolean state) {
		this.song = song;
		this.player = player;
		this.state = state;
	}

	/**
	 * Returns true if Player is actually in SongPlayer range
	 * @return boolean determining if is Player in SongPlayer range
	 */
	public boolean isInRange() {
		return state;
	}

}
