package com.zenex.anticheat.command;

import com.zenex.anticheat.ZenexAntiCheat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AntiCheatCommand implements CommandExecutor {
    
    private final ZenexAntiCheat plugin;
    
    public AntiCheatCommand(ZenexAntiCheat plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("zenexanticheat.admin")) {
            sender.sendMessage(plugin.getConfig().getString("messages.no-permission", "&c❌ У вас нет прав!").replace("&", "§"));
            return true;
        }
        
        if (args.length == 0) {
            sender.sendMessage(plugin.getConfig().getString("messages.command-usage", "&c/anticheat <reload|info|alerts|check>").replace("&", "§"));
            return true;
        }
        
        switch (args[0].toLowerCase()) {
            case "reload":
                plugin.reloadConfig();
                sender.sendMessage(plugin.getConfig().getString("messages.reload", "&a✅ Античит перезагружен!").replace("&", "§"));
                break;
                
            case "info":
                String info = plugin.getConfig().getString("messages.command-info", "&6=== ZenexAntiCheat Info ===\n&7Версия: &e{version}\n&7Режим: &e{mode}\n&7Проверок активно: &e{checks}")
                    .replace("{version}", plugin.getDescription().getVersion())
                    .replace("{mode}", plugin.getConfig().getString("mode", "NORMAL"))
                    .replace("{checks}", String.valueOf(plugin.getCheckManager().getChecks().size()))
                    .replace("&", "§");
                sender.sendMessage(info);
                break;
                
            case "alerts":
                // TODO: Вкл/выкл алерты
                sender.sendMessage("§aАлерты переключены!");
                break;
                
            default:
                sender.sendMessage(plugin.getConfig().getString("messages.command-usage", "&c/anticheat <reload|info|alerts|check>").replace("&", "§"));
        }
        return true;
    }
}
