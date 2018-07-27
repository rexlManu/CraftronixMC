/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.bungeesystem.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import tk.craftronixmc.bungeesystem.BungeeSystem;
import tk.craftronixmc.bungeesystem.entities.Support;
import tk.craftronixmc.bungeesystem.manager.SettingManager;
import tk.craftronixmc.bungeesystem.manager.SupportManager;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 10:23                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class SupportCommand extends Command {

    private BungeeSystem system;
    private String prefix;

    public SupportCommand(String name, BungeeSystem system) {
        super(name);
        this.system = system;
        this.prefix = "§2•§a● Support§8│ §7";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        final ProxiedPlayer player = (ProxiedPlayer) sender;
        final SupportManager supportManager = system.getSupportManager();
        if (args.length == 0) {
            sender.sendMessage(prefix + "§7/Support §8[§aQuestion§8]");
        } else {
            if (sender.hasPermission("craftronix.support")) {
                if (args[0].equalsIgnoreCase("close")) {
                    final Support supportFromPlayer = supportManager.getSupportFromPlayer(player);
                    if (supportFromPlayer != null) {
                        supportManager.closeSupport(supportFromPlayer);
                    } else {
                        player.sendMessage(prefix + "§7You do not have an open §asupport-ticket§8.");
                    }
                } else {
                    String name = args[0];
                    supportManager.acceptSupport(name, player);
                }
                return;
            }
            final StringBuilder stringBuilder = new StringBuilder();
            for (String arg : args) {
                stringBuilder.append(arg);
                stringBuilder.append(" ");
            }
            final String question = stringBuilder.toString().trim();

            if (supportManager.hasSupportTicket(player)) {
                player.sendMessage(prefix + "§7You can't create a new §asupport-ticket §7while you are in the support queue§8.");
            } else {
                supportManager.newSupport(question, player);
            }

        }
    }
}
