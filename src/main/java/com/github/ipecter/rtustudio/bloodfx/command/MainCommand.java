package com.github.ipecter.rtustudio.bloodfx.command;

import com.github.ipecter.rtustudio.bloodfx.BloodFX;
import com.github.ipecter.rtustudio.bloodfx.configuration.EffectConfig;
import com.github.ipecter.rtustudio.bloodfx.configuration.ParticleConfig;
import com.github.ipecter.rtustudio.bloodfx.manager.ToggleManager;
import kr.rtuserver.framework.bukkit.api.command.RSCommand;
import kr.rtuserver.framework.bukkit.api.command.RSCommandData;
import org.bukkit.entity.Player;

public class MainCommand extends RSCommand<BloodFX> {

    private final EffectConfig effectConfig;
    private final ParticleConfig particleConfig;
    private final ToggleManager toggleManager;

    public MainCommand(BloodFX plugin) {
        super(plugin, "bloodfx");
        this.effectConfig = plugin.getEffectConfig();
        this.particleConfig = plugin.getParticleConfig();
        this.toggleManager = plugin.getToggleManager();
    }

    @Override
    public boolean execute(RSCommandData data) {
        Player player = player();
        if (player == null) {
            chat().announce(audience(), message().getCommon(sender(), "onlyPlayer"));
            return true;
        }
        if (hasPermission(getPlugin().getName() + ".toggle")) {
            if (toggleManager.get(player.getUniqueId())) {
                toggleManager.off(player.getUniqueId());
                chat().announce(audience(), message().get(sender(), "disable"));
            } else {
                toggleManager.on(player.getUniqueId());
                chat().announce(audience(), message().get(sender(), "enable"));
            }
            return true;
        } else chat().announce(audience(), message().getCommon(sender(), "noPermission"));
        return true;
    }

    @Override
    public void reload(RSCommandData data) {
        effectConfig.reload();
        particleConfig.reload();
    }

}
