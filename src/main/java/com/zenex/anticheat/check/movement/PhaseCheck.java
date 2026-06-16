package com.zenex.anticheat.check.movement;

import com.zenex.anticheat.check.Check;
import com.zenex.anticheat.check.CheckType;
import com.zenex.anticheat.data.PlayerData;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PhaseCheck extends Check {
    
    public PhaseCheck() {
        super("Phase", CheckType.PHASE);
    }
    
    @Override
    public void check(Player player, PlayerData data) {
        if (player.hasPermission("zenexanticheat.bypass")) return;
        if (player.isInsideVehicle()) return;
        if (player.isFlying()) return;
        if (player.getGameMode().equals(org.bukkit.GameMode.CREATIVE) ||
            player.getGameMode().equals(org.bukkit.GameMode.SPECTATOR)) return;
        
        Location loc = player.getLocation();
        boolean inWall = loc.getBlock().getType().isSolid() ||
                         loc.clone().add(0, 1, 0).getBlock().getType().isSolid();
        
        if (inWall && player.isOnGround()) {
            flag(player, data, "player in wall");
        }
    }
}
