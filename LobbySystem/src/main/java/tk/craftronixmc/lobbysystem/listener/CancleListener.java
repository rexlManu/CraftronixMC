/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.lobbysystem.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import tk.craftronixmc.lobbysystem.LobbySystem;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 15:05                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class CancleListener implements Listener {

    private LobbySystem lobbySystem;

    public CancleListener(LobbySystem lobbySystem) {
        this.lobbySystem = lobbySystem;
        Bukkit.getPluginManager().registerEvents(this, lobbySystem);
    }

    @EventHandler
    public void on(BlockBreakEvent event) {
        event.setCancelled(event.getPlayer().getGameMode() == GameMode.SURVIVAL);
    }

    @EventHandler
    public void on(BlockPlaceEvent event) {
        event.setCancelled(event.getPlayer().getGameMode() == GameMode.SURVIVAL);
    }

    @EventHandler
    public void on(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void on(PlayerPickupItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void on(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void on(LeavesDecayEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void on(CreatureSpawnEvent event) {
        event.setCancelled(event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

    @EventHandler
    public void on(EntityExplodeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void on(EntityDamageEvent event) {
        event.setCancelled(true);
    }

}
