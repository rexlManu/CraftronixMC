/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.craftronixapi.bungeecord;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import tk.craftronixmc.craftronixapi.CraftronixAPI;
import tk.craftronixmc.craftronixapi.database.User;
import tk.craftronixmc.craftronixapi.rank.Rank;
import tk.craftronixmc.craftronixapi.rank.RankAPI;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 08:04                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class BungeeTrap extends Plugin implements Listener {

    private CraftronixAPI craftronixAPI;

    @Override
    public void onEnable() {
        this.craftronixAPI = new CraftronixAPI();
        this.craftronixAPI.onEnable();

        ProxyServer.getInstance().getPluginManager().registerListener(this, this);
    }

    @Override
    public void onDisable() {
        this.craftronixAPI.onDisable();
    }

    @EventHandler
    public void on(PostLoginEvent event) {
        final ProxiedPlayer player = event.getPlayer();
        if (!User.getUser(player.getUniqueId()).getProperties().containsKey("rank")) {
            RankAPI.setRank(player.getUniqueId(), Rank.SPIELER);
        }
    }
}
