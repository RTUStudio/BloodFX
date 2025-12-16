package kr.rtustudio.bloodfx.listener;

import kr.rtustudio.bloodfx.BloodFX;
import kr.rtustudio.bloodfx.manager.ToggleManager;
import kr.rtustudio.framework.bukkit.api.listener.RSListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@SuppressWarnings("unused")
public class PlayerJoinQuit extends RSListener<BloodFX> {

    private final ToggleManager manager;

    public PlayerJoinQuit(BloodFX plugin) {
        super(plugin);
        this.manager = plugin.getToggleManager();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        manager.addPlayer(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        manager.removePlayer(e.getPlayer().getUniqueId());
    }

}
