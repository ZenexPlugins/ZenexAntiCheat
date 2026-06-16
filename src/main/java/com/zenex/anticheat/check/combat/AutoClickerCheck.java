package com.zenex.anticheat.check.combat;

import com.zenex.anticheat.check.Check;
import com.zenex.anticheat.check.CheckType;
import com.zenex.anticheat.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AutoClickerCheck extends Check implements Listener {
    
    private final double MAX_CPS;
    
    public AutoClickerCheck() {
        super("AutoClicker", CheckType.AUTOCLICKER);
        this.MAX_CPS = plugin.getConfig().getDouble("checks.autoclicker.max-cps", 16);
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
        if (player.hasPermission("zenexanticheat.bypass")) return;
        
        PlayerData data = plugin.getDataManager().getData(player);
        if (data == null) return;
        
        long now = System.currentTimeMillis();
        data.getHitTimes().add(now);
        data.getHitTimes().removeIf(time -> now - time > 2000);
        
        double cps = data.getHitTimes().size() / 2.0;
        
        if (cps > MAX_CPS) {
            flag(player, data, String.format("CPS:%.1f>%.1f", cps, MAX_CPS));
        }
    }
}
