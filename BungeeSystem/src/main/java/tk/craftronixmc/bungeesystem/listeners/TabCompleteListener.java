/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.bungeesystem.listeners;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import tk.craftronixmc.bungeesystem.BungeeSystem;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 10:19                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class TabCompleteListener implements Listener {

    private BungeeSystem system;

    public TabCompleteListener(BungeeSystem system) {
        this.system = system;
        ProxyServer.getInstance().getPluginManager().registerListener(system, this);
    }

    @EventHandler
    public void on(TabCompleteEvent event) {
        if (event.getSender() instanceof ProxiedPlayer) {
            final ProxiedPlayer player = (ProxiedPlayer) event.getSender();
            if (event.getCursor().startsWith("/")) {
                if (!player.hasPermission("craftronix.admin")) {
                    event.getSuggestions().clear();
                }
            }
        }
    }
}
