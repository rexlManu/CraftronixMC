/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.lobbysystem.misc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import tk.craftronixmc.lobbysystem.LobbySystem;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 14:41                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class PlayerScoreboard {

    private int animationTick = 0;
    private int state = 0;
    private int task;

    public PlayerScoreboard() {

    }

    private String[] pluginMc = new String[]{
            "§2•", "§2•§a●", "§2•§a● ", "§2•§a● §a§lC", "§2•§a● §a§lCr", "§2•§a● §a§lCra", "§2•§a● §a§lCraf", "§2•§a● §a§lCraft",
            "§2•§a● §a§lCraftr", "§2•§a● §a§lCraftro", "§2•§a● §a§lCraftron", "§2•§a● §a§lCraftroni", "§2•§a● §a§lCraftronix",
            "§2•§a● §a§lCraftronixM", "§2•§a● §a§lCraftronixMC", "§2•§a● §a§lCraftronixMC"
    };

    public void set(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        final Scoreboard scoreboard = player.getScoreboard();
        final Objective objective = scoreboard.registerNewObjective("lobby", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§2•§a● §a§lCraftronixMC");

        objective.getScore("§a§8§m---------------").setScore(10);
        addUpdatableTeam(scoreboard, objective, "x13", "§8•§7● Profile", "", "§a", 9);
        final String name = player.getName();

        if (name.length() > 10) {
            addUpdatableTeam(scoreboard, objective, "x12", "§8➜ §a" + name.substring(0, name.length() / 2), "§a" + name.substring(name.length() / 2, name.length()), "§b", 8);
        } else {
            addUpdatableTeam(scoreboard, objective, "x12", "§8➜ §a" + name, "", "§b", 8);
        }

        objective.getScore("§c").setScore(7);
        addUpdatableTeam(scoreboard, objective, "x11", "§8•§7● Server", "", "§d", 6);
        addUpdatableTeam(scoreboard, objective, "x10", "§8➜ §f", "§f" + Bukkit.getServerName(), "§e", 5);
        objective.getScore("§f").setScore(4);
        addUpdatableTeam(scoreboard, objective, "x9", "§8•§7● Teamspeak", "", "§1", 3);
        addUpdatableTeam(scoreboard, objective, "x8", "§8➜ §fCraft", "§fronixMC.net", "§2", 2);
        objective.getScore("§b§8§m---------------").setScore(1);

    }

    public void update(Player player) {
        if (player.getScoreboard() == null || player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null) return;


    }

    private void addUpdatableTeam(Scoreboard scoreboard, Objective objective, String teamName, String prefix, String suffex, String entry, int score) {
        Team team = scoreboard.registerNewTeam(teamName);
        team.setPrefix(prefix);
        team.setSuffix(suffex);
        team.addEntry(entry);
        objective.getScore(entry).setScore(score);
    }

    public void animate(LobbySystem lobbySystem) {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(lobbySystem, () -> {

            animationTick = 0;

            task = Bukkit.getScheduler().scheduleAsyncRepeatingTask(lobbySystem, () -> {
                if (animationTick == (pluginMc.length-1)) {
                    Bukkit.getScheduler().cancelTask(task);
                    return;
                }

                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getScoreboard() != null) {
                        player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(pluginMc[animationTick]);
                    }

                }
                animationTick++;
            }, 1, 1);

        }, 0, 100);
    }


}
