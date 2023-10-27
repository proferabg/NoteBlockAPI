package com.xxmicloxx.NoteBlockAPI.songplayer;

import com.velocitypowered.api.proxy.Player;
import com.xxmicloxx.NoteBlockAPI.model.playmode.ChannelMode;
import com.xxmicloxx.NoteBlockAPI.model.playmode.MonoMode;
import com.xxmicloxx.NoteBlockAPI.model.playmode.MonoStereoMode;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.SoundCategory;
import dev.simplix.protocolize.api.player.ProtocolizePlayer;

import com.xxmicloxx.NoteBlockAPI.NoteBlockAPI;
import com.xxmicloxx.NoteBlockAPI.model.Layer;
import com.xxmicloxx.NoteBlockAPI.model.Note;
import com.xxmicloxx.NoteBlockAPI.model.Playlist;
import com.xxmicloxx.NoteBlockAPI.model.Song;

/**
 * SongPlayer playing to everyone added to it no matter where he is
 *
 */
public class RadioSongPlayer extends SongPlayer {
	
	//protected boolean stereo = true;
	
	public RadioSongPlayer(Song song) {
		super(song);
	}

	public RadioSongPlayer(Song song, SoundCategory soundCategory) {
		super(song, soundCategory);
	}

	public RadioSongPlayer(Playlist playlist, SoundCategory soundCategory) {
		super(playlist, soundCategory);
	}

	public RadioSongPlayer(Playlist playlist) {
		super(playlist);
	}

	@Override
	public void playTick(Player player, int tick) {
		byte playerVolume = NoteBlockAPI.getPlayerVolume(player);

		for (Layer layer : song.getLayerHashMap().values()) {
			Note note = layer.getNote(tick);
			if (note == null) {
				continue;
			}

			float volume = (layer.getVolume() * (int) this.volume * (int) playerVolume * note.getVelocity()) / 100_00_00_00F;

			ProtocolizePlayer pp = Protocolize.playerProvider().player(player.getUniqueId());
			channelMode.play(player, pp.location(), song, layer, note, soundCategory, volume, !enable10Octave);
		}
	}

	/**
	 * Returns if the SongPlayer will play Notes from two sources as stereo
	 * @return if is played stereo
     * @deprecated
	 */
	@Deprecated
	public boolean isStereo(){
		return !(channelMode instanceof MonoMode);
	}
	
	/**
	 * Sets if the SongPlayer will play Notes from two sources as stereo
	 * @param stereo
     * @deprecated
	 */
	@Deprecated
	public void setStereo(boolean stereo){
		channelMode = stereo ? new MonoMode() : new MonoStereoMode();
	}

	/**
	 * Sets how will be {@link Note} played to {@link Player} (eg. mono or stereo). Default is {@link MonoMode}.
	 * @param mode
	 */
	public void setChannelMode(ChannelMode mode){
	    channelMode = mode;
    }
}
