/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.bungeesystem.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import tk.craftronixmc.bungeesystem.BungeeSystem;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 10:13                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class HubCommand extends Command {

    private BungeeSystem system;

    public HubCommand(String name, BungeeSystem system) {
        super(name);
        this.system = system;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        final ProxiedPlayer player = (ProxiedPlayer) sender;
        if (player.getServer().getInfo().getName().contains("Lobby")) {
            player.sendMessage(system.getSettingManager().getPrefix() + "§7You are already connected to the §aLobby-Server§8!");
        } else {
            player.connect(ProxyServer.getInstance().getServerInfo("Lobby-1"));
            player.sendMessage(system.getSettingManager().getPrefix() + "§7Connected to the §aLobby-Server§8!");
        }
    }
}
