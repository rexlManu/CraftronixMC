/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.lobbysystem.misc;

import com.arangodb.entity.BaseDocument;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tk.craftronixmc.lobbysystem.LobbySystem;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 14:09                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class PlayerUpdater {

    public PlayerUpdater(LobbySystem system) {

        Bukkit.getScheduler().scheduleAsyncRepeatingTask(system, () -> {
            final int onlinePlayers = Integer.parseInt(String.valueOf(system.getConnector().getDatabase().collection("settings").getDocument("onlineplayers", BaseDocument.class).getAttribute("onlineplayers")));
            final int slots = Integer.parseInt(String.valueOf(system.getConnector().getDatabase().collection("settings").getDocument("slots", BaseDocument.class).getAttribute("slots")));
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.setLevel(onlinePlayers);
                player.setExp((float) onlinePlayers / (float) slots);
            }
        }, 20, 20);

    }
}
