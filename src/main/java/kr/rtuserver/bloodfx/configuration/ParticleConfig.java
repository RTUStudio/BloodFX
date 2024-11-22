package kr.rtuserver.bloodfx.configuration;

import kr.rtuserver.framework.bukkit.api.RSPlugin;
import kr.rtuserver.framework.bukkit.api.config.RSConfiguration;
import lombok.Getter;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Getter
public class ParticleConfig extends RSConfiguration {

    private Material defaultParticle = Material.REDSTONE_BLOCK;
    private final Map<String, Material> map = new HashMap<>();

    public ParticleConfig(RSPlugin plugin) {
        super(plugin, "Particle.yml", null);
        setup(this);
    }

    private void init() {
        map.clear();
        getConfig().setComment("", """
                Mob: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/entity/EntityType.html
                Material: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html
                Particle: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Particle.html
                
                예시 / Example
                [Mob]: material:[Material]
                [Mob]: particle:[Particle]""");
        defaultParticle = parse(getString("DEFAULT", "REDSTONE_BLOCK", """
                기본 파티클
                Default particle"""));
        for (String key : getConfig().getKeys(false)) {
            if (key.equalsIgnoreCase("DEFAULT")) continue;
            map.put(namespacedKey(key), parse(getString(key, "")));
        }
    }

    private String namespacedKey(String value) {
        if (value.contains(":")) return value;
        else return "minecraft:" + value;
    }

    private Material parse(String value) {
        Material material = Material.getMaterial(value.toUpperCase());
        if (material == null) return defaultParticle;
        return material;
    }
}
