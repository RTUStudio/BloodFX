package com.github.ipecter.rtustudio.bloodfx.dependency;

import com.github.ipecter.rtustudio.bloodfx.BloodFX;
import com.github.ipecter.rtustudio.bloodfx.manager.ToggleManager;
import kr.rtuserver.framework.bukkit.api.integration.RSPlaceholder;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlaceholderAPI extends RSPlaceholder<BloodFX> {

    private final ToggleManager manager;

    public PlaceholderAPI(BloodFX plugin) {
        super(plugin);
        this.manager = plugin.getToggleManager();
    }

    @Override
    public String request(OfflinePlayer offlinePlayer, String[] params) {
        if ("status".equalsIgnoreCase(params[0])) {
            if (manager.get(offlinePlayer.getUniqueId())) {
                if (offlinePlayer instanceof Player player) {
                    return message().get(player, "placeholder.active");
                } else return message().get("placeholder.active");
            } else {
                if (offlinePlayer instanceof Player player) {
                    return message().get(player, "placeholder.inactive");
                } else return message().get("placeholder.inactive");
            }
        }
        return "ERROR";
    }

}
