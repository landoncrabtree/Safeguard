package pw.landon.safeguard.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pw.landon.safeguard.SafeguardPlugin;

public class ReloadCommand implements CommandExecutor {

    private SafeguardPlugin main;
    public ReloadCommand(SafeguardPlugin main) {this.main = SafeguardPlugin.getInstance();}

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission(main.getConfig().getString("permissions.reload_command"))) {
                main.saveDefaultConfig();
                main.reloadConfig();
                player.sendMessage(ChatColor.GREEN + "Configuration reloaded.");
                return true;
            }
        } else {
            main.saveDefaultConfig();
            main.reloadConfig();
            System.out.println("Configuration reloaded.");
            return true;
        }
        return true;
    }

}
