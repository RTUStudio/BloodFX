package kr.rtustudio.bloodfx;

import kr.astria.testing.BaseRSPluginTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BloodFXTest extends BaseRSPluginTest<BloodFX> {

    @Override
    protected BloodFX createPluginMock() {
        return loadPlugin(BloodFX.class);
    }

    // ==================== 플러그인 초기화 ====================

    @Test
    @DisplayName("서버 초기화(MockBukkit) 시 런타임 예외가 발생하지 않고 로드된다")
    void should_load_without_exceptions() {
        assertNotNull(plugin, "플러그인이 정상적으로 로드되지 않았습니다.");
        assertTrue(plugin.isEnabled(), "플러그인이 비활성화된 상태입니다.");
    }

    @Test
    @DisplayName("플러그인 싱글톤 인스턴스가 설정된다")
    void should_set_singleton_instance() {
        assertSame(plugin, BloodFX.getInstance(), "싱글톤 인스턴스가 올바르게 설정되지 않았습니다.");
    }

    // ==================== 명령어 ====================

    @Test
    @DisplayName("명령어(/bloodfx)가 정상적으로 등록되어 있다")
    void should_register_command() {
        verifyCommand("bloodfx");
    }

    @Test
    @DisplayName("기본 명령어 실행 시 예외가 발생하지 않는다")
    void should_execute_command_without_exception() {
        var player = safeAddPlayer();
        if (player == null) return;
        assertDoesNotThrow(() -> {
            player.performCommand("bloodfx");
            server.getScheduler().performTicks(20L);
        });
    }

    // ==================== 매니저 ====================

    @Test
    @DisplayName("ToggleManager가 정상적으로 초기화된다")
    void should_initialize_toggle_manager() {
        assertNotNull(plugin.getToggleManager(), "ToggleManager가 초기화되지 않았습니다.");
    }

    // ==================== 이벤트 시뮬레이션 ====================

    @Test
    @DisplayName("플레이어 입장 시 이벤트 핸들러가 예외 없이 동작한다")
    void should_handle_player_join_without_exception() {
        var player = safeAddPlayer();
        if (player == null) return;
        assertDoesNotThrow(() -> server.getScheduler().performTicks(20L));
    }

    @Test
    @DisplayName("플레이어 퇴장 시 이벤트 핸들러가 예외 없이 동작한다")
    void should_handle_player_quit_without_exception() {
        var player = safeAddPlayer();
        if (player == null) return;
        assertDoesNotThrow(() -> {
            player.disconnect();
            server.getScheduler().performTicks(20L);
        });
    }
}
