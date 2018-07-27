/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.lobbysystem.manager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import tk.craftronixmc.lobbysystem.utils.ItemBuilder;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 15:08                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class ItemManager {

    public ItemManager() {
    }

    public void giveItems(Player player) {
        final PlayerInventory inventory = player.getInventory();
        inventory.setItem(0, new ItemBuilder(Material.COMPASS, 1).setName(toItemName("Navigation")).build());
        inventory.setItem(1, new ItemBuilder(Material.BLAZE_ROD, 1).setName(toItemName("Playerhider")).build());
        if (player.hasPermission("system.nicktool")) {
            inventory.setItem(3, new ItemBuilder(Material.NAME_TAG, 1).setName(toItemName("Nicktool")).build());
        }
        if (player.hasPermission("system.silentlobby")) {
            inventory.setItem(5, new ItemBuilder(Material.TNT, 1).setName(toItemName("Silentlobby")).build());
        }

        inventory.setItem(7, new ItemBuilder(Material.COMMAND, 1).setName(toItemName("Settings")).build());
        inventory.setItem(8, new ItemBuilder(Material.NETHER_STAR, 1).setName(toItemName("Lobbyswitcher")).build());

    }

    private String toItemName(String keyWord) {
        return "§2•§a● " + keyWord + "§8│ §7Rightclick";
    }
}
