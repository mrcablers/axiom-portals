package com.axiomportals.api;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;

/**
 * Public API for Axiom-Portals compatibility events.
 * Third-party mods can use these listeners to hook into portal events.
 */
public interface PortalEventListener {
    
    /**
     * Called before an entity traverses a portal
     * @param entity The entity about to traverse
     * @return Whether the event was handled (cancellation not supported, always true)
     */
    boolean onPrePortalTraverse(Entity entity);

    /**
     * Called after an entity has traversed a portal
     * @param entity The entity that traversed
     * @return Whether the event was handled
     */
    boolean onPostPortalTraverse(Entity entity);

    /**
     * Called when entity data needs to be preserved
     * @param entity The entity
     * @return NBT data to preserve
     */
    NbtCompound captureData(Entity entity);

    /**
     * Called to restore entity data after traversal
     * @param entity The entity
     * @param data The captured data
     */
    void restoreData(Entity entity, NbtCompound data);
}
