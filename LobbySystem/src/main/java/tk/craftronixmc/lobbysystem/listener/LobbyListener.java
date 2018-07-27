/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.lobbysystem.listener;

import com.arangodb.entity.BaseDocument;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import tk.craftronixmc.craftronixapi.database.User;
import tk.craftronixmc.craftronixapi.rank.Rank;
import tk.craftronixmc.craftronixapi.rank.RankAPI;
import tk.craftronixmc.craftronixapi.spigot.api.TitleAPI;
import tk.craftronixmc.craftronixapi.spigot.misc.Message;
import tk.craftronixmc.lobbysystem.LobbySystem;

import java.util.UUID;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 13:42                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class LobbyListener implements Listener {

    private LobbySystem lobbySystem;

    public LobbyListener(LobbySystem lobbySystem) {
        this.lobbySystem = lobbySystem;
        Bukkit.getPluginManager().registerEvents(this, lobbySystem);
    }

    @EventHandler
    public void on(PlayerItemHeldEvent event) {
        final Player player = event.getPlayer();
        player.playSound(player.getLocation(), Sound.WOOD_CLICK, 1, 10);
    }

    @EventHandler
    public void on(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        final Player player = event.getPlayer();
        final BaseDocument user = User.getUser(player.getUniqueId());
        if (lobbySystem.getSettingsManager().isToggled(player.getUniqueId())) {
            if (!user.getProperties().containsKey("location")) {
            /*
            no saved last location
             */
                if (lobbySystem.getLocationManager().getSpawn() != null) {
                    player.teleport(lobbySystem.getLocationManager().getSpawn());
                    saveLocation(player.getUniqueId(), lobbySystem.getLocationManager().getSpawn());
                }

            } else {
                player.teleport(readFromString(user.getAttribute("location").toString()));
            }
        } else {
            if (lobbySystem.getLocationManager().getSpawn() != null) {
                player.teleport(lobbySystem.getLocationManager().getSpawn());
            }
        }

        player.setGameMode(GameMode.ADVENTURE);
        player.setHealthScale(20);
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setLevel(0);
        player.setExp(0);


        lobbySystem.getPlayerScoreboard().set(player);
        lobbySystem.getItemManager().giveItems(player);
        TitleAPI.clearTitle(player);
        TitleAPI.sendTitle(player, 5, 10, 5, Message.prefix("CraftronixMC"), "§7Welcome§8, §a" + player.getName());

        RankAPI.updateTablist();
    }

    @EventHandler
    public void on(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        if (!RankAPI.hasPermission(player.getUniqueId(), Rank.PRIME)) {
            event.setCancelled(true);
            player.sendMessage(Message.prefix("CraftronixMC") + "§7You can't use the chat in the lobby§8!");
            player.sendMessage(Message.prefix("CraftronixMC") + "§7If you want to, please check out our store: §ahttp://store.craftronixmc.tk");
            return;
        }

        final Rank rank = RankAPI.getRankFromPlayer(player.getUniqueId());
        event.setFormat(rank.getDisplayName() + player.getName() + " §8» §f" + event.getMessage().replace("%", "%%"));
    }

    @EventHandler
    public void on(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        saveLocation(event.getPlayer().getUniqueId(), event.getPlayer().getLocation());
    }

    public Location readFromString(String read) {
        final String[] data = read.split(";");
        return new Location(Bukkit.getWorld(data[0]), Double.valueOf(data[1]), Double.valueOf(data[2]), Double.valueOf(data[3]), Float.valueOf(data[4]), Float.valueOf(data[5]));
    }

    public void saveLocation(UUID uuid, Location location) {
        final BaseDocument user = User.getUser(uuid);
        if (!user.getProperties().containsKey("location")) {

            user.addAttribute("location", location.getWorld().getName() + ";" + location.getX() + ";" + location.getY() + ";" + location.getZ() + ";" + location.getYaw() + ";" + location.getPitch());
        } else {

            user.updateAttribute("location", location.getWorld().getName() + ";" + location.getX() + ";" + location.getY() + ";" + location.getZ() + ";" + location.getYaw() + ";" + location.getPitch());
        }
        User.updateDocument(user);
    }
}
