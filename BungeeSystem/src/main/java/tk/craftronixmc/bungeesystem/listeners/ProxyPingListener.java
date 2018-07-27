/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.bungeesystem.listeners;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import tk.craftronixmc.bungeesystem.BungeeSystem;
import tk.craftronixmc.bungeesystem.manager.SettingManager;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 10:07                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class ProxyPingListener implements Listener {

    private BungeeSystem system;

    public ProxyPingListener(BungeeSystem system) {
        this.system = system;
        ProxyServer.getInstance().getPluginManager().registerListener(system, this);
    }

    @EventHandler
    public void on(ProxyPingEvent event) {
        final ServerPing response = event.getResponse();
        final SettingManager settingManager = system.getSettingManager();
        final int onlineCount = ProxyServer.getInstance().getOnlineCount();
        response.setPlayers(new ServerPing.Players(settingManager.getSettingAsInt("slots"), onlineCount, new ServerPing.PlayerInfo[]{}));
        response.setDescriptionComponent(new TextComponent(settingManager.getSetting("motd1") + "\n" + settingManager.getSetting("motd2")));
        event.setResponse(response);
    }
}
