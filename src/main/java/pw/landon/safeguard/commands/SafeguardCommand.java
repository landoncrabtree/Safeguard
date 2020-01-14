package pw.landon.safeguard.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pw.landon.safeguard.SafeguardPlugin;
import pw.landon.safeguard.utilities.Chat;

public class SafeguardCommand implements CommandExecutor {

    private SafeguardPlugin main;
    public SafeguardCommand(SafeguardPlugin main) {this.main = SafeguardPlugin.getInstance();}

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        int argumentCount = args.length;
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!(player.hasPermission(main.getConfig().getString("permissions.help_command")))) {
                player.sendMessage("&bThis server is running Safeguard Server Protection.");
            } else {
                switch (argumentCount) {
                    case 0:
                        player.sendMessage(Chat.color("&b&lSafeguard Server Protection"));
                        player.sendMessage(Chat.color("&7- &3/sg reload &bReload config."));
                        break;
                    case 1:
                        String arg1 = args[0].toLowerCase();
                        if (arg1.equals("reload")) {
                            player.performCommand("sgreload");
                        }
                        break;
                    default:
                        player.sendMessage(Chat.color("&b&lSafeguard Server Protection"));
                        player.sendMessage(Chat.color("&7- &3/sg reload &bReload config."));
                        break;
                }
            }
        } else {
            switch (argumentCount) {
                case 0:
                    System.out.println("Safeguard Server Protection");
                    System.out.println("- /sg reload: Reload config.");
                    break;
                case 1:
                    String arg1 = args[0].toLowerCase();
                    if (arg1.equals("reload")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "sgreload");
                    }
                    break;
                default:
                    System.out.println("Safeguard Server Protection");
                    System.out.println("- /sg reload: Reload config.");
                    break;
            }
        }
        return true;
    }

}
