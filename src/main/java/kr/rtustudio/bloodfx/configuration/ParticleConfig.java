package kr.rtustudio.bloodfx.configuration;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import kr.rtustudio.configurate.model.ConfigurationPart;
import kr.rtustudio.configurate.objectmapping.meta.Comment;
import kr.rtustudio.configurate.objectmapping.meta.Setting;
import kr.rtustudio.framework.bukkit.api.platform.MinecraftVersion;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.Map;

@Getter
@SuppressWarnings({"unused", "CanBeFinal", "FieldCanBeLocal", "FieldMayBeFinal", "InnerClassMayBeStatic"})
public class ParticleConfig extends ConfigurationPart {

    @Comment("""
            Mob: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/entity/EntityType.html
            Material: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html
            
            [Mob]: [Block Material]
            
            You can omit the namespace if it is minecraft
            네임스페이스가 minecraft인 경우 생략할 수 있습니다""")
    @Setting("default")
    private Material defaultParticle = Material.REDSTONE_BLOCK;

    @Setting(nodeFromParent = true)
    private final Map<EntityType, Material> particles = make(new Object2ObjectOpenHashMap<>(), map -> {
        map.put(EntityType.ARMOR_STAND, Material.AIR);
        map.put(EntityType.CREEPER, Material.OAK_LEAVES);
        EntityType endCrystal = MinecraftVersion.isSupport("1.20.5") ? EntityType.valueOf("END_CRYSTAL") : EntityType.valueOf("ENDER_CRYSTAL");
        map.put(endCrystal, Material.GLASS);
        map.put(EntityType.ENDER_DRAGON, Material.CRYING_OBSIDIAN);
        map.put(EntityType.ENDERMAN, Material.BLACK_SHULKER_BOX);
        map.put(EntityType.SKELETON, Material.WHITE_SHULKER_BOX);
        map.put(EntityType.SLIME, Material.SLIME_BLOCK);
        map.put(EntityType.SPIDER, Material.CYAN_SHULKER_BOX);
        map.put(EntityType.STRAY, Material.WHITE_SHULKER_BOX);
        map.put(EntityType.ZOMBIE, Material.GREEN_SHULKER_BOX);
        map.put(EntityType.ZOMBIE_VILLAGER, Material.GREEN_SHULKER_BOX);
    });
}

