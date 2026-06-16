package com.zenex.anticheat.listener;

import com.zenex.anticheat.ZenexAntiCheat;
import com.zenex.anticheat.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AntiCheatListener implements Listener {
    
    private final ZenexAntiCheat plugin;
    
    public AntiCheatListener(ZenexAntiCheat plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerData data = plugin.getDataManager().getData(player);
        
        // Проверка на bypass
        if (player.hasPermission("zenexanticheat.bypass")) {
            data.setBypass(true);
        }
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.getDataManager().removeData(event.getPlayer());
    }
}
