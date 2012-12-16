package com.platymuus.bukkit.nojoinspam;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class NoJoinSpamMain extends JavaPlugin implements Listener
{
    public final ArrayList<Player> enabledPlayers = new ArrayList<Player>();

    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(this, this);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] split)
    {
        if ((sender instanceof Player)) {
            Player player = (Player) sender;
            if (this.enabledPlayers.contains(player)) {
                this.enabledPlayers.remove(player);
                player.sendMessage(ChatColor.GRAY + "Join/quit messages are now OFF");
            }
            else {
                this.enabledPlayers.add(player);
                player.sendMessage(ChatColor.GRAY + "Join/quit messages are now ON");
            }
        }
        return true;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        String msg = event.getJoinMessage();
        event.setJoinMessage(null);
        for (Player player : this.enabledPlayers)
            player.sendMessage(msg);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        String msg = event.getQuitMessage();
        event.setQuitMessage(null);
        for (Player player : this.enabledPlayers)
            player.sendMessage(msg);
    }
}