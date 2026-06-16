package com.zenex.anticheat.check.movement;

import com.zenex.anticheat.check.Check;
import com.zenex.anticheat.check.CheckType;
import com.zenex.anticheat.data.PlayerData;
import org.bukkit.entity.Player;

public class JesusCheck extends Check {
    
    private final double MAX_WATER_TIME;
    
    public JesusCheck() {
        super("Jesus", CheckType.JESUS);
        this.MAX_WATER_TIME = plugin.getConfig().getDouble("checks.jesus.max-water-time", 3.0);
    }
    
    @Override
    public void check(Player player, PlayerData data) {
        if (player.hasPermission("zenexanticheat.bypass")) return;
        if (!player.isInWater()) {
            data.setWaterTime(0);
            return;
        }
        
        if (!player.isSwimming() && !player.isSprinting()) {
            data.setWaterTime(data.getWaterTime() + 0.05);
        } else {
            data.setWaterTime(0);
            return;
        }
        
        if (data.getWaterTime() > MAX_WATER_TIME && player.isOnGround()) {
            flag(player, data, String.format("time:%.1f", data.getWaterTime()));
            data.setWaterTime(0);
        }
    }
}
