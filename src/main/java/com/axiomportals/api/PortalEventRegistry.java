package com.axiomportals.api;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import java.util.*;

/**
 * Event system for portal traversal events.
 * Allows external mods to register listeners and respond to portal events.
 */
public class PortalEventRegistry {
    private static final List<PortalEventListener> LISTENERS = Collections.synchronizedList(new ArrayList<>());

    /**
     * Register a portal event listener
     */
    public static void registerListener(PortalEventListener listener) {
        if (listener != null && !LISTENERS.contains(listener)) {
            LISTENERS.add(listener);
        }
    }

    /**
     * Unregister a portal event listener
     */
    public static void unregisterListener(PortalEventListener listener) {
        LISTENERS.remove(listener);
    }

    /**
     * Fire pre-traversal event
     */
    public static void firePrePortalTraverse(Entity entity) {
        for (PortalEventListener listener : LISTENERS) {
            try {
                listener.onPrePortalTraverse(entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Fire post-traversal event
     */
    public static void firePostPortalTraverse(Entity entity) {
        for (PortalEventListener listener : LISTENERS) {
            try {
                listener.onPostPortalTraverse(entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Fire data capture event
     */
    public static NbtCompound fireCaptureData(Entity entity) {
        NbtCompound combined = new NbtCompound();
        for (PortalEventListener listener : LISTENERS) {
            try {
                NbtCompound data = listener.captureData(entity);
                if (data != null) {
                    combined.copyFrom(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return combined;
    }

    /**
     * Fire data restore event
     */
    public static void fireRestoreData(Entity entity, NbtCompound data) {
        for (PortalEventListener listener : LISTENERS) {
            try {
                listener.restoreData(entity, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get all registered listeners
     */
    public static List<PortalEventListener> getListeners() {
        return new ArrayList<>(LISTENERS);
    }

    /**
     * Clear all listeners
     */
    public static void clearListeners() {
        LISTENERS.clear();
    }
}
