package com.zenex.anticheat.data;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerData {
    
    private final Player player;
    private final Map<String, Double> violations = new HashMap<>();
    private final List<Long> hitTimes = new ArrayList<>();
    private final List<Long> placeTimes = new ArrayList<>();
    private final List<Long> breakTimes = new ArrayList<>();
    
    private Location lastLocation;
    private double lastDeltaY;
    private double fallDistance;
    private double actualFallDistance;
    private double waterTime;
    private boolean bypass = false;
    public double buffer = 0;
    
    public PlayerData(Player player) {
        this.player = player;
    }
    
    public double addViolation(String check, double amount) {
        return violations.merge(check, amount, Double::sum);
    }
    
    public void resetViolation(String check) {
        violations.remove(check);
    }
    
    public double getViolation(String check) {
        return violations.getOrDefault(check, 0.0);
    }
    
    public Map<String, Double> getViolations() {
        return violations;
    }
    
    // Getters and Setters
    public Player getPlayer() { return player; }
    public List<Long> getHitTimes() { return hitTimes; }
    public List<Long> getPlaceTimes() { return placeTimes; }
    public List<Long> getBreakTimes() { return breakTimes; }
    public Location getLastLocation() { return lastLocation; }
    public void setLastLocation(Location loc) { this.lastLocation = loc; }
    public double getLastDeltaY() { return lastDeltaY; }
    public void setLastDeltaY(double d) { this.lastDeltaY = d; }
    public double getFallDistance() { return fallDistance; }
    public void setFallDistance(double d) { this.fallDistance = d; }
    public double getActualFallDistance() { return actualFallDistance; }
    public void setActualFallDistance(double d) { this.actualFallDistance = d; }
    public double getWaterTime() { return waterTime; }
    public void setWaterTime(double d) { this.waterTime = d; }
    public boolean isBypass() { return bypass; }
    public void setBypass(boolean b) { this.bypass = b; }
}
