package com.github.ipecter.rtustudio.bloodfx;

import com.github.ipecter.rtustudio.bloodfx.listener.PlayerJoinQuit;
import com.github.ipecter.rtustudio.bloodfx.manager.ToggleManager;
import com.github.ipecter.rtustudio.bloodfx.command.Command;
import com.github.ipecter.rtustudio.bloodfx.configuration.EffectConfig;
import com.github.ipecter.rtustudio.bloodfx.configuration.ParticleConfig;
import com.github.ipecter.rtustudio.bloodfx.dependency.PlaceholderAPI;
import com.github.ipecter.rtustudio.bloodfx.listener.EntityDamageByEntity;
import com.github.ipecter.rtustudio.bloodfx.packet.BloodHeartParticle;
import kr.rtuserver.framework.bukkit.api.RSPlugin;
import lombok.Getter;
import org.bukkit.permissions.PermissionDefault;

public class BloodFX extends RSPlugin {

    @Getter
    private EffectConfig effectConfig;
    @Getter
    private ParticleConfig particleConfig;
    @Getter
    private ToggleManager toggleManager;

    private BloodHeartParticle packetListener;
    private PlaceholderAPI placeholder;

    @Override
    public void enable() {
        getConfigurations().initStorage("Toggle");

        effectConfig = new EffectConfig(this);
        particleConfig = new ParticleConfig(this);

        toggleManager = new ToggleManager(this);

        registerPermission(getName() + ".toggle", PermissionDefault.TRUE);

        registerEvent(new PlayerJoinQuit(this));
        registerEvent(new EntityDamageByEntity(this));

        registerCommand(new Command(this), true);

        if (getFramework().isEnabledDependency("ProtocolLib")) {
            packetListener = new BloodHeartParticle(this);
            packetListener.register();
        }
        if (getFramework().isEnabledDependency("PlaceholderAPI")) {
            placeholder = new PlaceholderAPI(this);
            placeholder.register();
        }
    }

    @Override
    public void disable() {
        if (getFramework().isEnabledDependency("ProtocolLib")) {
            packetListener.unregister();
        }
        if (getFramework().isEnabledDependency("PlaceholderAPI")) {
            placeholder.unregister();
        }
    }
}
