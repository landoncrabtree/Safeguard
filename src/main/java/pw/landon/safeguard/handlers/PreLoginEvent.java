package pw.landon.safeguard.handlers;

import me.gong.mcleaks.MCLeaksAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import pw.landon.safeguard.SafeguardPlugin;
import pw.landon.safeguard.utilities.Chat;

import java.util.UUID;

public class PreLoginEvent implements Listener {

    private SafeguardPlugin main;
    public PreLoginEvent(SafeguardPlugin main) {this.main = SafeguardPlugin.getInstance();}

    @EventHandler(priority = EventPriority.HIGHEST)
    public void preLoginEvent(AsyncPlayerPreLoginEvent event) {
        UUID uuid = event.getUniqueId();
        //final Optional<Boolean> cachedUUID = main.checkMcLeaks().getCachedCheck(uuid);
        final MCLeaksAPI.Result uuidResult = main.checkMcLeaks().checkAccount(uuid);
        if (uuidResult.isMCLeaks()) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Chat.color(main.getConfig().getString("messages.mcleaks_kick")));
        }

    }
}
