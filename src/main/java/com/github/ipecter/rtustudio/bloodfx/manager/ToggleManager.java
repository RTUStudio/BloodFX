package com.github.ipecter.rtustudio.bloodfx.manager;

import com.github.ipecter.rtustudio.bloodfx.BloodFX;
import kr.rtuserver.framework.bukkit.api.platform.JSON;
import kr.rtuserver.framework.bukkit.api.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class ToggleManager {

    private final BloodFX plugin;
    private final Map<UUID, Boolean> map = new HashMap<>();

    public void put(UUID uuid, boolean value) {
        map.put(uuid, value);
    }

    public boolean get(UUID uuid) {
        return map.getOrDefault(uuid, true);
    }

    public void addPlayer(UUID uuid) {
        Storage storage = plugin.getStorage();
        storage.get("Toggle", JSON.of("uuid", uuid.toString())).thenAccept(result -> {
            if (result == null || result.isEmpty()) {
                storage.add("Toggle", JSON.of("uuid", uuid.toString()).append("toggle", true).get());
                map.put(uuid, true);
            } else map.put(uuid, result.getFirst().get("toggle").getAsBoolean());
        });
    }

    public void removePlayer(UUID uuid) {
        map.remove(uuid);
    }

    public void on(UUID uuid) {
        Storage storage = plugin.getStorage();
        storage.set("Toggle", JSON.of("uuid", uuid.toString()), JSON.of("toggle", true));
        map.put(uuid, true);
    }

    public void off(UUID uuid) {
        Storage storage = plugin.getStorage();
        storage.set("Toggle", JSON.of("uuid", uuid.toString()), JSON.of("toggle", false));
        map.put(uuid, false);
    }

}
