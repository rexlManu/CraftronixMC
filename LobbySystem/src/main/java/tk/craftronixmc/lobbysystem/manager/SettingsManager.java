/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.lobbysystem.manager;

import com.arangodb.ArangoCollection;
import com.arangodb.entity.BaseDocument;
import tk.craftronixmc.craftronixapi.database.User;
import tk.craftronixmc.lobbysystem.LobbySystem;

import java.util.UUID;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 18:15                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class SettingsManager {

    private LobbySystem lobbySystem;
    private ArangoCollection arangoCollection;

    public SettingsManager(LobbySystem lobbySystem) {
        this.lobbySystem = lobbySystem;
        this.arangoCollection = lobbySystem.getConnector().getDatabase().collection("players");
    }

    public void togglePosition(UUID uuid, boolean toggle) {
        final BaseDocument user = User.getUser(uuid);
        user.updateAttribute("Position-Synchronisation", toggle);
        User.updateDocument(user);
    }

    public boolean isToggled(UUID uuid) {
        final BaseDocument user = User.getUser(uuid);
        if (!user.getProperties().containsKey("Position-Synchronisation")) {
            user.addAttribute("Position-Synchronisation", false);
            User.updateDocument(user);
            return true;
        } else {
            return (boolean) user.getAttribute("Position-Synchronisation");
        }
    }
}
