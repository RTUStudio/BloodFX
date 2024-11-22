package kr.rtuserver.bloodfx.dependency;

import kr.rtuserver.bloodfx.RSBloodFX;
import kr.rtuserver.bloodfx.particle.ToggleManager;
import kr.rtuserver.framework.bukkit.api.RSPlugin;
import kr.rtuserver.framework.bukkit.api.dependencies.RSPlaceholder;
import org.bukkit.OfflinePlayer;

public class PlaceholderAPI extends RSPlaceholder {

    private final ToggleManager manager;

    public PlaceholderAPI(RSBloodFX plugin) {
        super(plugin);
        this.manager = plugin.getToggleManager();
    }

    @Override
    public String request(OfflinePlayer offlinePlayer, String[] params) {
        if ("toggle".equalsIgnoreCase(params[0])) {
            return manager.getMap().getOrDefault(offlinePlayer.getUniqueId(), false) ? "ON" : "OFF";
        }
        return "ERROR";
    }
}
