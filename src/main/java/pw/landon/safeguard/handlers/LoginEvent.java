package pw.landon.safeguard.handlers;

import org.bukkit.ChatColor;
import pw.landon.safeguard.SafeguardPlugin;
import pw.landon.safeguard.methods.CheckIP;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import pw.landon.safeguard.utilities.Chat;

import java.io.IOException;

public class LoginEvent implements Listener {

    private SafeguardPlugin main;
    public LoginEvent(SafeguardPlugin main) {this.main = SafeguardPlugin.getInstance();}

    @EventHandler(priority = EventPriority.HIGHEST)
    public void loginEvent(PlayerLoginEvent event) throws IOException {

        boolean prevent_vpn = main.getConfig().getBoolean("options.prevent_vpn");
        boolean prevent_proxy = main.getConfig().getBoolean("options.prevent_proxy");
        boolean prevent_high_fraud_score = main.getConfig().getBoolean("options.prevent_high_fraud_score");

        if (prevent_vpn) {
            if (!(event.getPlayer().hasPermission(main.getConfig().getString("permissions.vpn_proxy_fraud_bypass")))) {
                if (CheckIP.isUsingVPN(event.getRealAddress().getHostAddress())) {
                    event.disallow(PlayerLoginEvent.Result.KICK_OTHER, main.getConfig().getString("messages.vpn_kick").replace('&', '§'));
                    Bukkit.broadcast(Chat.color(main.getConfig().getString("messages.vpn_notify").replace("%player%", event.getPlayer().getName())), main.getConfig().getString("permissions.staff_notify"));
                    System.out.println(("[SafeGuard] %player% tried to connect with a VPN. Connection cancelled.").replace("%player%", event.getPlayer().getName()));

                } else {
                    event.allow();
                }
            }
        } else if (prevent_proxy) {
            if (!(event.getPlayer().hasPermission(main.getConfig().getString("permissions.vpn_proxy_fraud_bypass")))) {
                if (CheckIP.isUsingProxy(event.getRealAddress().getHostAddress())) {
                    event.disallow(PlayerLoginEvent.Result.KICK_OTHER, main.getConfig().getString("messages.proxy_kick").replace('&', '§'));
                    Bukkit.broadcast(Chat.color(main.getConfig().getString("messages.proxy_notify").replace("%player%", event.getPlayer().getName())), main.getConfig().getString("permissions.staff_notify"));
                    System.out.println(("[SafeGuard] %player% tried to connect with a proxy. Connection cancelled.").replace("%player%", event.getPlayer().getName()));

                } else {
                    event.allow();
                }
            }
        } else if (prevent_high_fraud_score) {
            if (!(event.getPlayer().hasPermission(main.getConfig().getString("permissions.vpn_proxy_fraud_bypass")))) {
                if (CheckIP.hasHighFraudScore(event.getRealAddress().getHostAddress())) {
                    event.disallow(PlayerLoginEvent.Result.KICK_OTHER, main.getConfig().getString("messages.fraud_score_kick").replace('&', '§'));
                    Bukkit.broadcast(Chat.color(main.getConfig().getString("messages.fraud_score_notify").replace("%player%", event.getPlayer().getName())), main.getConfig().getString("permissions.staff_notify"));
                    System.out.println(("[SafeGuard] %player% tried to connect with a high fraud score. Connection cancelled.").replace("%player%", event.getPlayer().getName()));
                } else {
                    event.allow();
                }
            }
        }
    }
}
