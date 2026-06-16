package com.zenex.anticheat.data;

import com.zenex.anticheat.ZenexAntiCheat;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataManager {
    
    private final ZenexAntiCheat plugin;
    private final Map<UUID, PlayerData> dataMap = new HashMap<>();
    
    public DataManager(ZenexAntiCheat plugin) {
        this.plugin = plugin;
    }
    
    public PlayerData getData(Player player) {
        return dataMap.computeIfAbsent(player.getUniqueId(), k -> new PlayerData(player));
    }
    
    public void removeData(Player player) {
        dataMap.remove(player.getUniqueId());
    }
    
    public void clearAll() {
        dataMap.clear();
    }
}
