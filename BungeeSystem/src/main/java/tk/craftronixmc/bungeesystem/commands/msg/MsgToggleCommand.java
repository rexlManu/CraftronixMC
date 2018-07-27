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
 *    Erstellt: 27.07.2018 / 12:29                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class MsgToggleCommand extends Command {

    private BungeeSystem system;

    public MsgToggleCommand(String name, BungeeSystem system) {
        super(name);
        this.system = system;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        final ProxiedPlayer player = (ProxiedPlayer) sender;
        final PrivatMessageManager privatMessageManager = system.getPrivatMessageManager();
        if (privatMessageManager.isToggled(player.getUniqueId())) {
            privatMessageManager.toggleMSG(player.getUniqueId(), false);
            player.sendMessage(Message.prefix("MSG") + "§aSuccessfully §7toggled §aMSG-Messages §7to §8(§cNot allowed§8)");
        } else {
            privatMessageManager.toggleMSG(player.getUniqueId(), true);
            player.sendMessage(Message.prefix("MSG") + "§aSuccessfully §7toggled §aMSG-Messages §7to §8(§aAllowed§8)");
        }
    }
}
