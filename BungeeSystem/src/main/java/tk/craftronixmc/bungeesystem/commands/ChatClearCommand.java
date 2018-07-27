/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.bungeesystem.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import tk.craftronixmc.bungeesystem.BungeeSystem;
import tk.craftronixmc.bungeesystem.misc.Message;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 13:20                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class ChatClearCommand extends Command {

    private BungeeSystem system;

    public ChatClearCommand(String name, BungeeSystem system) {
        super(name);
        this.system = system;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("craftronix.support")) {
            sender.sendMessage(Message.nonPerms());
            return;
        }
        final ProxiedPlayer player = (ProxiedPlayer) sender;
        for (ProxiedPlayer target : ProxyServer.getInstance().getPlayers()) {
            if (target.getServer().equals(player.getServer())) {
                for (int i = 0; i < 100; i++) {
                    target.sendMessage("§r");
                }
                target.sendMessage(Message.prefix("CraftronixMC") + "§7The chat was cleared by §a"+player.getName()+"§8!");
            }
        }
    }
}
