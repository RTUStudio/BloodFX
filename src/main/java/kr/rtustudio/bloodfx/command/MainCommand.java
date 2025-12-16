package kr.rtustudio.bloodfx.command;

import kr.rtustudio.bloodfx.BloodFX;
import kr.rtustudio.bloodfx.configuration.EffectConfig;
import kr.rtustudio.bloodfx.configuration.ParticleConfig;
import kr.rtustudio.bloodfx.manager.ToggleManager;
import kr.rtustudio.framework.bukkit.api.command.RSCommand;
import kr.rtustudio.framework.bukkit.api.command.RSCommandData;
import org.bukkit.entity.Player;

public class MainCommand extends RSCommand<BloodFX> {

    private final ToggleManager toggleManager;

    public MainCommand(BloodFX plugin) {
        super(plugin, "bloodfx");
        this.toggleManager = plugin.getToggleManager();
    }

    @Override
    public Result execute(RSCommandData data) {
        Player player = player();
        if (player == null) return Result.ONLY_PLAYER;
        if (hasPermission(getPlugin().getName() + ".toggle")) {
            if (toggleManager.get(player.getUniqueId())) {
                toggleManager.off(player.getUniqueId());
                chat().announce(audience(), message().get(sender(), "disable"));
            } else {
                toggleManager.on(player.getUniqueId());
                chat().announce(audience(), message().get(sender(), "enable"));
            }
            return Result.SUCCESS;
        } else return Result.NO_PERMISSION;
    }

    @Override
    public void reload(RSCommandData data) {
        getPlugin().reloadConfiguration(EffectConfig.class);
        getPlugin().reloadConfiguration(ParticleConfig.class);
    }

}
