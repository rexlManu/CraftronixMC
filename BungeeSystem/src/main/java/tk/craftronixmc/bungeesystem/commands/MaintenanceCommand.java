/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.bungeesystem.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import tk.craftronixmc.bungeesystem.BungeeSystem;
import tk.craftronixmc.bungeesystem.manager.SettingManager;
import tk.craftronixmc.bungeesystem.misc.Message;

import java.util.List;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 13:11                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class MaintenanceCommand extends Command {

    private BungeeSystem system;

    public MaintenanceCommand(String name, BungeeSystem system) {
        super(name);
        this.system = system;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("craftronix.admin")) {
            sender.sendMessage(Message.nonPerms());
            return;
        }

        final String prefix = Message.prefix("Maintenance");

        final SettingManager manager = system.getSettingManager();
        if (args.length == 0) {
            sendHelp(sender);
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("toggle")) {
                final boolean maintenance = manager.getSettingAsBoolean("maintenance");
                if (maintenance) {
                    manager.setSetting("maintenance", false);
                    sender.sendMessage(prefix + "§7You have disabled the §amaintenance§8.");
                } else {
                    manager.setSetting("maintenance", true);
                    sender.sendMessage(prefix + "§7You have enabled the §amaintenance§8.");
                    final List<String> whitelist = (List<String>) manager.getSettingAsObject("whitelist");
                    final TextComponent text = new TextComponent("\n§8§m-------------------\n\n§7The server is currently under §amaintenance§8.\n\n§8§m-------------------\n");
                    ProxyServer.getInstance().getPlayers().forEach(player -> {
                        if (!whitelist.contains(player.getName())) {
                            player.disconnect(text);
                        }
                    });
                }
            } else sendHelp(sender);
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("add")) {
                final List<String> whitelist = (List<String>) manager.getSettingAsObject("whitelist");
                whitelist.add(args[1]);
                sender.sendMessage(prefix + "You have successfully whitelisted this player.");
                manager.setSetting("whitelist", whitelist);
            } else if (args[0].equalsIgnoreCase("remove")) {
                final List<String> whitelist = (List<String>) manager.getSettingAsObject("whitelist");
                whitelist.remove(args[1]);
                sender.sendMessage(prefix + "You have successfully removed this player.");
                manager.setSetting("whitelist", whitelist);
            } else sendHelp(sender);
        }

    }

    private void sendHelp(CommandSender sender) {
        final String prefix = Message.prefix("Maintenance");
        sender.sendMessage(prefix + "§a/Maintenance toggle");
        sender.sendMessage(prefix + "§a/Maintenance add §8[§aPlayer§8]");
        sender.sendMessage(prefix + "§a/Maintenance remove §8[§aPlayer§8]");
    }
}
