package pw.landon.safeguard;

import pw.landon.safeguard.commands.ReloadCommand;
import pw.landon.safeguard.commands.SafeguardCommand;
import pw.landon.safeguard.handlers.JoinEvent;
import pw.landon.safeguard.handlers.LoginEvent;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SafeguardPlugin extends JavaPlugin {

    private static SafeguardPlugin instance;


    @Override
    public void onEnable() {
        instance = this;
        this.registerCommands();
        this.registerEvents();
        this.getConfig().options().copyDefaults();
        this.saveDefaultConfig();
        System.out.println("Your server is protected by SAFEGUARD.");

    }

    private void registerCommands() {
        this.getCommand("safeguard").setExecutor(new SafeguardCommand(this));
        this.getCommand("sgreload").setExecutor(new ReloadCommand(this));
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new LoginEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new JoinEvent(this), this);
    }

    public static SafeguardPlugin getInstance() {
        return instance;
    }
}
