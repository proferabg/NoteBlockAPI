package com.xxmicloxx.NoteBlockAPI.event;

import lombok.Getter;
import lombok.Setter;

import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;

/**
 * Called whenever a SongPlayer is destroyed
 * @see SongPlayer
 *
 */
public class SongDestroyingEvent {
	private final SongPlayer song;

	@Setter
	@Getter
	private boolean cancelled = false;

	public SongDestroyingEvent(SongPlayer song) {
		this.song = song;
	}

	/**
	 * Returns SongPlayer which is being destroyed
	 * @return SongPlayer
	 */
	public SongPlayer getSongPlayer() {
		return song;
	}


}
