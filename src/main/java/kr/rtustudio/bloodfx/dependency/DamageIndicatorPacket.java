package kr.rtustudio.bloodfx.dependency;

import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.particle.Particle;
import com.github.retrooper.packetevents.protocol.particle.type.ParticleTypes;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerParticle;
import kr.rtustudio.bloodfx.BloodFX;
import kr.rtustudio.bloodfx.configuration.EffectConfig;
import kr.rtustudio.bloodfx.manager.ToggleManager;
import kr.rtustudio.framework.bukkit.api.integration.wrapper.PacketWrapper;
import org.bukkit.entity.Player;

public class DamageIndicatorPacket extends PacketWrapper<BloodFX> {

    private final ToggleManager manager;
    private final EffectConfig config;

    public DamageIndicatorPacket(BloodFX plugin) {
        super(plugin);
        this.manager = plugin.getToggleManager();
        this.config = plugin.getConfiguration(EffectConfig.class);
    }

    @Override
    public void onSend(PacketSendEvent event) {
        if (event.getPacketType() != PacketType.Play.Server.PARTICLE) {
            return;
        }

        Player player = event.getPlayer();
        WrapperPlayServerParticle packet = new WrapperPlayServerParticle(event);
        Particle<?> particle = packet.getParticle();

        if (particle.getType() == ParticleTypes.DAMAGE_INDICATOR) {
            boolean toggle = manager.get(player.getUniqueId());
            if (!config.isVanillaParticle() && toggle) {
                event.setCancelled(true);
            }
        }
    }

}
