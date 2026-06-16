package com.zenex.anticheat.check;

import com.zenex.anticheat.ZenexAntiCheat;
import com.zenex.anticheat.check.movement.*;
import com.zenex.anticheat.check.combat.*;
import com.zenex.anticheat.check.interaction.*;
import com.zenex.anticheat.data.PlayerData;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CheckManager {
    
    private final ZenexAntiCheat plugin;
    private final List<Check> checks = new ArrayList<>();
    
    public CheckManager(ZenexAntiCheat plugin) {
        this.plugin = plugin;
        registerChecks();
    }
    
    private void registerChecks() {
        // Movement
        checks.add(new SpeedCheck());
        checks.add(new FlyCheck());
        checks.add(new NoFallCheck());
        checks.add(new JesusCheck());
        checks.add(new PhaseCheck());
        
        // Combat
        checks.add(new KillAuraCheck());
        checks.add(new ReachCheck());
        checks.add(new VelocityCheck());
        checks.add(new AutoClickerCheck());
        
        // Interaction
        checks.add(new FastPlaceCheck());
        checks.add(new FastBreakCheck());
    }
    
    public void runChecks() {
        if (!plugin.getConfig().getBoolean("enabled", true)) return;
        
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            PlayerData data = plugin.getDataManager().getData(player);
            if (data == null || data.isBypass()) continue;
            
            for (Check check : checks) {
                if (check.isEnabled()) {
                    try {
                        check.check(player, data);
                    } catch (Exception e) {
                        plugin.getLogger().warning("Error in check " + check.getName());
                    }
                }
            }
        }
    }
    
    public List<Check> getChecks() {
        return checks;
    }
}
