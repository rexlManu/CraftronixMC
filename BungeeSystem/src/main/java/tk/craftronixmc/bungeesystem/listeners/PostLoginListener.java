/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.bungeesystem.listeners;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import tk.craftronixmc.bungeesystem.BungeeSystem;
import tk.craftronixmc.bungeesystem.manager.SettingManager;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 10:05                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class PostLoginListener implements Listener {

    private BungeeSystem system;

    public PostLoginListener(BungeeSystem system) {
        this.system = system;
        ProxyServer.getInstance().getPluginManager().registerListener(system, this);
    }

    @EventHandler
    public void on(PostLoginEvent event) {
        final ProxiedPlayer player = event.getPlayer();
        final SettingManager settingManager = system.getSettingManager();
        ProxyServer.getInstance().getPlayers().forEach(target -> {
            target.setTabHeader(new TextComponent(settingManager.getSetting("header").replace("%players%", ProxyServer.getInstance().getOnlineCount() + "")), new TextComponent(settingManager.getSetting("footer")));
        });
//        if(!player.getServer().getInfo().getName().contains("Lobby")){
//            player.connect(ProxyServer.getInstance().getServerInfo("Lobby-1"));
//        }
    }

    @EventHandler
    public void on(PlayerDisconnectEvent event){
        final ProxiedPlayer player = event.getPlayer();
        final SettingManager settingManager = system.getSettingManager();
        ProxyServer.getInstance().getPlayers().forEach(target -> {
            target.setTabHeader(new TextComponent(settingManager.getSetting("header").replace("%players%", ProxyServer.getInstance().getOnlineCount() + "")), new TextComponent(settingManager.getSetting("footer")));
        });
    }
}
