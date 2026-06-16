package com.zenex.anticheat.check.interaction;

import com.zenex.anticheat.check.Check;
import com.zenex.anticheat.check.CheckType;
import com.zenex.anticheat.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class FastBreakCheck extends Check implements Listener {
    
    private final int MAX_BREAKS;
    
    public FastBreakCheck() {
        super("FastBreak", CheckType.FASTBREAK);
        this.MAX_BREAKS = plugin.getConfig().getInt("checks.fastbreak.max-break-per-second", 10);
    }
    
    @Override
    public void check(Player player, PlayerData data) {
        // Check is handled by event listener
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("zenexanticheat.bypass")) return;
        if (player.getGameMode().equals(org.bukkit.GameMode.CREATIVE)) return;
        
        PlayerData data = plugin.getDataManager().getData(player);
        if (data == null) return;
        
        long now = System.currentTimeMillis();
        data.getBreakTimes().add(now);
        data.getBreakTimes().removeIf(time -> now - time > 1000);
        
        int breaks = data.getBreakTimes().size();
        if (breaks > MAX_BREAKS) {
            flag(player, data, String.format("BPS:%d>%d", breaks, MAX_BREAKS));
        }
    }
}
