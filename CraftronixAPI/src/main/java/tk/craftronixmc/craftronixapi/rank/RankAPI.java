/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.craftronixapi.rank;

import com.arangodb.entity.BaseDocument;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import tk.craftronixmc.craftronixapi.database.User;

import java.util.UUID;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 19:36                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class RankAPI {

    public static boolean hasPermission(UUID uuid, Rank rank) {
        return getRankFromPlayer(uuid).getTagID() <= rank.getTagID();
    }

    public static void updateTablist() {
        Bukkit.getOnlinePlayers().forEach(RankAPI::setTablist);
    }

    public static void setTablist(Player player) {
        final Scoreboard scoreboard = player.getScoreboard();
        for (Rank rank : Rank.values()) {
            final String teamName = rank.getTagID() + "_" + rank.name();
            if (scoreboard.getTeam(teamName) != null) {
                scoreboard.getTeam(teamName).unregister();
            }
            final Team team = scoreboard.registerNewTeam(teamName);
            team.setPrefix(rank.getPrefix());
        }

        for (Player all : Bukkit.getOnlinePlayers()) {
            final Rank rankFromPlayer = getRankFromPlayer(all.getUniqueId());
            scoreboard.getTeam(rankFromPlayer.getTagID() + "_" + rankFromPlayer.name()).addEntry(all.getName());
        }
        player.setScoreboard(scoreboard);
    }

    public static Rank getRankFromPlayer(UUID uuid) {
        return Rank.valueOf(User.getUser(uuid).getAttribute("rank").toString().toUpperCase());
    }

    public static void setRank(UUID uuid, Rank rank) {
        final BaseDocument user = User.getUser(uuid);
        user.addAttribute("rank", rank.name());
        User.updateDocument(user);
    }
}
