/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.craftronixapi.spigot.misc;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 14:14                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;


public class ServerPingManager {

    private static HashMap<String, Integer> onlinePlayers = new HashMap<>();
    private static HashMap<String, Integer> maxPlayers = new HashMap<>();
    private static HashMap<String, String> motd = new HashMap<>();

    public void pingServer(String host, int port, String serverName, JavaPlugin javaPlugin) {

        new BukkitRunnable() {

            @Override
            public void run() {

                Socket socket = new Socket();

                try {

                    socket.connect(new InetSocketAddress(host, port));

                    OutputStream outputStream = socket.getOutputStream();
                    InputStream inputStream = socket.getInputStream();

                    outputStream.write(254);

                    StringBuilder str = new StringBuilder();

                    int b;

                    while ((b = inputStream.read()) != -1) {
                        if ((b != 0) && (b > 16) && (b != 255) && (b != 23) && (b != 24)) {
                            str.append((char) b);
                        }
                    }

                    String[] data = str.toString().split("§");

                    int online = Integer.parseInt(data[1]);
                    int max = Integer.parseInt(data[2]);
                    String motd = data[0];

                    onlinePlayers.put(serverName, online);
                    maxPlayers.put(serverName, max);
                    ServerPingManager.this.motd.put(serverName, motd);

                    socket.close();

                    return;

                } catch (IOException e) {

                    onlinePlayers.put(serverName, 0);
                    maxPlayers.put(serverName, 0);
                    motd.put(serverName, "Offline");

                    try {
                        socket.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                }

            }
        }.runTaskAsynchronously(javaPlugin);
    }

    public int getOnlinePlayers(String serverName) {
        if (onlinePlayers.containsKey(serverName)) {
            return onlinePlayers.get(serverName);
        }
        return 0;
    }

    public int getMaxPlayers(String serverName) {
        if (maxPlayers.containsKey(serverName)) {
            return maxPlayers.get(serverName);
        }
        return 0;
    }

    public String getMotd(String serverName) {
        if (motd.containsKey(serverName)) {
            return motd.get(serverName);
        }
        return "error";
    }

    public boolean isServerOnline(String serverName) {
        if (motd.containsKey(serverName)) {
            if (!motd.get(serverName).equals("Offline")) {
                return true;
            }
        }
        return false;
    }
}