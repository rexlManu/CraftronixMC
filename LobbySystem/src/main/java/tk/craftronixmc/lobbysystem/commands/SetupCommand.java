/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.lobbysystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.craftronixmc.craftronixapi.spigot.misc.Message;
import tk.craftronixmc.lobbysystem.LobbySystem;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 14:02                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class SetupCommand implements CommandExecutor {

    private LobbySystem system;

    public SetupCommand(LobbySystem system) {
        this.system = system;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("craftronix.admin")) {
            sender.sendMessage(Message.nonPerms());
            return true;
        }
        sender.sendMessage(Message.prefix("Spawn") + "§7You have set the spawn.");
        system.getLocationManager().setSpawn(((Player) sender).getLocation());
        return false;
    }
}
