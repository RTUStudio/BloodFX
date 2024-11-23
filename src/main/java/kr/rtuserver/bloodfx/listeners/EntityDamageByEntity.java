package kr.rtuserver.bloodfx.listeners;

import kr.rtuserver.bloodfx.RSBloodFX;
import kr.rtuserver.bloodfx.configuration.EffectConfig;
import kr.rtuserver.bloodfx.configuration.ParticleConfig;
import kr.rtuserver.bloodfx.events.BloodEvent;
import kr.rtuserver.bloodfx.util.HitLocation;
import kr.rtuserver.bloodfx.util.ParticleUtil;
import kr.rtuserver.framework.bukkit.api.listener.RSListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntity extends RSListener {

    private final EffectConfig effectConfig;
    private final ParticleConfig particleConfig;

    public EntityDamageByEntity(RSBloodFX plugin) {
        super(plugin);
        effectConfig = plugin.getEffectConfig();
        particleConfig = plugin.getParticleConfig();
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
        if (!event.isCancelled())
            ParticleUtil.spawn(victim.getWorld(), hitLoc, material, effectConfig.getParticleAmount());
    }
}
