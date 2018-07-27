/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.craftronixapi.database;

import com.arangodb.ArangoCollection;
import com.arangodb.entity.BaseDocument;
import lombok.Getter;
import tk.craftronixmc.craftronixapi.CraftronixAPI;

import java.util.UUID;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 12:21                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class User {

    public static BaseDocument getUser(UUID uuid) {
        final ArangoCollection players = CraftronixAPI.getInstance().getConnector().getDatabase().collection("players");
        if (!players.documentExists(uuid.toString())) {
            final BaseDocument document = new BaseDocument(uuid.toString());
            players.insertDocument(document);
            return document;
        } else {
            return players.getDocument(uuid.toString(), BaseDocument.class);
        }
    }

    public static void updateDocument(BaseDocument baseDocument) {
        final ArangoCollection players = CraftronixAPI.getInstance().getConnector().getDatabase().collection("players");
        players.updateDocument(baseDocument.getKey(), baseDocument);
    }

    @Getter
    private UUID uuid;
    @Getter
    private String name;

    public User(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }
}
