package com.zenex.anticheat;

import com.zenex.anticheat.check.CheckManager;
import com.zenex.anticheat.data.DataManager;
import com.zenex.anticheat.data.ViolationManager;
import com.zenex.anticheat.punishment.PunishmentManager;
import com.zenex.anticheat.command.AntiCheatCommand;
import com.zenex.anticheat.listener.AntiCheatListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ZenexAntiCheat extends JavaPlugin {
    
    private static ZenexAntiCheat instance;
    private CheckManager checkManager;
    private DataManager dataManager;
    private ViolationManager violationManager;
    private PunishmentManager punishmentManager;
    
    @Override
    public void onEnable() {
        instance = this;
        
        saveDefaultConfig();
        saveResource("messages.yml", false);
        
        dataManager = new DataManager(this);
        violationManager = new ViolationManager(this);
        checkManager = new CheckManager(this);
        punishmentManager = new PunishmentManager(this);
        
        getCommand("anticheat").setExecutor(new AntiCheatCommand(this));
        getCommand("acadmin").setExecutor(new AntiCheatCommand(this));
        
        Bukkit.getPluginManager().registerEvents(new AntiCheatListener(this), this);
        
        // Запуск проверок
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            checkManager.runChecks();
        }, 0L, 1L);
        
        getLogger().info("✅ ZenexAntiCheat v" + getDescription().getVersion() + " enabled!");
    }
    
    @Override
    public void onDisable() {
        getLogger().info("ZenexAntiCheat disabled!");
    }
    
    public static ZenexAntiCheat getInstance() {
        return instance;
    }
    
    public CheckManager getCheckManager() {
        return checkManager;
    }
    
    public DataManager getDataManager() {
        return dataManager;
    }
    
    public ViolationManager getViolationManager() {
        return violationManager;
    }
    
    public PunishmentManager getPunishmentManager() {
        return punishmentManager;
    }
}
