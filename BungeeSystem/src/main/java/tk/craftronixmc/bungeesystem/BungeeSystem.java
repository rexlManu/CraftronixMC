package tk.craftronixmc.bungeesystem;

import com.arangodb.entity.BaseDocument;
import lombok.Getter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import tk.craftronixmc.bungeesystem.commands.ChatClearCommand;
import tk.craftronixmc.bungeesystem.commands.HubCommand;
import tk.craftronixmc.bungeesystem.commands.MaintenanceCommand;
import tk.craftronixmc.bungeesystem.commands.SupportCommand;
import tk.craftronixmc.bungeesystem.commands.msg.MsgCommand;
import tk.craftronixmc.bungeesystem.commands.msg.RCommand;
import tk.craftronixmc.bungeesystem.commands.msg.MsgToggleCommand;
import tk.craftronixmc.bungeesystem.listeners.*;
import tk.craftronixmc.bungeesystem.manager.PrivatMessageManager;
import tk.craftronixmc.bungeesystem.manager.SettingManager;
import tk.craftronixmc.bungeesystem.manager.SupportManager;
import tk.craftronixmc.bungeesystem.misc.RunTask;
import tk.craftronixmc.craftronixapi.CraftronixAPI;
import tk.craftronixmc.craftronixapi.database.ArangoConnector;

@Getter
public final class BungeeSystem extends Plugin {

    private ArangoConnector connector;
    private SettingManager settingManager;
    private SupportManager supportManager;
    private PrivatMessageManager privatMessageManager;

    @Override
    public void onEnable() {
        this.connector = CraftronixAPI.getInstance().getConnector();
        this.settingManager = new SettingManager(this);
        this.supportManager = new SupportManager(this);
        this.privatMessageManager = new PrivatMessageManager(this);

        initListeners();
        initCommands();
        initDocuments();
        new RunTask(this);
    }

    private void initDocuments() {
        final BaseDocument onlineplayers = new BaseDocument("onlineplayers");
        onlineplayers.addAttribute("onlineplayers", ProxyServer.getInstance().getOnlineCount());
        if (!getConnector().getDatabase().collection("settings").documentExists("onlineplayers")) {
            getConnector().getDatabase().collection("settings").insertDocument(onlineplayers);
        }
    }

    private void initCommands() {
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new HubCommand("hub", this));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new SupportCommand("support", this));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new MsgCommand("msg", this));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new RCommand("r", this));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new MsgToggleCommand("msgtoggle", this));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new MaintenanceCommand("maintenance", this));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new ChatClearCommand("cc", this));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new ChatClearCommand("chatclear", this));
    }

    private void initListeners() {
        new ChatListener(this);
        new ProxyPingListener(this);
        new PostLoginListener(this);
        new ServerKickListener(this);
        new TabCompleteListener(this);
        new LoginListener(this);
        new PermissionCheckListener(this);
    }

    @Override
    public void onDisable() {
    }
}
