/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.lobbysystem.manager;

import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;
import tk.craftronixmc.craftronixapi.spigot.misc.ServerPingManager;
import tk.craftronixmc.lobbysystem.LobbySystem;
import tk.craftronixmc.lobbysystem.entities.Server;

import java.util.ArrayList;
import java.util.List;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 16:23                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class ServerManager extends BukkitRunnable {

    private LobbySystem lobbySystem;
    @Getter
    private List<Server> servers;
    private ServerPingManager serverPingManager;

    public ServerManager(LobbySystem lobbySystem) {
        this.lobbySystem = lobbySystem;
        this.serverPingManager = new ServerPingManager();
        runTaskTimerAsynchronously(lobbySystem, 0, 20);
        this.servers = new ArrayList<>();
        this.servers.add(new Server("CityBuild-1", "127.0.0.1", 20001));
        this.servers.add(new Server("CityBuild-2", "127.0.0.1", 20002));
        this.servers.add(new Server("Lobby-1", "127.0.0.1", 20000));
        this.servers.add(new Server("Lobby-2", "127.0.0.1", 20003));
    }

    @Override
    public void run() {
        for (Server server : servers) {
            serverPingManager.pingServer(server.getIp(), server.getPort(), server.getServerName(), lobbySystem);
        }

        for (Server server : servers) {
            server.setOnline(serverPingManager.isServerOnline(server.getServerName()));
            if (server.isOnline()) {
                server.setOnlinePlayers(serverPingManager.getOnlinePlayers(server.getServerName()));
            }
        }

    }
}
