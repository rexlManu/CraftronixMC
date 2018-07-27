package tk.craftronixmc.lobbysystem;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import tk.craftronixmc.craftronixapi.CraftronixAPI;
import tk.craftronixmc.craftronixapi.database.ArangoConnector;
import tk.craftronixmc.lobbysystem.commands.SetupCommand;
import tk.craftronixmc.lobbysystem.listener.CancleListener;
import tk.craftronixmc.lobbysystem.listener.LobbyListener;
import tk.craftronixmc.lobbysystem.manager.*;
import tk.craftronixmc.lobbysystem.misc.PlayerScoreboard;
import tk.craftronixmc.lobbysystem.misc.PlayerUpdater;
import tk.craftronixmc.lobbysystem.utils.BungeeUtils;

@Getter
public final class LobbySystem extends JavaPlugin {

    private ArangoConnector connector;
    private PlayerScoreboard playerScoreboard;
    private ItemManager itemManager;
    private InventoryManager inventoryManager;
    private PlayerUpdater playerUpdater;
    private ServerManager serverManager;
    private PlayerManager playerManager;
    private SettingsManager settingsManager;
    private LocationManager locationManager;

    @Override
    public void onEnable() {
        this.connector = CraftronixAPI.getInstance().getConnector();
        this.playerScoreboard = new PlayerScoreboard();
        this.playerScoreboard.animate(this);
        this.itemManager = new ItemManager();
        this.inventoryManager = new InventoryManager(this);
        this.playerUpdater = new PlayerUpdater(this);
        this.serverManager = new ServerManager(this);
        this.playerManager = new PlayerManager(this);
        this.settingsManager = new SettingsManager(this);
        this.locationManager = new LocationManager(this);

        initListener();

        checkWorlds();
        BungeeUtils.registerBungeeChannel(this, getServer());
        new AntiWeather().runTaskTimer(this, 0, 20 * 10);
        getCommand("setup").setExecutor(new SetupCommand(this));
    }

    @Override
    public void onDisable() {
    }

    private void initListener() {
        new CancleListener(this);
        new LobbyListener(this);
    }

    private void updateWorlds() {
        for (final World world : Bukkit.getWorlds()) {
            world.setTime(0);
            world.setThundering(false);
            world.setStorm(false);
        }
    }

    private void checkWorlds() {
        for (final World world : Bukkit.getWorlds()) {
            world.setTime(0);
            world.setThundering(false);
            world.setGameRuleValue("doFireTick", "false");
            world.setGameRuleValue("doDaylightCycle", "false");
            world.setGameRuleValue("doMobSpawning", "false");
            world.setGameRuleValue("doMobLoot", "false");
            world.setGameRuleValue("doTileDrops", "false");
            world.setGameRuleValue("mobGriefing", "false");
            world.setStorm(false);
        }
    }

    class AntiWeather extends BukkitRunnable {

        @Override
        public void run() {
            updateWorlds();
        }
    }

}
