/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.bungeesystem.manager;

import com.arangodb.ArangoCollection;
import com.arangodb.entity.BaseDocument;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import tk.craftronixmc.bungeesystem.BungeeSystem;
import tk.craftronixmc.craftronixapi.database.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 11:29                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class PrivatMessageManager {

    private BungeeSystem system;
    final ArangoCollection collectionPlayers;
    private Map<ProxiedPlayer, ProxiedPlayer> lastMessage;

    public PrivatMessageManager(BungeeSystem system) {
        this.system = system;
        this.lastMessage = new HashMap<>();
        try {
            system.getConnector().getDatabase().createCollection("players");
        } catch (Exception ignored) {
        }
        this.collectionPlayers = system.getConnector().getDatabase().collection("players");
    }

    public void setLastMessage(ProxiedPlayer sender, ProxiedPlayer receiver) {
        lastMessage.put(receiver, sender);
    }

    public ProxiedPlayer getLastMessager(ProxiedPlayer player) {
        return lastMessage.get(player);
    }

    public void toggleMSG(UUID uuid, boolean toggle) {
        final BaseDocument user = User.getUser(uuid);
        user.updateAttribute("msg", toggle);
        User.updateDocument(user);
    }

    public boolean isToggled(UUID uuid) {
        final BaseDocument user = User.getUser(uuid);
        if (!user.getProperties().containsKey("msg")) {
            user.addAttribute("msg", true);
            User.updateDocument(user);
            return true;
        } else {
            return (boolean) user.getAttribute("msg");
        }
    }
}
