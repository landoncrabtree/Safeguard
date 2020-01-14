package pw.landon.safeguard.handlers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.StringUtil;
import pw.landon.safeguard.SafeguardPlugin;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import pw.landon.safeguard.utilities.Chat;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class JoinEvent implements Listener {

    private SafeguardPlugin main;
    public JoinEvent(SafeguardPlugin main) {this.main = SafeguardPlugin.getInstance();}

    private Random random = ThreadLocalRandom.current();
    private static ArrayList<Player> close = new ArrayList<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void joinEvent(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        if (main.getConfig().getBoolean("options.captcha_enabled")) {
            if (!(player.hasPermission(main.getConfig().getString("permissions.captcha_bypass")))) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        generateCaptcha(player);
                    }
                }.runTaskLater(main, 1);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void inventoryInteract(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals(Chat.color(main.getConfig().getString("options.captcha_gui_title")))) {
            Player player = (Player) event.getWhoClicked();
            if (!(event.getCurrentItem() == null || event.getCurrentItem() == new ItemStack(Material.AIR))) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(Chat.color("&a&lCLICK TO PASS CAPTCHA"))) {
                    close.add(player);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.closeInventory();
                        }
                    }.runTaskLater(main, 1);
                }
            }
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void inventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getTitle().equals(Chat.color(main.getConfig().getString("options.captcha_gui_title")))) {
            Player player = (Player) event.getPlayer();
            if (close.contains(player)) {
                close.remove(player);
            } else {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        generateCaptcha(player);
                    }
                }.runTaskLater(main, 1);
            }
        }
    }

    private void generateCaptcha(Player player) {
        ItemStack[] array;
        array = new ItemStack[27];
        array[0] = new ItemStack(Material.LAPIS_ORE);
        array[1] = new ItemStack(Material.RED_MUSHROOM);
        array[2] = new ItemStack(Material.ANVIL);
        array[3] = new ItemStack(Material.DEAD_BUSH);
        array[4] = new ItemStack(Material.OBSIDIAN);
        array[5] = new ItemStack(Material.HOPPER);
        array[6] = new ItemStack(Material.EGG);
        array[7] = new ItemStack(Material.LAVA_BUCKET);
        array[8] = new ItemStack(Material.PAINTING);
        array[9] = new ItemStack(Material.ARROW);
        array[10] = new ItemStack(Material.PRISMARINE_SHARD);
        array[11] = new ItemStack(Material.MINECART);
        array[12] = new ItemStack(Material.RAW_BEEF);
        array[13] = new ItemStack(Material.LADDER);
        array[14] = new ItemStack(Material.SUGAR_CANE);
        array[15] = new ItemStack(Material.QUARTZ);
        array[16] = new ItemStack(Material.WEB);
        array[17] = new ItemStack(Material.GHAST_TEAR);
        array[18] = new ItemStack(Material.DETECTOR_RAIL);
        array[19] = new ItemStack(Material.ICE);
        array[20] = new ItemStack(Material.INK_SACK);
        array[21] = new ItemStack(Material.SLIME_BALL);
        array[22] = new ItemStack(Material.SKULL_ITEM);
        array[23] = new ItemStack(Material.BAKED_POTATO);
        array[24] = new ItemStack(Material.MELON_SEEDS);
        array[25] = new ItemStack(Material.DRAGON_EGG);
        array[26] = new ItemStack(Material.TORCH);

        Inventory captchaGUI = Bukkit.createInventory(null, 27, Chat.color(main.getConfig().getString("options.captcha_gui_title")));
        for (int i = 0; i < 27; i++) {
            ItemMeta meta = array[i].getItemMeta();
            meta.setDisplayName(Chat.color("&c&lDO NOT CLICK"));
            array[i].setItemMeta(meta);
            captchaGUI.setItem(i, array[i]);
        }
        int correctCaptcha = random.nextInt((27 - 1) + 1) + 1;
        correctCaptcha = correctCaptcha - 1;
        ItemStack captchaItem = new ItemStack(array[correctCaptcha]);
        ItemMeta captchaMeta = captchaItem.getItemMeta();
        captchaMeta.setDisplayName(Chat.color("&a&lCLICK TO PASS CAPTCHA"));
        captchaItem.setItemMeta(captchaMeta);
        captchaGUI.setItem(correctCaptcha, captchaItem);
        player.openInventory(captchaGUI);
    }
}
