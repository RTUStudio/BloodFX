package kr.rtustudio.bloodfx;

import kr.rtustudio.bloodfx.command.MainCommand;
import kr.rtustudio.bloodfx.configuration.EffectConfig;
import kr.rtustudio.bloodfx.configuration.ParticleConfig;
import kr.rtustudio.bloodfx.configuration.serializer.EntityTypeSerializer;
import kr.rtustudio.bloodfx.configuration.serializer.MaterialSerializer;
import kr.rtustudio.bloodfx.integration.DamageIndicatorPacket;
import kr.rtustudio.bloodfx.integration.StatusPlaceholder;
import kr.rtustudio.bloodfx.handler.EntityDamageByEntity;
import kr.rtustudio.bloodfx.handler.PlayerJoinQuit;
import kr.rtustudio.bloodfx.manager.ToggleManager;
import kr.rtustudio.configurate.model.ConfigPath;
import kr.rtustudio.framework.bukkit.api.RSPlugin;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.permissions.PermissionDefault;

public class BloodFX extends RSPlugin {

    @Getter
    private static BloodFX instance;

    @Getter
    private ToggleManager toggleManager;

    @Override
    protected void load() {
        instance = this;
    }

    @Override
    protected void enable() {
        registerStorage("Toggle");

        registerConfiguration(EffectConfig.class, ConfigPath.of("Effect"));
        registerConfiguration(ParticleConfig.class, ConfigPath.of("Particle"), builder -> {
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
