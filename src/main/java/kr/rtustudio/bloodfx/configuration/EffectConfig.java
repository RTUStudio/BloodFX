package kr.rtustudio.bloodfx.configuration;

import kr.rtustudio.configurate.objectmapping.meta.Comment;
import kr.rtustudio.framework.bukkit.api.configuration.ConfigurationPart;
import lombok.Getter;

@Getter
@SuppressWarnings({"unused", "FieldMayBeFinal"})
public class EffectConfig extends ConfigurationPart {

    @Comment("""
            Keep damage indicator particles
            데미지 인디케이터 파티클을 유지합니다""")
    private boolean vanillaParticle = false;

    @Comment("""
            Blood particle settings
            혈흔 파티클 설정""")
    private Particle particle;

    @Getter
    public static class Particle extends ConfigurationPart {

        @Comment("""
                Variance of particle spawn location
                파티클 생성 위치 분산도""")
        private double variance = 0.5;

        @Comment("""
                Amount of particle
                파티클 스폰 수""")
        private int amount = 15;

        @Comment("""
                Use more precise hit locations
                더 정확한 피격 위치를 사용합니다""")
        private boolean precisionLocation = true;
    }
}
