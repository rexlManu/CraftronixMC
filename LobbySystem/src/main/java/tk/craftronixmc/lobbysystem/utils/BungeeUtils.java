/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.lobbysystem.utils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 16:45                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class BungeeUtils {

    public static void sendPlayerToServer(JavaPlugin javaPlugin, Player player, String serverName) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(serverName);
        player.sendPluginMessage(javaPlugin, "BungeeCord", out.toByteArray());
    }

    public static void registerBungeeChannel(JavaPlugin javaPlugin, Server server) {
        server.getMessenger().registerOutgoingPluginChannel(javaPlugin, "BungeeCord");

    }
}
