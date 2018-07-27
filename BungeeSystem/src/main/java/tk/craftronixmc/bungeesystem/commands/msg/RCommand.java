/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.bungeesystem.commands.msg;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import tk.craftronixmc.bungeesystem.BungeeSystem;
import tk.craftronixmc.bungeesystem.manager.PrivatMessageManager;
import tk.craftronixmc.bungeesystem.misc.Message;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 12:45                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class RCommand extends Command {

    private BungeeSystem system;

    public RCommand(String name, BungeeSystem system) {
        super(name);
        this.system = system;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        final ProxiedPlayer player = (ProxiedPlayer) sender;
        if (args.length == 1) {
            player.sendMessage(Message.prefix("MSG") + "§a/R §8[§aReply-Message§8]");
        } else {
            final PrivatMessageManager manager = system.getPrivatMessageManager();
            if (manager.getLastMessager(player) == null) {
                player.sendMessage(Message.prefix("MSG") + "§7You can't reply to a message because you don't get one§8.");
            } else {
                final ProxiedPlayer target = manager.getLastMessager(player);
                StringBuilder builder = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    builder.append(args[i]).append(" ");
                }
                final String message = builder.toString().trim();
                player.chat("/msg " + target.getName() + " " + message);
            }

        }
    }
}
