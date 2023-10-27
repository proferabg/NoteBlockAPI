package com.xxmicloxx.NoteBlockAPI.model.playmode;

import com.velocitypowered.api.proxy.Player;
import com.xxmicloxx.NoteBlockAPI.model.Layer;
import com.xxmicloxx.NoteBlockAPI.model.Note;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import dev.simplix.protocolize.api.Location;
import dev.simplix.protocolize.api.SoundCategory;

/**
 * Decides how is {@link Note} played to {@link Player}
 */
public abstract class ChannelMode {

    @Deprecated
    public abstract void play(Player player, Location location, Song song, Layer layer, Note note,
                              SoundCategory soundCategory, float volume, float pitch);

    public abstract void play(Player player, Location location, Song song, Layer layer, Note note,
                              SoundCategory soundCategory, float volume, boolean doTranspose);
}