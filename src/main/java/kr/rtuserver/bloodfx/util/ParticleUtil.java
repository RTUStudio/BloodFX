package kr.rtuserver.bloodfx.util;

import kr.rtuserver.bloodfx.RSBloodFX;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class ParticleUtil {

    public static void spawn(World world, Location hitlocation, Material material, int amount) {
        Map<UUID, Boolean> map = RSBloodFX.getInstance().getToggleManager().getMap();
        BlockData blockCrackData = material.createBlockData();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getWorld() == world && map.getOrDefault(player.getUniqueId(), true)) {
                player.spawnParticle(Particle.BLOCK_CRACK, hitlocation.getX(), hitlocation.getY(), hitlocation.getZ(), amount, blockCrackData);
            }
        }
    }
}
