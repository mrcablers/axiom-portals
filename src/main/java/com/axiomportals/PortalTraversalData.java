package com.axiomportals;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * Manages portal traversal data for Axiom-Portals compatibility.
 * Handles entity data preservation through dimensional portals.
 */
public class PortalTraversalData {
    private static final String AXIOM_DATA_KEY = "axiomPortalData";
    private static final String AXIOM_CLIPBOARD_KEY = "axiomClipboard";
    private static final String AXIOM_SELECTION_KEY = "axiomSelection";
    private static final String ORIGIN_WORLD_KEY = "originWorld";
    private static final String ORIGIN_POS_KEY = "originPos";
    private static final String TRAVERSAL_TICK_KEY = "traversalTick";

    /**
     * Captures Axiom-related data before traversing a portal
     */
    public static NbtCompound captureAxiomData(Entity entity) {
        NbtCompound data = new NbtCompound();
        
        if (entity.hasCustomName()) {
            data.putString("customName", entity.getCustomName().getString());
        }

        // Try to extract Axiom-specific NBT if present
        NbtCompound entityData = new NbtCompound();
        entity.writeNbt(entityData);
        
        if (entityData.contains(AXIOM_CLIPBOARD_KEY)) {
            data.put(AXIOM_CLIPBOARD_KEY, entityData.get(AXIOM_CLIPBOARD_KEY));
        }
        
        if (entityData.contains(AXIOM_SELECTION_KEY)) {
            data.put(AXIOM_SELECTION_KEY, entityData.get(AXIOM_SELECTION_KEY));
        }

        // Store origin information
        data.putString("originWorld", entity.world.getRegistryKey().getValue().toString());
        data.putDouble("originX", entity.getX());
        data.putDouble("originY", entity.getY());
        data.putDouble("originZ", entity.getZ());
        data.putLong("traversalTick", entity.world.getTime());

        return data;
    }

    /**
     * Restores Axiom data after traversing a portal
     */
    public static void restoreAxiomData(Entity entity, NbtCompound data) {
        if (data == null) return;

        NbtCompound entityData = new NbtCompound();
        entity.writeNbt(entityData);

        // Restore Axiom-specific data
        if (data.contains(AXIOM_CLIPBOARD_KEY)) {
            entityData.put(AXIOM_CLIPBOARD_KEY, data.get(AXIOM_CLIPBOARD_KEY));
        }

        if (data.contains(AXIOM_SELECTION_KEY)) {
            entityData.put(AXIOM_SELECTION_KEY, data.get(AXIOM_SELECTION_KEY));
        }

        entity.readNbt(entityData);
    }

    /**
     * Prevents data loss when entity crosses dimensional boundaries
     */
    public static void preserveDataAcrossDimensions(Entity entity, World fromWorld, World toWorld) {
        NbtCompound nbt = new NbtCompound();
        entity.writeNbt(nbt);
        
        // Mark entity as portal-traversing to prevent unloading
        nbt.putBoolean("axiom_portal_traversing", true);
        nbt.putString("axiom_source_world", fromWorld.getRegistryKey().getValue().toString());
        
        entity.readNbt(nbt);
    }

    /**
     * Clears portal traversal markers
     */
    public static void clearTraversalMarkers(Entity entity) {
        NbtCompound nbt = new NbtCompound();
        entity.writeNbt(nbt);
        
        nbt.remove("axiom_portal_traversing");
        nbt.remove("axiom_source_world");
        
        entity.readNbt(nbt);
    }
}
