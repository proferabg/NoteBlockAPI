package com.xxmicloxx.NoteBlockAPI;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.io.File;

public class NBAPICommand implements SimpleCommand {
    @Override
    public void execute(final Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();

        if(source instanceof Player player) {
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("start")) {
                    File file = new File(args[1]);
                    if(!file.exists()) {
                        player.sendMessage(Component.text("[NBAPI] This song doesn't exist").color(NamedTextColor.RED));
                        return;
                    }
                    Song song = NBSDecoder.parse(file);
                    RadioSongPlayer rsp = new RadioSongPlayer(song);
                    rsp.addPlayer(player);
                    rsp.setPlaying(true);
                }
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("stop")) {
                    if (NoteBlockAPI.isReceivingSong(player)) {
                        NoteBlockAPI.stopPlaying(player);
                    }
                }
            }
        }
    }
}

