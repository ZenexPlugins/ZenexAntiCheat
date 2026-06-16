package com.zenex.anticheat.check.movement;

import com.zenex.anticheat.check.Check;
import com.zenex.anticheat.check.CheckType;
import com.zenex.anticheat.data.PlayerData;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SpeedCheck extends Check {
    
    private final double MAX_SPEED;
    
    public SpeedCheck() {
        super("Speed", CheckType.SPEED);
        this.MAX_SPEED = plugin.getConfig().getDouble("checks.speed.max-speed", 8.0);
    }
    
    @Override
    public void check(Player player, PlayerData data) {
        Location from = data.getLastLocation();
        Location to = player.getLocation();
        
        if (from == null || to == null) return;
        if (!player.isOnGround()) return;
        if (player.isFlying() || player.isGliding()) return;
        if (player.isInWater() || player.isInLava()) return;
        if (player.hasPermission("zenexanticheat.bypass")) return;
        
        double dx = to.getX() - from.getX();
        double dz = to.getZ() - from.getZ();
        double speed = Math.sqrt(dx * dx + dz * dz);
        
        if (speed > MAX_SPEED) {
            flag(player, data, String.format("S:%.2f>%.2f", speed, MAX_SPEED));
        }
        
        data.setLastLocation(to);
    }
}
