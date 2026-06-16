package com.zenex.anticheat.check.movement;

import com.zenex.anticheat.check.Check;
import com.zenex.anticheat.check.CheckType;
import com.zenex.anticheat.data.PlayerData;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class FlyCheck extends Check {
    
    public FlyCheck() {
        super("Fly", CheckType.FLY);
    }
    
    @Override
    public void check(Player player, PlayerData data) {
        if (player.hasPermission("zenexanticheat.bypass")) return;
        if (player.getGameMode().equals(org.bukkit.GameMode.CREATIVE) ||
            player.getGameMode().equals(org.bukkit.GameMode.SPECTATOR)) return;
        if (player.isFlying()) return;
        
        Location loc = player.getLocation();
        boolean onGround = player.isOnGround();
        boolean inWater = player.isInWater();
        boolean inLava = player.isInLava();
        boolean onClimb = loc.getBlock().getType().toString().contains("LADDER") ||
                          loc.getBlock().getType().toString().contains("VINE");
        
        if (!onGround && !inWater && !inLava && !onClimb && !player.isFlying()) {
            if (data.getLastLocation() != null) {
                double dy = loc.getY() - data.getLastLocation().getY();
                double ddy = data.getLastDeltaY();
                
                if (dy > 0.1 && ddy < -0.1) {
                    flag(player, data, String.format("dy:%.2f, last:%.2f", dy, ddy));
                }
            }
        }
        
        if (data.getLastLocation() != null) {
            data.setLastDeltaY(loc.getY() - data.getLastLocation().getY());
        }
        data.setLastLocation(loc);
    }
}
