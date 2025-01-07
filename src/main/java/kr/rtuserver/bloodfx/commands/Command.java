package kr.rtuserver.bloodfx.commands;

import kr.rtuserver.bloodfx.RSBloodFX;
import kr.rtuserver.bloodfx.configuration.EffectConfig;
import kr.rtuserver.bloodfx.configuration.ParticleConfig;
import kr.rtuserver.bloodfx.manager.ToggleManager;
import kr.rtuserver.framework.bukkit.api.command.RSCommand;
import kr.rtuserver.framework.bukkit.api.command.RSCommandData;
import kr.rtuserver.framework.bukkit.api.utility.player.PlayerChat;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Command extends RSCommand<RSBloodFX> {

    private final EffectConfig effectConfig;
    private final ParticleConfig particleConfig;
    private final ToggleManager toggleManager;

    public Command(RSBloodFX plugin) {
        super(plugin, "rsbfx");
        this.effectConfig = plugin.getEffectConfig();
        this.particleConfig = plugin.getParticleConfig();
        this.toggleManager = plugin.getToggleManager();
    }

    @Override
    public boolean execute(RSCommandData data) {
        PlayerChat chat = PlayerChat.of(getPlugin());
        if (getSender() instanceof Player player) {
            if (hasPermission(getPlugin().getName() + ".toggle")) {
                boolean isVisible = toggleManager.getMap().getOrDefault(player.getUniqueId(), false);
                if (isVisible) {
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
