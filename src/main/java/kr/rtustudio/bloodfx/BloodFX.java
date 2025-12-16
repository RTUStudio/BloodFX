package kr.rtustudio.bloodfx;

import kr.rtustudio.bloodfx.command.MainCommand;
import kr.rtustudio.bloodfx.configuration.EffectConfig;
import kr.rtustudio.bloodfx.configuration.ParticleConfig;
import kr.rtustudio.bloodfx.configuration.serializer.EntityTypeSerializer;
import kr.rtustudio.bloodfx.configuration.serializer.MaterialSerializer;
import kr.rtustudio.bloodfx.dependency.DamageIndicatorPacket;
import kr.rtustudio.bloodfx.dependency.StatusPlaceholder;
import kr.rtustudio.bloodfx.listener.EntityDamageByEntity;
import kr.rtustudio.bloodfx.listener.PlayerJoinQuit;
import kr.rtustudio.bloodfx.manager.ToggleManager;
import kr.rtustudio.framework.bukkit.api.RSPlugin;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.permissions.PermissionDefault;

public class BloodFX extends RSPlugin {

    @Getter
    private ToggleManager toggleManager;

    @Override
    public void load() {
    }

    @Override
    public void enable() {
        initStorage("Toggle");

        registerConfiguration(EffectConfig.class, "Effect");
        registerConfiguration(ParticleConfig.class, "Particle", builder -> {
            builder.register(EntityType.class, new EntityTypeSerializer());
            builder.register(Material.class, new MaterialSerializer());
        });

        toggleManager = new ToggleManager(this);

        registerPermission("toggle", PermissionDefault.TRUE);

        registerEvent(new PlayerJoinQuit(this));
        registerEvent(new EntityDamageByEntity(this));

        registerCommand(new MainCommand(this), true);

        registerIntegration(new StatusPlaceholder(this));
        registerIntegration(new DamageIndicatorPacket(this));
    }

}
