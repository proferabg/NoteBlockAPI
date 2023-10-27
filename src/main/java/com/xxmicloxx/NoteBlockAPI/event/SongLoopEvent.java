package com.xxmicloxx.NoteBlockAPI.event;

import lombok.Getter;
import lombok.Setter;

import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;

public class SongLoopEvent {
	private final SongPlayer song;

	@Setter
	@Getter
	private boolean cancelled = false;

	public SongLoopEvent(SongPlayer song) {
		this.song = song;
	}

	/**
	 * Returns SongPlayer which {@link Song} ends and is going to start again
	 * @return SongPlayer
	 */
	public SongPlayer getSongPlayer() {
		return song;
	}

}
