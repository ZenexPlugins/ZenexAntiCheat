package com.zenex.anticheat.punishment;

import com.zenex.anticheat.ZenexAntiCheat;
import com.zenex.anticheat.check.Check;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PunishmentManager {
    
    private final ZenexAntiCheat plugin;
    
    public PunishmentManager(ZenexAntiCheat plugin) {
        this.plugin = plugin;
    }
    
    public void punish(Player player, Check check, double vl) {
        PunishmentType type = check.getPunishment();
        String checkName = check.getName();
        
        switch (type) {
            case WARN:
                String warnMsg = plugin.getConfig().getString("messages.punishment-warn", "&c⚠️ {player}, не используйте читы! ({check})")
                    .replace("{player}", player.getName())
                    .replace("{check}", checkName)
                    .replace("&", "§");
                player.sendMessage(warnMsg);
                break;
                
            case KICK:
                String kickMsg = plugin.getConfig().getString("messages.punishment-kick", "&c❌ Вы были кикнуты за читы! ({check})")
                    .replace("{check}", checkName)
                    .replace("&", "§");
                Bukkit.getScheduler().runTask(plugin, () -> player.kickPlayer(kickMsg));
                break;
                
            case BAN:
                String banMsg = plugin.getConfig().getString("messages.punishment-ban", "&c❌ Вы забанены за читы! ({check})")
                    .replace("{check}", checkName)
                    .replace("&", "§");
                Bukkit.getScheduler().runTask(plugin, () -> player.kickPlayer(banMsg));
                break;
        }
    }
}
