package tk.craftronixmc.craftronixapi;

import lombok.Getter;
import tk.craftronixmc.craftronixapi.database.ArangoConnector;

public final class CraftronixAPI {

    @Getter
    private static CraftronixAPI instance;
    @Getter
    private ArangoConnector connector;

    public void onEnable() {
        instance = this;
        this.connector = new ArangoConnector();
        this.connector.connect();
    }

    public void onDisable() {
        this.connector.disconnect();
    }

}
