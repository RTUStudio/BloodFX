package com.github.ipecter.rtustudio.bloodfx.listener;

import com.github.ipecter.rtustudio.bloodfx.BloodFX;
import com.github.ipecter.rtustudio.bloodfx.event.BloodEvent;
import com.github.ipecter.rtustudio.bloodfx.manager.ToggleManager;
import com.github.ipecter.rtustudio.bloodfx.configuration.EffectConfig;
import com.github.ipecter.rtustudio.bloodfx.configuration.ParticleConfig;
import com.github.ipecter.rtustudio.bloodfx.util.HitLocation;
import kr.rtuserver.framework.bukkit.api.listener.RSListener;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntity extends RSListener<BloodFX> {

    private final EffectConfig effectConfig;
    private final ParticleConfig particleConfig;
    private final ToggleManager manager;

    public EntityDamageByEntity(BloodFX plugin) {
        super(plugin);
        this.effectConfig = plugin.getEffectConfig();
        this.particleConfig = plugin.getParticleConfig();
        this.manager = plugin.getToggleManager();
    }

    @EventHandler
    private void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        Entity victim = e.getEntity();
        Entity attacker = e.getDamager();
        String namespacedKey = victim.getType().getKey().toString().toLowerCase();

        Location hitLoc;
        if (attacker instanceof Projectile projectile) {
            hitLoc = HitLocation.fromProjectile(projectile, victim, effectConfig.getParticleAccuracy());
        } else hitLoc = HitLocation.fromMelee((LivingEntity) attacker, victim, effectConfig.getParticleAccuracy());

        Material material = particleConfig.getMap().getOrDefault(namespacedKey, particleConfig.getDefaultParticle());
        BloodEvent event = new BloodEvent(attacker, victim, hitLoc, material);
        Bukkit.getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            BlockData blockCrackData = material.createBlockData();
            for (Player player : Bukkit.getOnlinePlayers()) {
                World world = player.getWorld();
                if (world == victim.getWorld() && manager.get(player.getUniqueId())) {
                    player.spawnParticle(Particle.BLOCK_CRACK, hitLoc.getX(), hitLoc.getY(), hitLoc.getZ(), effectConfig.getParticleAmount(), blockCrackData);
                }
            }
        }
    }

}
