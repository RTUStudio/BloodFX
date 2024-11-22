package kr.rtuserver.bloodfx.commands;

import kr.rtuserver.bloodfx.RSBloodFX;
import kr.rtuserver.bloodfx.configuration.EffectConfig;
import kr.rtuserver.bloodfx.configuration.ParticleConfig;
import kr.rtuserver.bloodfx.particle.ToggleManager;
import kr.rtuserver.framework.bukkit.api.RSPlugin;
import kr.rtuserver.framework.bukkit.api.command.RSCommand;
import kr.rtuserver.framework.bukkit.api.command.RSCommandData;
import kr.rtuserver.framework.bukkit.api.utility.player.PlayerChat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Command extends RSCommand {

    private final EffectConfig effectConfig;
    private final ParticleConfig particleConfig;
    private final ToggleManager toggleManager;

    public Command(RSBloodFX plugin) {
        super(plugin, "rsbfx", true);
        this.effectConfig = plugin.getEffectConfig();
        this.particleConfig = plugin.getParticleConfig();
        this.toggleManager = plugin.getToggleManager();
    }

    @Override
    public boolean execute(RSCommandData data) {
        PlayerChat chat = PlayerChat.of(getPlugin());
        String on = getCommand().get("toggle.on");
        String off = getCommand().get("toggle.off");
        if (data.equals(0, on)) {
            if (hasPermission("rsbfx.toggle")) {
                if (getSender() instanceof Player player) {
                    toggleManager.on(player.getUniqueId());
                    chat.announce(getSender(), getMessage().get("toggle.on"));
                } else chat.announce(getAudience(), getMessage().getCommon("onlyPlayer"));
            } else chat.announce(getAudience(), getMessage().getCommon("noPermission"));
        }
        if (data.equals(0, off)) {
            if (hasPermission("rsbfx.toggle")) {
                if (getSender() instanceof Player player) {
                    toggleManager.off(player.getUniqueId());
                    chat.announce(getSender(), getMessage().get("toggle.off"));
                } else chat.announce(getAudience(), getMessage().getCommon("onlyPlayer"));
            } else chat.announce(getAudience(), getMessage().getCommon("noPermission"));
        }
        return false;
    }

    @Override
    public void reload(RSCommandData data) {
        effectConfig.reload();
        particleConfig.reload();
    }

    @Override
    public void wrongUsage(RSCommandData data) {
        PlayerChat chat = PlayerChat.of(getPlugin());
        if (hasPermission("rsbfx.toggle")) {
            chat.send(getAudience(), getMessage().get("wrongUsage.toggle.on"));
            chat.send(getAudience(), getMessage().get("wrongUsage.toggle.off"));
        }
    }

    @Override
    public List<String> tabComplete(RSCommandData data) {
        String on = getCommand().get("toggle.on");
        String off = getCommand().get("toggle.off");
        if (data.length(1)) {
            List<String> list = new ArrayList<>();
            if (hasPermission("rsbfx.toggle")) {
                list.add(on);
                list.add(off);
            }
            return list;
        }
        return List.of();
    }
}
