package kr.rtustudio.bloodfx.integration;

import kr.rtustudio.bloodfx.BloodFX;
import kr.rtustudio.bloodfx.manager.ToggleManager;
import kr.rtustudio.framework.bukkit.api.integration.wrapper.PlaceholderArgs;
import kr.rtustudio.framework.bukkit.api.integration.wrapper.PlaceholderWrapper;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class StatusPlaceholder extends PlaceholderWrapper<BloodFX> {

    private final ToggleManager manager;

    public StatusPlaceholder(BloodFX plugin) {
        super(plugin);
        this.manager = plugin.getToggleManager();
    }

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, PlaceholderArgs params) {
        if (params.length(1) && "status".equalsIgnoreCase(params.get(0))) {
            if (manager.get(offlinePlayer.getUniqueId())) {
                if (offlinePlayer instanceof Player player) {
                    return message.get(player, "placeholder.active");
                } else return message.get("placeholder.active");
            } else {
                if (offlinePlayer instanceof Player player) {
                    return message.get(player, "placeholder.inactive");
                } else return message.get("placeholder.inactive");
            }
        }
        return "ERROR";
    }
}
