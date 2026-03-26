package kr.rtustudio.bloodfx.command;

import kr.rtustudio.bloodfx.BloodFX;
import kr.rtustudio.bloodfx.configuration.EffectConfig;
import kr.rtustudio.bloodfx.configuration.ParticleConfig;
import kr.rtustudio.bloodfx.manager.ToggleManager;
import kr.rtustudio.framework.bukkit.api.command.CommandArgs;
import kr.rtustudio.framework.bukkit.api.command.RSCommand;
import org.bukkit.entity.Player;

public class MainCommand extends RSCommand<BloodFX> {

    private final ToggleManager toggleManager;

    public MainCommand(BloodFX plugin) {
        super(plugin, "bloodfx");
        this.toggleManager = plugin.getToggleManager();
    }

    @Override
    public Result execute(CommandArgs data) {
        Player player = player();
        if (player == null) return Result.ONLY_PLAYER;
        if (hasPermission("toggle")) {
            if (toggleManager.get(player.getUniqueId())) {
                toggleManager.off(player.getUniqueId());
                notifier.announce(message.get(player, "disable"));
            } else {
                toggleManager.on(player.getUniqueId());
                notifier.announce(message.get(player, "enable"));
            }
            return Result.SUCCESS;
        } else return Result.NO_PERMISSION;
    }

    @Override
    public void reload(CommandArgs data) {
        plugin.reloadConfiguration(EffectConfig.class);
        plugin.reloadConfiguration(ParticleConfig.class);
    }

}
