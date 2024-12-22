package kr.rtuserver.bloodfx;

import kr.rtuserver.bloodfx.commands.Command;
import kr.rtuserver.bloodfx.configuration.EffectConfig;
import kr.rtuserver.bloodfx.configuration.ParticleConfig;
import kr.rtuserver.bloodfx.dependency.PlaceholderAPI;
import kr.rtuserver.bloodfx.listeners.EntityDamageByEntity;
import kr.rtuserver.bloodfx.listeners.PlayerJoinQuit;
import kr.rtuserver.bloodfx.manager.ToggleManager;
import kr.rtuserver.bloodfx.packet.BloodHeartParticle;
import kr.rtuserver.framework.bukkit.api.RSPlugin;
import lombok.Getter;
import org.bukkit.permissions.PermissionDefault;

public class RSBloodFX extends RSPlugin {

    @Getter
    private static RSBloodFX instance;
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
        instance = this;
        getConfigurations().initStorage("Toggle");

        registerPermission("rsbfx.toggle", PermissionDefault.TRUE);

        effectConfig = new EffectConfig(this);
        particleConfig = new ParticleConfig(this);

        toggleManager = new ToggleManager(this);

        registerEvent(new PlayerJoinQuit(this));
        registerEvent(new EntityDamageByEntity(this));
        registerCommand(new Command(this));

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
