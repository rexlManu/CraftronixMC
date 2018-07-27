/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.craftronixapi.spigot;

import org.bukkit.plugin.java.JavaPlugin;
import tk.craftronixmc.craftronixapi.CraftronixAPI;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 08:11                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class SpigotTrap extends JavaPlugin {

    private CraftronixAPI craftronixAPI;

    @Override
    public void onEnable() {
        this.craftronixAPI = new CraftronixAPI();
        this.craftronixAPI.onEnable();
    }

    @Override
    public void onDisable() {
        this.craftronixAPI.onDisable();
    }
}
