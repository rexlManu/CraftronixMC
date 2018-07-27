/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.bungeesystem.commands.msg;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PermissionCheckEvent;
import net.md_5.bungee.api.plugin.Command;
import tk.craftronixmc.bungeesystem.BungeeSystem;
import tk.craftronixmc.bungeesystem.manager.PrivatMessageManager;
import tk.craftronixmc.bungeesystem.misc.Message;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 12:35                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class MsgCommand extends Command {

    private BungeeSystem system;

    public MsgCommand(String name, BungeeSystem system) {
        super(name);
        this.system = system;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        final ProxiedPlayer player = (ProxiedPlayer) sender;
        if (args.length == 0) {
            sendHelp(player);
        } else if (args.length > 1) {

            final ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(Message.offlinePlayer(args[0]));
                return;
            }
            final String prefix = Message.prefix("MSG");
            if(target.equals(player)){
                player.sendMessage(prefix+"§7You can't send messages to yourself§8.");
            }

            final PrivatMessageManager manager = system.getPrivatMessageManager();

            if (manager.isToggled(player.getUniqueId())) {
                StringBuilder builder = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    builder.append(args[i]).append(" ");
                }
                final String message = builder.toString().trim();
                player.sendMessage(prefix + "§8(§7" + player.getName() + " §8» §a" + target.getName() + "§8) §8➥ §f" + message);
                target.sendMessage(prefix + "§8(§a" + player.getName() + "§8) §8» §f" + message);
                manager.setLastMessage(player, target);
            } else {
                player.sendMessage(prefix + "§a" + target.getName() + "§7 has toggled his §aMSG-Messages§8!");
            }

        } else sendHelp(player);
    }

    private void sendHelp(ProxiedPlayer player) {
        player.sendMessage(Message.prefix("MSG") + "§a/MSG §8[§aPlayer§8] §8[§aMessage...§8]");
        player.sendMessage(Message.prefix("MSG") + "§a/MSGToggle");
    }
}
