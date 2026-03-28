package kr.rtustudio.bloodfx;

import kr.astria.testing.BaseRSPluginTest;
import kr.rtustudio.bloodfx.configuration.ParticleConfig;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BloodFXTest extends BaseRSPluginTest<BloodFX> {

    @Override
    protected BloodFX createPluginMock() {
        return loadPlugin(BloodFX.class);
    }

    @Test
    @DisplayName("서버 초기화(MockBukkit) 시 런타임 예외가 발생하지 않고 로드된다")
    void should_load_without_exceptions() {
        assertNotNull(plugin, "플러그인이 정상적으로 로드되지 않았습니다.");
        assertTrue(plugin.isEnabled(), "플러그인이 비활성화된 상태입니다.");
    }

    @Test
    @DisplayName("BloodFX 명령어(/bloodfx)가 정상적으로 등록되어 있다")
    void should_register_command() {
        verifyCommand("bloodfx");
    }

    @Test
    @DisplayName("ParticleConfig가 정상적으로 로드되고 엔티티 파티클 맵이 비어있지 않다")
    void should_load_particle_config() {
        ParticleConfig config = plugin.getConfiguration(ParticleConfig.class);
        assertNotNull(config, "ParticleConfig가 null입니다.");

        Map<EntityType, Material> particles = config.getParticles();
        assertNotNull(particles, "파티클 맵이 null입니다.");
        assertFalse(particles.isEmpty(), "파티클 맵이 비어있습니다.");
    }

    @Test
    @DisplayName("ParticleConfig의 버전 분기가 정상적으로 처리된다 (END_CRYSTAL/ENDER_CRYSTAL)")
    void should_handle_version_branching() {
        ParticleConfig config = plugin.getConfiguration(ParticleConfig.class);
        Map<EntityType, Material> particles = config.getParticles();

        // 1.21.1 환경에서는 END_CRYSTAL이 존재해야 함
        boolean hasEndCrystal = particles.keySet().stream()
                .anyMatch(type -> type.name().contains("CRYSTAL"));
        assertTrue(hasEndCrystal, "END_CRYSTAL 또는 ENDER_CRYSTAL 설정이 없습니다.");
    }

    @Test
    @DisplayName("ToggleManager가 정상적으로 초기화된다")
    void should_initialize_toggle_manager() {
        assertNotNull(plugin.getToggleManager(), "ToggleManager가 null입니다.");
    }
}
