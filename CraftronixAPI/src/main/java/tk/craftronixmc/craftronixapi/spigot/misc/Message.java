/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.craftronixapi.spigot.misc;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 12:34                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class Message {

    public static String prefix(String keyWord) {
        return "§2•§a● " + keyWord + "§8│ §7";
    }

    public static String offlinePlayer(String player) {
        return prefix("CraftronixMC") + "§a" + player + "§7 is not online right now§8!";
    }

    public static String nonPerms() {
        return prefix("CraftronixMC") + "§7You aren't allowed to §cexecute §7this command§8.";
    }
}
