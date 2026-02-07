package com.axiomportals.util;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

/**
 * Utilities for handling NBT data safely across mod interop.
 */
public class NBTHelper {
    
    /**
     * Safely merge NBT data without overwriting critical tags
     */
    public static void mergeNBT(NbtCompound source, NbtCompound target) {
        for (String key : source.getKeys()) {
            if (!target.contains(key)) {
                target.put(key, source.get(key));
            }
        }
    }

    /**
     * Copy Axiom-specific NBT tags between compounds
     */
    public static void copyAxiomTags(NbtCompound source, NbtCompound target) {
        String[] axiomTags = {
            "axiomClipboard",
            "axiomSelection",
            "axiomUndoStack",
            "axiomRedoStack",
            "axiomBuffer",
            "axiomData"
        };

        for (String tag : axiomTags) {
            if (source.contains(tag)) {
                target.put(tag, source.get(tag).copy());
            }
        }
    }

    /**
     * Validate entity NBT integrity
     */
    public static boolean isNBTIntact(Entity entity) {
        try {
            NbtCompound tag = new NbtCompound();
            entity.writeNbt(tag);
            return !tag.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Clear portal-specific NBT marks
     */
    public static void clearPortalMarks(Entity entity) {
        NbtCompound tag = new NbtCompound();
        entity.writeNbt(tag);
        
        tag.remove("axiom_portal_traversing");
        tag.remove("axiom_source_world");
        tag.remove("axiom_keep_alive");
        
        entity.readNbt(tag);
    }
}
