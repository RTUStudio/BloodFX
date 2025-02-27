package com.github.ipecter.rtustudio.bloodfx.dependency;

import com.github.ipecter.rtustudio.bloodfx.BloodFX;
import com.github.ipecter.rtustudio.bloodfx.manager.ToggleManager;
import kr.rtuserver.framework.bukkit.api.dependencies.RSPlaceholder;
import org.bukkit.OfflinePlayer;

public class PlaceholderAPI extends RSPlaceholder<BloodFX> {

    private final ToggleManager manager;

    public PlaceholderAPI(BloodFX plugin) {
        super(plugin);
        this.manager = plugin.getToggleManager();
    }

    @Override
    public String request(OfflinePlayer offlinePlayer, String[] params) {
        if ("toggle".equalsIgnoreCase(params[0])) {
            return manager.get(offlinePlayer.getUniqueId()) ? "ON" : "OFF";
        }
        return "ERROR";
    }
}
