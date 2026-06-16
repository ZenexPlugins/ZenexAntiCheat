package com.zenex.anticheat.data;

import com.zenex.anticheat.ZenexAntiCheat;
import com.zenex.anticheat.check.Check;
import org.bukkit.entity.Player;

public class ViolationManager {
    
    private final ZenexAntiCheat plugin;
    
    public ViolationManager(ZenexAntiCheat plugin) {
        this.plugin = plugin;
    }
    
    public void handleViolation(Player player, Check check, double vl, String details) {
        if (plugin.getConfig().getBoolean("alerts.chat", true)) {
            String alert = plugin.getConfig().getString("messages.alert", "&c[AC] &7{player} &cнарушил &e{check} &7(VL: {vl})")
                .replace("{player}", player.getName())
                .replace("{check}", check.getName())
                .replace("{vl}", String.format("%.1f", vl))
                .replace("&", "§");
            
            plugin.getServer().broadcast(alert, "zenexanticheat.alerts");
        }
        
        if (plugin.getConfig().getBoolean("alerts.console", true)) {
            plugin.getLogger().info("[AC] " + player.getName() + " violated " + check.getName() + " (VL: " + vl + ") " + details);
        }
    }
}
