/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.lobbysystem.manager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import tk.craftronixmc.craftronixapi.spigot.misc.Message;
import tk.craftronixmc.lobbysystem.LobbySystem;
import tk.craftronixmc.lobbysystem.entities.Server;
import tk.craftronixmc.lobbysystem.utils.BungeeUtils;
import tk.craftronixmc.lobbysystem.utils.ItemBuilder;

import java.util.List;
import java.util.Random;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 15:50                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class InventoryManager extends BukkitRunnable implements Listener {

    private LobbySystem lobbySystem;
    private Inventory navigator;
    private Inventory cityBuildSelector;
    private Inventory lobbySwitcher;
    private Inventory playerHider;
    private Inventory settings;

    public InventoryManager(LobbySystem lobbySystem) {
        this.lobbySystem = lobbySystem;
        initInventories();
        runTaskTimer(lobbySystem, 0, 20);
        Bukkit.getPluginManager().registerEvents(this, lobbySystem);
    }

    private void initInventories() {
        final ItemStack glass = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 7).setName("§r").build();
        /*
            build the naviation inventory
         */
        {
            this.navigator = Bukkit.createInventory(null, 3 * 9, "§2•§a● Navigation");
            for (int i = 0; i < navigator.getSize(); i++) {
                navigator.setItem(i, glass);
            }
            this.navigator.setItem(13, new ItemBuilder(Material.CHEST, 1).setName("§2•§a● CityBuild").setLore("§7Click to see all §aavailable §7citybuild servers§8.").build());
        }

        /*
            build the citybuild selection
         */
        {
            this.cityBuildSelector = Bukkit.createInventory(null, 3 * 9, "§2•§a● Navigation§b");
            for (int i = 0; i < cityBuildSelector.getSize(); i++) {
                cityBuildSelector.setItem(i, glass);
            }
        }
        /*
            build the lobby switcher
         */
        {
            this.lobbySwitcher = Bukkit.createInventory(null, 9, "§2•§a● Navigation§b");
            for (int i = 0; i < lobbySwitcher.getSize(); i++) {
                lobbySwitcher.setItem(i, glass);
            }
        }
        /*
            build the playerhider
         */
        {
            this.playerHider = Bukkit.createInventory(null, InventoryType.HOPPER, "§2•§a● Playerhider§8│ §7Setup");
            for (int i = 0; i < playerHider.getSize(); i++) {
                playerHider.setItem(i, glass);
            }
            this.playerHider.setItem(1, new ItemBuilder(Material.INK_SACK, 1, 10).setName("§2•§a● Show all players").build());
            this.playerHider.setItem(3, new ItemBuilder(Material.INK_SACK, 1, 1).setName("§4•§c● Hide all players").build());
        }
                /*
            build the settings
         */
        {
            this.settings = Bukkit.createInventory(null, 18, "§2•§a● Position-Synchronisation");
            for (int i = 0; i < settings.getSize(); i++) {
                settings.setItem(i, glass);
            }
            settings.setItem(13, new ItemBuilder(Material.FEATHER, 1).setName("§2•§a● Position-Synchronisation").setLore(
                    "§r",
                    "§7Synchronize your position between the",
                    "§aLobby-Servers§8.",
                    "§r",
                    "§aClick §7to change option§8!"
            ).build());
        }

    }

    public void openNavigator(Player player) {
        player.openInventory(navigator);
        player.playSound(player.getLocation(), Sound.CHEST_OPEN, 10, 10);
    }

    @Override
    public void run() {
        if (cityBuildSelector == null) return;

        final List<Server> servers = lobbySystem.getServerManager().getServers();
        {
            int i = 0;
            for (Server server : servers) {
                if (server.getServerName().contains("CityBuild")) {
                    i++;
                    if (server.isOnline()) {
                        cityBuildSelector.setItem(i + 10, new ItemBuilder(Material.CHEST, server.getOnlinePlayers()).setName("§2•§a● " + server.getServerName()).setLore("§aClick §7to join§8!").build());
                    } else {
                        cityBuildSelector.setItem(i + 10, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 7).setName("§r").build());
                    }
                }
            }
        }
        {
            int i = 0;
            for (Server server : servers) {
                if (server.getServerName().contains("Lobby")) {
                    if (server.isOnline()) {
                        lobbySwitcher.setItem(i, new ItemBuilder(Material.CHEST, server.getOnlinePlayers()).setName("§2•§a● " + server.getServerName()).setLore("§7Click to join this lobby§8.").build());
                    } else {
                        lobbySwitcher.setItem(i, new ItemBuilder(Material.BARRIER, 0).setName("§c§l" + server.getServerName()).build());
                    }
                    i++;
                }
            }
        }
    }

    @EventHandler
    public void on(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        if (event.getItem().getItemMeta() == null) return;
        final Player player = event.getPlayer();
        if (event.getAction().name().contains("RIGHT")) {
            final String displayName = event.getItem().getItemMeta().getDisplayName();
            if (displayName.contains("Navigation")) {
                openNavigator(player);
            } else if (displayName.contains("Settings")) {
                Inventory inventory = Bukkit.createInventory(null, 18, "§2•§a● Position-Synchronisation");
                inventory.setContents(settings.getContents());
                if (lobbySystem.getSettingsManager().isToggled(player.getUniqueId())) {
                    inventory.setItem(4, new ItemBuilder(Material.INK_SACK, 1, 10).setName("§2•§a● §aOn").build());
                } else {
                    inventory.setItem(4, new ItemBuilder(Material.INK_SACK, 1, 1).setName("§4•§c● §cOff").build());
                }
                player.openInventory(inventory);
                player.playSound(player.getLocation(), Sound.CHEST_OPEN, 10, 10);
            } else if (displayName.contains("Playerhider")) {
                player.openInventory(playerHider);
                player.playSound(player.getLocation(), Sound.CHEST_OPEN, 10, 10);
            } else if (displayName.contains("Lobbyswitcher")) {
                player.openInventory(lobbySwitcher);
                player.playSound(player.getLocation(), Sound.CHEST_OPEN, 10, 10);
            }
        }
    }

    @EventHandler
    public void on(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        if (event.getClickedInventory() == null) return;
        final String title = event.getClickedInventory().getTitle();
        if (title.equals("§2•§a● Navigation")) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;
            if (!event.getCurrentItem().hasItemMeta()) return;
            final String displayName = event.getCurrentItem().getItemMeta().getDisplayName();
            if (displayName.equals("§2•§a● CityBuild")) {
                player.openInventory(cityBuildSelector);
                player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 1f, 1f);
            }
        } else if (title.equals("§2•§a● Position-Synchronisation")) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;
            if (!event.getCurrentItem().hasItemMeta()) return;
            final String displayName = event.getCurrentItem().getItemMeta().getDisplayName();
            final SettingsManager settingsManager = lobbySystem.getSettingsManager();
            if (displayName.contains("Position") || displayName.contains("Off") || displayName.contains("On")) {
                settingsManager.togglePosition(player.getUniqueId(), !settingsManager.isToggled(player.getUniqueId()));
                player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1f, 1f);

                if (settingsManager.isToggled(player.getUniqueId())) {
                    event.getClickedInventory().setItem(4, new ItemBuilder(Material.INK_SACK, 1, 10).setName("§2•§a● §aOn").build());
                } else {
                    event.getClickedInventory().setItem(4, new ItemBuilder(Material.INK_SACK, 1, 1).setName("§4•§c● §cOff").build());
                }
            }


        } else if (title.equals("§2•§a● Playerhider§8│ §7Setup")) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;
            if (!event.getCurrentItem().hasItemMeta()) return;
            final String displayName = event.getCurrentItem().getItemMeta().getDisplayName();
            switch (displayName) {
                case "§2•§a● Show all players":
                    lobbySystem.getPlayerManager().getHideEnabled().remove(player);
                    lobbySystem.getPlayerManager().function(player);
                    player.sendMessage(Message.prefix("CraftronixMC") + "§7You are able to see all players now§8.");
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 10);
                    player.closeInventory();
                    break;
                case "§4•§c● Hide all players":
                    lobbySystem.getPlayerManager().getHideEnabled().add(player);
                    lobbySystem.getPlayerManager().function(player);
                    player.sendMessage(Message.prefix("CraftronixMC") + "§7All players are §ainvisible §7for now§8.");
                    player.playSound(player.getLocation(), Sound.NOTE_BASS_GUITAR, 10, 10);
                    player.closeInventory();
                    break;
            }
        } else if (title.equals("§2•§a● Navigation§b")) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;
            if (!event.getCurrentItem().hasItemMeta()) return;
            final String displayName = event.getCurrentItem().getItemMeta().getDisplayName();
            if (displayName.startsWith("§2•§a● ")) {
                String serverName = displayName.replace("§2•§a● ", "");
                if (!Bukkit.getServerName().equalsIgnoreCase(serverName)) {
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 10f, 10f);
                    BungeeUtils.sendPlayerToServer(lobbySystem, player, serverName);
                }
            }
        }
    }
}
