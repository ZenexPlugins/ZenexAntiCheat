package com.zenex.anticheat.check.combat;

import com.zenex.anticheat.check.Check;
import com.zenex.anticheat.check.CheckType;
import com.zenex.anticheat.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class VelocityCheck extends Check implements Listener {
    
    public VelocityCheck() {
        super("Velocity", CheckType.VELOCITY);
    }
    
    @Override
    public void check(Player player, PlayerData data) {
        // Check is handled by event listener
    }
    
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (!(event.getDamager() instanceof Player)) return;
        
        Player player = (Player) event.getEntity();
        if (player.hasPermission("zenexanticheat.bypass")) return;
        
        PlayerData data = plugin.getDataManager().getData(player);
        if (data == null) return;
        
        double velocityY = player.getVelocity().getY();
        if (velocityY < 0.1 && velocityY > -0.1) {
            flag(player, data, String.format("velY:%.2f", velocityY));
        }
    }
}
