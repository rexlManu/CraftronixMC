/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.craftronixapi.rank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 14:04                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/
@Getter
@AllArgsConstructor
public enum Rank {

    OWNER("§4Owner §8● §4", "§8│ §4Owner §8● §4", 1),
    ADMIN("§cAdmin §8● §c", "§8│ §cAdmin §8● §c", 2),
    MODERATOR("§cMod §8● §c", "§8│ §cModerator §8● §c", 3),
    SUPPORTER("§2Support §8● §2", "§8│ §2Supporter §8● §2", 4),
    DEVELOPER("§bDev §8● §b", "§8│ §bDeveloper §8● §b", 5),
    CONTENT("§bContent §8● §b", "§8│ §bContent §8● §b", 6),
    BUILDER("§eBuild §8● §e", "§8│ §eBuilder §8● §e", 7),
    PRIME("§e", "§e", 8),
    SPIELER("§7", "§7", 9999);

    private String prefix;
    private String displayName;
    private int tagID;

}
