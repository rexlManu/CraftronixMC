/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.lobbysystem.manager;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import tk.craftronixmc.lobbysystem.LobbySystem;

import java.io.File;
import java.io.IOException;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 19:10                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class LocationManager {

    private YamlConfiguration configuration;
    private File file;
    private LobbySystem lobbySystem;

    public LocationManager(LobbySystem lobbySystem) {
        this.lobbySystem = lobbySystem;
        this.file = new File(lobbySystem.getDataFolder(), "location.yml");
        lobbySystem.getDataFolder().mkdir();
        this.configuration = YamlConfiguration.loadConfiguration(file);
        if (!this.file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Location getSpawn() {
        return (Location) this.configuration.get("Spawn");
    }

    public void setSpawn(Location location) {
        this.configuration.set("Spawn", location);
        try {
            this.configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
