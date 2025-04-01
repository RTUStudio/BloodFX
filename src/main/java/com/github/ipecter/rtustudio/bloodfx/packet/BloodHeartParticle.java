package com.github.ipecter.rtustudio.bloodfx.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.github.ipecter.rtustudio.bloodfx.BloodFX;
import com.github.ipecter.rtustudio.bloodfx.configuration.EffectConfig;
import com.github.ipecter.rtustudio.bloodfx.manager.ToggleManager;
import kr.rtuserver.framework.bukkit.api.dependency.RSPacketListener;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class BloodHeartParticle extends RSPacketListener<BloodFX> {

    private final ToggleManager manager;
    private final EffectConfig config;

    public BloodHeartParticle(BloodFX plugin) {
        super(plugin, new AdapterParameteters()
                .listenerPriority(ListenerPriority.HIGHEST)
                .types(PacketType.Play.Server.WORLD_PARTICLES)
                .optionAsync());
        this.manager = plugin.getToggleManager();
        this.config = plugin.getEffectConfig();
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        Player player = event.getPlayer();
        PacketContainer packet = event.getPacket();
        if (event.getPacketType() != PacketType.Play.Server.WORLD_PARTICLES)
            return;
        if (packet.getNewParticles().read(0).getParticle() == Particle.DAMAGE_INDICATOR) {
            boolean toggle = manager.get(player.getUniqueId());
            if (!config.isVanillaParticle() || toggle) event.setCancelled(true);
        }
    }

}
