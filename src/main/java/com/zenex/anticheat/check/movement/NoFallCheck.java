package com.zenex.anticheat.check.movement;

import com.zenex.anticheat.check.Check;
import com.zenex.anticheat.check.CheckType;
import com.zenex.anticheat.data.PlayerData;
import org.bukkit.entity.Player;

public class NoFallCheck extends Check {
    
    private final double MIN_FALL;
    
    public NoFallCheck() {
        super("NoFall", CheckType.NOFALL);
        this.MIN_FALL = plugin.getConfig().getDouble("checks.nofall.min-fall-distance", 3.0);
    }
    
    @Override
    public void check(Player player, PlayerData data) {
        if (player.hasPermission("zenexanticheat.bypass")) return;
        if (player.isFlying()) return;
        if (player.isInWater() || player.isInLava()) return;
        
        boolean onGround = player.isOnGround();
        double fallDistance = player.getFallDistance();
        
        if (fallDistance > MIN_FALL && onGround) {
            if (data.getActualFallDistance() < fallDistance - 0.5) {
                flag(player, data, String.format("exp:%.1f, act:%.1f", fallDistance, data.getActualFallDistance()));
            }
        }
        
        data.setFallDistance(fallDistance);
        data.setActualFallDistance(fallDistance);
    }
}
