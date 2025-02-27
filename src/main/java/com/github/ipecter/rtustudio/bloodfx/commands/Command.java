package com.github.ipecter.rtustudio.bloodfx.commands;

import com.github.ipecter.rtustudio.bloodfx.BloodFX;
import com.github.ipecter.rtustudio.bloodfx.configuration.EffectConfig;
import com.github.ipecter.rtustudio.bloodfx.configuration.ParticleConfig;
import com.github.ipecter.rtustudio.bloodfx.manager.ToggleManager;
import kr.rtuserver.framework.bukkit.api.command.RSCommand;
import kr.rtuserver.framework.bukkit.api.command.RSCommandData;
import kr.rtuserver.framework.bukkit.api.utility.player.PlayerChat;
import org.bukkit.entity.Player;

public class Command extends RSCommand<BloodFX> {

    private final EffectConfig effectConfig;
    private final ParticleConfig particleConfig;
    private final ToggleManager toggleManager;

    public Command(BloodFX plugin) {
        super(plugin, "bloodfx");
        this.effectConfig = plugin.getEffectConfig();
        this.particleConfig = plugin.getParticleConfig();
        this.toggleManager = plugin.getToggleManager();
    }

    @Override
    public boolean execute(RSCommandData data) {
        PlayerChat chat = PlayerChat.of(getPlugin());
        if (getSender() instanceof Player player) {
            if (hasPermission(getPlugin().getName() + ".toggle")) {
                if (toggleManager.get(player.getUniqueId())) {
                    toggleManager.off(player.getUniqueId());
                    chat.announce(getSender(), getMessage().get(getSender(), "disable"));
                } else {
                    toggleManager.on(player.getUniqueId());
                    chat.announce(getSender(), getMessage().get(getSender(), "enable"));
                }
                return true;
            } else chat.announce(getAudience(), getCommon().getMessage(getSender(), "noPermission"));
        } else chat.announce(getAudience(), getCommon().getMessage(getSender(), "onlyPlayer"));
        return true;
    }

    @Override
    public void reload(RSCommandData data) {
        effectConfig.reload();
        particleConfig.reload();
    }
}
