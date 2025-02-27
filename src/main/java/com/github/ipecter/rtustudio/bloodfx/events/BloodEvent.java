package com.github.ipecter.rtustudio.bloodfx.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@Setter
public class BloodEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final Entity attacker;

    private final Entity victim;

    private final Location location;

    private Material material;

    private boolean isCancelled = false;

    public BloodEvent(Entity attacker, Entity victim, Location location, Material material) {
        this.attacker = attacker;
        this.victim = victim;
        this.location = location;
        this.material = material;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}
