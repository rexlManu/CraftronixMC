/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2018.
 */
package tk.craftronixmc.bungeesystem.manager;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.CollectionEntity;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import tk.craftronixmc.bungeesystem.BungeeSystem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/******************************************************************************************
 *    Urheberrechtshinweis                                                       
 *    Copyright © Emmanuel Lampe 2018                                       
 *    Erstellt: 27.07.2018 / 09:49                           
 *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.                    
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,       
 *    bei Emmanuel Lampe. Alle Rechte vorbehalten.                      
 *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        
 *    öffentlichen Zugänglichmachung oder andere Nutzung           
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Emmanuel Lampe.  
 ******************************************************************************************/

public class SettingManager {

    private Map<String, Object> settings;
    @Getter
    private String prefix;

    private BungeeSystem system;

    public SettingManager(BungeeSystem system) {
        this.system = system;
        this.prefix = "§2•§a● CraftronixMC§8│ §7";
        this.settings = new HashMap<>();
        this.settings.put("motd1", "§2•§a● CraftronixMC.tk§8│ §7Your CityBuild Network");
        this.settings.put("motd2", "§7We are §aonline §8» §aCitybuild-network§8!");
        this.settings.put("header", "§r\n§8§m--------------------------\n§8§l» §a§lCraftronixMC.tk §8§l«\n§7Online Players §8➟ §a%players%\n§r");
        this.settings.put("footer", "§r\n§7Teamspeak³ §8» §aCraftronixMC.tk\n§7Store §8» §aStore.CraftronixMC.tk\n§8§m--------------------------\n§r");
        this.settings.put("slots", 100);
        this.settings.put("maintenance", false);
        this.settings.put("whitelist", Arrays.asList("rexlManu"));

        loadSettings();
    }

    public String getSetting(String key) {
        return settings.get(key).toString();
    }

    public int getSettingAsInt(String key) {
        return Integer.valueOf(getSetting(key));
    }

    public Object getSettingAsObject(String key) {
        return settings.get(key);
    }

    public void setSetting(String key, Object object) {
        settings.put(key, object);
        final ArangoCollection collection = system.getConnector().getDatabase().collection("settings");
        final BaseDocument document = collection.getDocument(key, BaseDocument.class);
        document.updateAttribute(key, object);
        collection.updateDocument(key, document);
    }

    public boolean getSettingAsBoolean(String key) {
        return Boolean.parseBoolean(getSetting(key));
    }

    private void loadSettings() {
        final ArangoDatabase database = system.getConnector().getDatabase();
        try {
            database.createCollection("settings");
        } catch (Exception ignored) {

        }
        final ArangoCollection collectionSettings = database.collection("settings");
        settings.forEach((s, o) -> {
            if (!collectionSettings.documentExists(s)) {
                final BaseDocument value = new BaseDocument(s);
                value.addAttribute(s, o);
                collectionSettings.insertDocument(value);
            }

            final BaseDocument document = collectionSettings.getDocument(s, BaseDocument.class);
            this.settings.put(s, document.getAttribute(s));
        });
    }
}
