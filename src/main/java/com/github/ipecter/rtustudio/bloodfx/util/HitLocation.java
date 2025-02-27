package com.github.ipecter.rtustudio.bloodfx.util;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class HitLocation {

    public static Location fromMelee(LivingEntity attacker, Entity victim, double accuracy) {
        Location loc1 = attacker.getEyeLocation();
        Location loc2 = attacker.getEyeLocation().add(attacker.getLocation().getDirection().multiply(10));
        Vector vector = getDirectionBetweenLocations(loc1, loc2);
        double i;
        for (i = 1.0D; i <= loc1.distance(loc2); i += accuracy) {
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

    public static Location fromProjectile(Entity projectile, Entity victim, double accuracy) {
        Location loc1 = projectile.getLocation().add(projectile.getVelocity().multiply(-3));
        Location loc2 = projectile.getLocation().add(projectile.getVelocity().multiply(3));
        Vector vector = getDirectionBetweenLocations(loc1, loc2);
        double i;
        for (i = 1.0D; i <= loc1.distance(loc2); i += accuracy) {
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

    private static Vector getDirectionBetweenLocations(Location Start, Location End) {
        Vector from = Start.toVector();
        Vector to = End.toVector();
        return to.subtract(from);
    }

}
