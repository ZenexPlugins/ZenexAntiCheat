package com.zenex.anticheat.check.combat;

import com.zenex.anticheat.check.Check;
import com.zenex.anticheat.check.CheckType;
import com.zenex.anticheat.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class KillAuraCheck extends Check implements Listener {
    
    private final double MAX_CPS;
    private final double ROTATION_THRESHOLD;
    
    public KillAuraCheck() {
        super("KillAura", CheckType.KILLAURA);
        this.MAX_CPS = plugin.getConfig().getDouble("checks.killaura.max-cps", 20);
        this.ROTATION_THRESHOLD = plugin.getConfig().getDouble("checks.killaura.rotation-threshold", 45);
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
        
        PlayerData data = plugin.getDataManager().getData(player);
        if (data == null) return;
        
        long now = System.currentTimeMillis();
        data.getHitTimes().add(now);
        data.getHitTimes().removeIf(time -> now - time > 1000);
        
        double cps = data.getHitTimes().size();
        
        if (cps > MAX_CPS) {
            flag(player, data, String.format("CPS:%.1f>%.1f", cps, MAX_CPS));
        }
        
        float yawDiff = Math.abs(player.getLocation().getYaw() - target.getLocation().getYaw());
        if (yawDiff > ROTATION_THRESHOLD && cps > 10) {
            flag(player, data, String.format("yaw:%.1f>%.1f", yawDiff, ROTATION_THRESHOLD));
        }
    }
}
