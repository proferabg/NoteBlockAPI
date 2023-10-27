package com.xxmicloxx.NoteBlockAPI.event;

import lombok.Getter;

import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;

public class SongNextEvent {
	@Getter
	private SongPlayer song;

	public SongNextEvent(SongPlayer song) {
		this.song = song;
	}

}
