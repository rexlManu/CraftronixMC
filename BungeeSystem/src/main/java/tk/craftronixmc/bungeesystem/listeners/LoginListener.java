/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.bungeesystem.listeners;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import tk.craftronixmc.bungeesystem.BungeeSystem;
import tk.craftronixmc.bungeesystem.manager.SettingManager;

import java.util.List;
import java.util.UUID;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 12:58                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class LoginListener implements Listener {

    private BungeeSystem system;

    public LoginListener(BungeeSystem system) {
        this.system = system;
        ProxyServer.getInstance().getPluginManager().registerListener(system, this);
    }

    @EventHandler
    public void on(LoginEvent event) {
        final String name = event.getConnection().getName();
        final SettingManager manager = system.getSettingManager();
        if (manager.getSettingAsBoolean("maintenance")) {
            final List<String> whitelist = (List<String>) manager.getSettingAsObject("whitelist");
            if (!whitelist.contains(name)) {
                event.setCancelReason(new TextComponent("\n§8§m-------------------\n\n§7The server is currently under §amaintenance§8.\n\n§8§m-------------------\n"));
                event.setCancelled(true);
            }
        }
    }
}
