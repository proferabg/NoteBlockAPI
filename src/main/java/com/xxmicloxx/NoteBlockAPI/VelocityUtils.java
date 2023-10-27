package com.xxmicloxx.NoteBlockAPI;

import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.proxy.ConsoleCommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import com.velocitypowered.api.scheduler.ScheduledTask;
import net.kyori.adventure.text.Component;

import java.time.Duration;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class VelocityUtils {

    private static NoteBlockAPI plugin;

    public static void init(NoteBlockAPI _plugin) {
        plugin = _plugin;
    }

    public static ServerInfo getServerInfo(String serverName){
        Optional<RegisteredServer> server = plugin.getServer().getServer(serverName);
        return server.map(RegisteredServer::getServerInfo).orElse(null);
    }

    public static RegisteredServer getServer(String serverName){
        return plugin.getServer().getServer(serverName).orElse(null);
    }

    public static Collection<RegisteredServer> getServers(){
        return plugin.getServer().getAllServers();
    }

    public static Player getPlayer(String playerName){
        return plugin.getServer().getPlayer(playerName).orElse(null);
    }

    public static Player getPlayer(UUID uuid){
        return plugin.getServer().getPlayer(uuid).orElse(null);
    }

    public static Collection<Player> getPlayers(){
        return plugin.getServer().getAllPlayers();
    }

    public static ProxyServer getProxy() {
        return plugin.getServer();
    }

    public static void broadcast(Component component){
        plugin.getServer().getAllPlayers().forEach((player) -> player.sendMessage(component));
    }

    public static ScheduledTask schedule(Runnable runnable, long delay){
        return plugin.getServer().getScheduler().buildTask(plugin, runnable).delay(Duration.ofMillis(delay)).schedule();
    }

    public static ScheduledTask schedule(Runnable runnable, long delay, long repeat){
        return plugin.getServer().getScheduler().buildTask(plugin, runnable).delay(Duration.ofMillis(delay)).repeat(Duration.ofMillis(repeat)).schedule();
    }

    public static CommandManager getCommandManager(){
        return plugin.getServer().getCommandManager();
    }

    public static void log(String message){
        plugin.getLogger().info(message);
    }

    public static void error(String message){
        plugin.getLogger().severe(message);
    }

    public static void executeCommand(String command){
        ConsoleCommandSource consoleCommandSource = plugin.getServer().getConsoleCommandSource();
        plugin.getServer().getCommandManager().executeAsync(consoleCommandSource, command);
    }

    public static void executeCommandAsPlayer(Player player, String command){
        CompletableFuture<Boolean> cf = plugin.getServer().getCommandManager().executeAsync(player, command.trim());
        cf.thenAccept(result -> {
            if (result != null) {
                System.out.println("Command execution result: " + result);
            } else {
                System.out.println("Command execution completed without a result.");
            }
        }).exceptionally(throwable -> {
            System.out.println("Command execution failed with exception: " + throwable);
            return null;
        });
    }

    public static ConsoleCommandSource getConsoleCommandSource(){
        return plugin.getServer().getConsoleCommandSource();
    }



}
