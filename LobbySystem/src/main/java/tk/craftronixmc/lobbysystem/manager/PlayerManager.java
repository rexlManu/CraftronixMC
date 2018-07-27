/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.lobbysystem.manager;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tk.craftronixmc.lobbysystem.LobbySystem;

import java.util.ArrayList;
import java.util.List;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 18:04                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class PlayerManager implements Listener {

    @Getter
    private List<Player> hideEnabled;

    public PlayerManager(LobbySystem lobbySystem) {
        this.hideEnabled = new ArrayList<>();
        Bukkit.getPluginManager().registerEvents(this, lobbySystem);
    }

    @EventHandler
    public void on(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        function(player);
    }

    public void function(Player player) {
        if (hideEnabled.contains(player)) {
            Bukkit.getOnlinePlayers().forEach(o -> {
                player.hidePlayer(o);
                if (hideEnabled.contains(o)) {
                    o.hidePlayer(player);
                } else o.showPlayer(player);
            });
        } else {
            Bukkit.getOnlinePlayers().forEach(player::showPlayer);
        }
    }

}
