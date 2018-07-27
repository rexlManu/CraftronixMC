/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.craftronixapi.database;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;
import lombok.Getter;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 08:13                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class ArangoConnector {

    @Getter
    private ArangoDB arangoDB;
    @Getter
    private ArangoDatabase database;

    public ArangoConnector() {
    }

    public void connect() {
        this.arangoDB = new ArangoDB.Builder().host("185.244.165.72", 8529).password("mb]h&k3v&tuK2QhS").build();
        if (!arangoDB.getDatabases().contains("craftronix")) {
            arangoDB.createDatabase("craftronix");
        }
        this.database = arangoDB.db("craftronix");
    }

    public void disconnect() {
    }

}
