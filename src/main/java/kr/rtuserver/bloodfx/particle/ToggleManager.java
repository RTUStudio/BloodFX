package kr.rtuserver.bloodfx.particle;

import kr.rtuserver.bloodfx.RSBloodFX;
import kr.rtuserver.framework.bukkit.api.storage.Storage;
import kr.rtuserver.framework.bukkit.api.utility.platform.JSON;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class ToggleManager {

    private final RSBloodFX plugin;
    @Getter
    private final Map<UUID, Boolean> map = new HashMap<>();

    public void addPlayer(UUID uuid) {
        Storage storage = plugin.getStorage();
        storage.add("Toggle", JSON.of("uuid", uuid.toString()).append("toggle", true).get());
        map.put(uuid, true);
    }

    public void removePlayer(UUID uuid) {
        map.remove(uuid);
    }

    public void on(UUID uuid) {
        Storage storage = plugin.getStorage();
        storage.set("Toggle", Pair.of("uuid", uuid.toString()), Pair.of("toggle", true));
        map.put(uuid, true);
    }

    public void off(UUID uuid) {
        Storage storage = plugin.getStorage();
        storage.set("Toggle", Pair.of("uuid", uuid.toString()), Pair.of("toggle", false));
        map.put(uuid, false);
    }

}
