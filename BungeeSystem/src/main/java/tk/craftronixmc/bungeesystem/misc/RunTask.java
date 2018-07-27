/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.bungeesystem.misc;

import com.arangodb.entity.BaseDocument;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import tk.craftronixmc.bungeesystem.BungeeSystem;

import java.util.concurrent.TimeUnit;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 14:15                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class RunTask {

    public RunTask(BungeeSystem system) {
        ProxyServer.getInstance().getScheduler().schedule(system, () -> {
            final BaseDocument onlineplayers = new BaseDocument("onlineplayers");
            onlineplayers.addAttribute("onlineplayers", ProxyServer.getInstance().getOnlineCount());
            system.getConnector().getDatabase().collection("settings").updateDocument("onlineplayers", onlineplayers);
        }, 0, 1, TimeUnit.SECONDS);
    }
}
