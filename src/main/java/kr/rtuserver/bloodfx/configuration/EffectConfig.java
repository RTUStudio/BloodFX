package kr.rtuserver.bloodfx.configuration;

import kr.rtuserver.framework.bukkit.api.RSPlugin;
import kr.rtuserver.framework.bukkit.api.config.RSConfiguration;
import lombok.Getter;
import org.bukkit.Material;

@Getter
public class EffectConfig extends RSConfiguration {

    private boolean vanillaParticle = false;
    private boolean precisionLocation = false;
    private double particleAccuracy = 0.5;
    private int particleAmount = 15;

    public EffectConfig(RSPlugin plugin) {
        super(plugin, "Effect.yml", null);
        setup(this);
    }

    private void init() {
        vanillaParticle = getBoolean("vanillaParticle", vanillaParticle, """
                바닐라 하트 파티클을 유지합니다
                Keep vanilla heart particles""");
        precisionLocation = getBoolean("spawnParticleGeneral", precisionLocation, """
                더 정확한 피격 위치를 사용합니다
                Use more precise hit locations""");
        particleAccuracy = getDouble("particle.accuracy", particleAccuracy, """
                파티클 스폰 위치의 정확도
                Accuracy of particle spawn location""");
        particleAmount = getInt("particle.amount", particleAmount, """
                파티클 스폰 수
                Amount of particle spawn""");
    }
}
