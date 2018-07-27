/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.bungeesystem.listeners;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import tk.craftronixmc.bungeesystem.BungeeSystem;
import tk.craftronixmc.bungeesystem.manager.SettingManager;

import java.util.Arrays;
import java.util.List;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 10:16                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class ChatListener implements Listener {

    private BungeeSystem system;
    private List<String> redirectToHelp;

    public ChatListener(BungeeSystem system) {
        this.system = system;
        this.redirectToHelp = Arrays.asList("/help", "/ver", "/pl", "/plugins");
        ProxyServer.getInstance().getPluginManager().registerListener(system, this);
    }

    @EventHandler
    public void on(ChatEvent event){
        final SettingManager settingManager = system.getSettingManager();
        if(event.getSender() instanceof ProxiedPlayer){
            final ProxiedPlayer player = (ProxiedPlayer) event.getSender();
            for (String s : redirectToHelp) {
                final String message = event.getMessage();
                if(message.startsWith(s)){
                    player.sendMessage(settingManager.getPrefix()+"If you need §ahelp §7please type in §a/Support §8[§aQuestion§8]");
                    event.setCancelled(true);
                }
            }
        }
    }
}
