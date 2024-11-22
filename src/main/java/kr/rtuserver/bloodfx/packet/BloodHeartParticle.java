package kr.rtuserver.bloodfx.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import kr.rtuserver.bloodfx.RSBloodFX;
import kr.rtuserver.bloodfx.configuration.EffectConfig;
import kr.rtuserver.bloodfx.particle.ToggleManager;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class BloodHeartParticle extends PacketAdapter {

    private final ToggleManager manager;
    private final EffectConfig config;

    public BloodHeartParticle(RSBloodFX plugin) {
        super((new AdapterParameteters())
                .plugin(plugin)
                .listenerPriority(ListenerPriority.HIGHEST)
                .types(PacketType.Play.Server.WORLD_PARTICLES)
                .optionAsync());
        this.manager = plugin.getToggleManager();
        this.config = plugin.getEffectConfig();
    }

    public void onPacketSending(PacketEvent event) {
        Player player = event.getPlayer();
        PacketContainer packet = event.getPacket();
        if (event.getPacketType() != PacketType.Play.Server.WORLD_PARTICLES)
            return;
        if (packet.getNewParticles().read(0).getParticle() == Particle.DAMAGE_INDICATOR) {
            boolean toggle = manager.getMap().getOrDefault(player.getUniqueId(), true);
            if (!config.isVanillaParticle() || toggle) event.setCancelled(true);
        }
    }
}
