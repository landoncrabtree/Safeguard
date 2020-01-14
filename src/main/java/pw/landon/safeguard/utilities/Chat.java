package pw.landon.safeguard.utilities;

import org.bukkit.ChatColor;

public class Chat {
    private Chat() { }

    public static String color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }
}