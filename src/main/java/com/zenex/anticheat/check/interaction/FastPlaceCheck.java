package com.zenex.anticheat.check.interaction;

import com.zenex.anticheat.check.Check;
import com.zenex.anticheat.check.CheckType;
import com.zenex.anticheat.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class FastPlaceCheck extends Check implements Listener {
    
    private final int MAX_BLOCKS;
    
    public FastPlaceCheck() {
        super("FastPlace", CheckType.FASTPLACE);
        this.MAX_BLOCKS = plugin.getConfig().getInt("checks.fastplace.max-block-per-second", 15);
    }
    
    @Override
    public void check(Player player, PlayerData data) {
        // Check is handled by event listener
    }
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("zenexanticheat.bypass")) return;
        if (player.getGameMode().equals(org.bukkit.GameMode.CREATIVE)) return;
        
        PlayerData data = plugin.getDataManager().getData(player);
        if (data == null) return;
        
        long now = System.currentTimeMillis();
        data.getPlaceTimes().add(now);
        data.getPlaceTimes().removeIf(time -> now - time > 1000);
        
        int blocks = data.getPlaceTimes().size();
        if (blocks > MAX_BLOCKS) {
            flag(player, data, String.format("BPS:%d>%d", blocks, MAX_BLOCKS));
        }
    }
}
