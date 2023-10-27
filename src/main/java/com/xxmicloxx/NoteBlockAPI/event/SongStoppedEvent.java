package com.xxmicloxx.NoteBlockAPI.event;

import lombok.Getter;

import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;


/**
 * Called whenever a SongPlayer is stopped
 * @see SongPlayer
 */
public class SongStoppedEvent {
	@Getter
	private SongPlayer songPlayer;

	public SongStoppedEvent(SongPlayer songPlayer) {
		this.songPlayer = songPlayer;
	}

}

