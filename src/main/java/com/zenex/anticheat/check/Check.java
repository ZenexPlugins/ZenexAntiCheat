package com.zenex.anticheat.check;

import com.zenex.anticheat.ZenexAntiCheat;
import com.zenex.anticheat.data.PlayerData;
import com.zenex.anticheat.punishment.PunishmentType;
import org.bukkit.entity.Player;

public abstract class Check {
    
    protected final ZenexAntiCheat plugin;
    protected final String name;
    protected final CheckType type;
    protected boolean enabled;
    protected double buffer;
    protected PunishmentType punishment;
    protected int vlThreshold;
    
    public Check(String name, CheckType type) {
        this.plugin = ZenexAntiCheat.getInstance();
        this.name = name;
        this.type = type;
        loadConfig();
    }
    
    public void loadConfig() {
        String path = "checks." + type.toString().toLowerCase() + "." + name.toLowerCase();
        this.enabled = plugin.getConfig().getBoolean(path + ".enabled", true);
        this.buffer = plugin.getConfig().getDouble(path + ".buffer", 0.1);
        this.vlThreshold = plugin.getConfig().getInt(path + ".vl-threshold", 10);
        String pun = plugin.getConfig().getString(path + ".punishment", "WARN");
        this.punishment = PunishmentType.valueOf(pun);
    }
    
    public abstract void check(Player player, PlayerData data);
    
    public void flag(Player player, PlayerData data, String details) {
        if (!enabled) return;
        
        double vl = data.addViolation(name, 1.0);
        data.buffer = Math.min(data.buffer + buffer, buffer * 5);
        
        plugin.getViolationManager().handleViolation(player, this, vl, details);
        
        if (vl >= vlThreshold) {
            plugin.getPunishmentManager().punish(player, this, vl);
            data.resetViolation(name);
            data.buffer = 0;
        }
    }
    
    public String getName() { return name; }
    public CheckType getType() { return type; }
    public boolean isEnabled() { return enabled; }
    public PunishmentType getPunishment() { return punishment; }
}
