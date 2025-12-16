package kr.rtustudio.bloodfx.listener;

import kr.rtustudio.bloodfx.BloodFX;
import kr.rtustudio.bloodfx.configuration.EffectConfig;
import kr.rtustudio.bloodfx.configuration.ParticleConfig;
import kr.rtustudio.bloodfx.event.BloodEvent;
import kr.rtustudio.bloodfx.manager.ToggleManager;
import kr.rtustudio.framework.bukkit.api.listener.RSListener;
import kr.rtustudio.framework.bukkit.api.platform.MinecraftVersion;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import java.util.Map;

@SuppressWarnings("unused")
public class EntityDamageByEntity extends RSListener<BloodFX> {

    private final EffectConfig effectConfig;
    private final ParticleConfig particleConfig;
    private final ToggleManager manager;

    public EntityDamageByEntity(BloodFX plugin) {
        super(plugin);
        this.effectConfig = plugin.getConfiguration(EffectConfig.class);
        this.particleConfig = plugin.getConfiguration(ParticleConfig.class);
        this.manager = plugin.getToggleManager();
    }

    @EventHandler
    private void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        Entity victim = e.getEntity();
        Entity attacker = e.getDamager();
        EntityType type = victim.getType();

        Map<EntityType, Material> particles = particleConfig.getParticles();
        Material material = particles.getOrDefault(type, particleConfig.getDefaultParticle());
        if (material == null) return;

        Location hitLoc;
        if (attacker instanceof Projectile projectile) {
            hitLoc = fromProjectile(projectile, victim);
        } else if (attacker instanceof LivingEntity living) hitLoc = fromMelee(living, victim);
        else hitLoc = victim.getBoundingBox().getCenter().toLocation(victim.getWorld());

        BloodEvent event = new BloodEvent(attacker, victim, hitLoc, material);
        Bukkit.getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            BlockData blockCrackData = material.createBlockData();
            for (Player player : Bukkit.getOnlinePlayers()) {
                World world = player.getWorld();
                if (world == victim.getWorld() && manager.get(player.getUniqueId())) {
                    Particle particle = MinecraftVersion.isSupport("1.20.5") ? Particle.BLOCK : Particle.valueOf("BLOCK_CRACK");
                    player.spawnParticle(particle, hitLoc.getX(), hitLoc.getY(), hitLoc.getZ(), effectConfig.getParticle().getAmount(), blockCrackData);
                }
            }
        }
    }

    private Location fromMelee(LivingEntity attacker, Entity victim) {
        if (effectConfig.getParticle().isPrecisionLocation()) {
            Location loc1 = attacker.getEyeLocation();
            Location loc2 = attacker.getEyeLocation().add(attacker.getLocation().getDirection().multiply(10));
            return applyVariance(victim, loc1, loc2);
        } else return victim.getBoundingBox().getCenter().toLocation(victim.getWorld());
    }

    private Location fromProjectile(Entity projectile, Entity victim) {
        if (effectConfig.getParticle().isPrecisionLocation()) {
            Location loc1 = projectile.getLocation().add(projectile.getVelocity().multiply(-3));
            Location loc2 = projectile.getLocation().add(projectile.getVelocity().multiply(3));
            return applyVariance(victim, loc1, loc2);
        } else return victim.getBoundingBox().getCenter().toLocation(victim.getWorld());
    }

    private Location applyVariance(Entity victim, Location loc1, Location loc2) {
        Vector vector = loc2.toVector().subtract(loc1.toVector());
        for (double i = 1.0D; i <= loc1.distance(loc2); i += effectConfig.getParticle().getVariance()) {
            vector.multiply(i);
            loc1.add(vector);
            boolean isX = false;
            boolean isY = false;
            boolean isZ = false;
            if (victim.getBoundingBox().getMinX() - 0.5D <= loc1.getX() && victim
                    .getBoundingBox().getMaxX() + 0.5D >= loc1.getX())
                isX = true;
            if (victim.getBoundingBox().getMinY() - 0.5D <= loc1.getY() && victim
                    .getBoundingBox().getMaxY() + 0.5D >= loc1.getY())
                isY = true;
            if (victim.getBoundingBox().getMinZ() - 0.5D <= loc1.getZ() && victim
                    .getBoundingBox().getMaxZ() + 0.5D >= loc1.getZ())
                isZ = true;
            if (isX && isY && isZ)
                return loc1;
            loc1.subtract(vector);
            vector.normalize();
        }
        return victim.getBoundingBox().getCenter().toLocation(victim.getWorld());
    }

}
