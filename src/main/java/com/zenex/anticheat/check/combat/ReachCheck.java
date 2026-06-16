package com.zenex.anticheat.check.combat;

import com.zenex.anticheat.check.Check;
import com.zenex.anticheat.check.CheckType;
import com.zenex.anticheat.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ReachCheck extends Check implements Listener {
    
    private final double MAX_REACH;
    
    public ReachCheck() {
        super("Reach", CheckType.REACH);
        this.MAX_REACH = plugin.getConfig().getDouble("checks.reach.max-reach", 3.5);
    }
    
    @Override
    public void check(Player player, PlayerData data) {
        // Check is handled by event listener
    }
    
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        if (!(event.getEntity() instanceof Player)) return;
        
        Player player = (Player) event.getDamager();
        Player target = (Player) event.getEntity();
        
        if (player.hasPermission("zenexanticheat.bypass")) return;
        
        double distance = player.getLocation().distance(target.getLocation());
        
        if (distance > MAX_REACH) {
            PlayerData data = plugin.getDataManager().getData(player);
            if (data != null) {
                flag(player, data, String.format("R:%.2f>%.2f", distance, MAX_REACH));
            }
        }
    }
}
