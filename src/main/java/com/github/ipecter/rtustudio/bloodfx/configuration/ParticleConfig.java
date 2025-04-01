package com.github.ipecter.rtustudio.bloodfx.configuration;

import com.github.ipecter.rtustudio.bloodfx.BloodFX;
import kr.rtuserver.framework.bukkit.api.configuration.RSConfiguration;
import lombok.Getter;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ParticleConfig extends RSConfiguration<BloodFX> {

    private final Map<String, Material> map = new HashMap<>();
    private Material defaultParticle = Material.REDSTONE_BLOCK;

    public ParticleConfig(BloodFX plugin) {
        super(plugin, "Particle.yml", null);
        setup(this);
    }

    private void init() {
        map.clear();
        getConfig().setComment("", """
                Mob: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/entity/EntityType.html
                Material: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html
                
                예시 / Example
                [Mob]: [Material]""");
        defaultParticle = parse(getString("DEFAULT", "REDSTONE_BLOCK", """
                기본 파티클
                Default particle"""));
        for (String key : getConfig().getKeys(false)) {
            if (key.equalsIgnoreCase("DEFAULT")) continue;
            map.put(namespacedKey(key), parse(getString(key, "")));
        }
    }

    private String namespacedKey(String value) {
        if (value.contains(":")) return value.toLowerCase();
        else return "minecraft:" + value.toLowerCase();
    }

    private Material parse(String value) {
        Material material = Material.getMaterial(value.toUpperCase());
        if (material == null) return defaultParticle;
        return material;
    }

}
