/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.bungeesystem.manager;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import tk.craftronixmc.bungeesystem.BungeeSystem;
import tk.craftronixmc.bungeesystem.entities.Support;

import java.util.ArrayList;
import java.util.List;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 10:25                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class SupportManager implements Listener {

    private List<Support> supports;
    private String prefix;

    public SupportManager(BungeeSystem system) {
        this.supports = new ArrayList<>();
        this.prefix = "§2•§a● Support§8│ §7";
        ProxyServer.getInstance().getPluginManager().registerListener(system, this);
    }

    public void newSupport(String question, ProxiedPlayer asker) {
        final Support support = new Support(null, asker, question);
        supports.add(support);
        asker.sendMessage(prefix + "§7Thanks for contacting our §asupport-team§8!");
        final int supportTicketCount = supports.size();
        asker.sendMessage(prefix + "§7There are currently §a" + supportTicketCount + "§7 available§8!");
        asker.sendMessage(prefix + "§7You are on position §a#" + (supportTicketCount + 1) + " §8(§aqueue§8)");
        notifyTeamNewTicket(support);
    }

    public void acceptSupport(String name, ProxiedPlayer supporter) {
        final Support supportByName = getSupportByName(name);
        if (supportByName == null) {
            supporter.sendMessage(prefix + "§7No §asupport-ticket §7could be found.");
        } else {
            if (supportByName.getSupporter() != null) {
                supporter.sendMessage(prefix + "This §asupport-ticket §7is already being processed");
            } else {
                final ProxiedPlayer asker = supportByName.getAsker();
                supportByName.setSupporter(supporter);
                asker.sendMessage(prefix + "§a" + supporter.getName() + "§7 is available for your question§8!");
                asker.sendMessage(prefix + "§7If you want to chat, simply type your message into the chat§8!");
                supporter.sendMessage(prefix + "You are now editing the §asupport-ticket§7 of §a" + asker.getName() + "§8.");
            }
        }
    }

    public void closeSupport(Support support) {
        support.getAsker().sendMessage(prefix + "§a" + support.getSupporter().getName() + "§7 has left the §asupport-room§8. §7Support closed§8!");
        support.getSupporter().sendMessage(prefix + "§7You have successfully closed the §asupport-ticket§8.");
        supports.remove(support);
    }

    public void disconnectCloseSupport(Support support, ProxiedPlayer disconnect) {
        final ProxiedPlayer asker = support.getAsker();
        if (disconnect.equals(asker)) {
            support.getSupporter().sendMessage(prefix + "§7The §aplayer §7has left the server§8.");
        } else {
            support.getAsker().sendMessage(prefix + "§7The §asupporter §7has left the server§8.");
        }
        supports.remove(support);
    }

    @EventHandler
    public void on(ServerDisconnectEvent event) {
        final ProxiedPlayer player = event.getPlayer();
        final Support supportFromPlayer = getSupportFromPlayer(player);
        if (supportFromPlayer != null) {
            disconnectCloseSupport(supportFromPlayer, player);
        }
    }

    @EventHandler
    public void on(ChatEvent event) {
        if (event.getSender() instanceof ProxiedPlayer) {
            final ProxiedPlayer player = (ProxiedPlayer) event.getSender();
            if (!event.getMessage().startsWith("/") && !event.isCancelled()) {
                final Support support = getSupportFromPlayer(player);
                if (support != null) {
                    final ProxiedPlayer supporter = support.getSupporter();
                    if (supporter != null) {

                        if (supporter.equals(player)) {
                            event.setCancelled(true);
                            supporter.sendMessage(prefix + "§8(§a" + supporter.getName() + "§8) §7» §f" + event.getMessage());
                            support.getAsker().sendMessage(prefix + "§8(§a" + supporter.getName() + "§8) §7» §f" + event.getMessage());
                        } else {
                            event.setCancelled(true);
                            supporter.sendMessage(prefix + "§8(§aYou§8) §7> §8(§a" + supporter.getName() + "§8) §7» §f" + event.getMessage());
                            support.getAsker().sendMessage(prefix + "§8(§aYou§8) §7> §8(§a" + supporter.getName() + "§8) §7» §f" + event.getMessage());
                        }
                    }
                }
            }
        }
    }

    public Support getSupportFromPlayer(ProxiedPlayer player) {
        for (Support support : supports) {
            if (support.getAsker().equals(player)) {
                return support;
            }
            if(support.getSupporter() != null && support.getSupporter().equals(player)){
                return support;
            }
        }
        return null;
    }

    public Support getSupportByName(String name) {
        for (Support support : supports) {
            if (support.getAsker().getName().equalsIgnoreCase(name)) {
                return support;
            }
        }
        return null;
    }

    private void notifyTeamNewTicket(Support support) {
        ProxyServer.getInstance().getPlayers().forEach(player -> {
            if (player.hasPermission("craftronix.support")) {
                final String name = support.getAsker().getName();
                player.sendMessage(prefix + "A new §asupport-ticket §7from §a" + name + "§8.");
                player.sendMessage(prefix + "§aQuestion §8» §7" + support.getQuestion() + "");
                player.sendMessage(prefix + "For accepting the §7/Support §a" + name + "§8");
            }
        });
    }

    public boolean hasSupportTicket(ProxiedPlayer asker) {
        for (Support support : supports) {
            if (support.getAsker().equals(asker)) {
                return true;
            }
        }
        return false;
    }
}
