package com.axiomportals.integration;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import java.util.*;

/**
 * Integration utilities for coordinating with Axiom and Immersive Portals.
 * Provides safe access to both mods' APIs without hard dependencies.
 */
public class PortalIntegrationHelper {
    
    private static volatile boolean axiomIntegrated = false;
    private static volatile boolean immersivePortalsIntegrated = false;

    /**
     * Initialize integration with both mods if available
     */
    public static void initializeIntegrations() {
        initializeAxiomIntegration();
        initializeImmersivePortalsIntegration();
    }

    /**
     * Set up Axiom integration
     */
    private static void initializeAxiomIntegration() {
        try {
            // Try to access Axiom API - this will fail gracefully if not installed
            Class.forName("com.github.cube_b2.data.DataStorage");
            axiomIntegrated = true;
            logIntegration("Axiom integration initialized successfully");
        } catch (ClassNotFoundException e) {
            logIntegration("Axiom not detected - some features will be limited");
            axiomIntegrated = false;
        }
    }

    /**
     * Set up Immersive Portals integration
     */
    private static void initializeImmersivePortalsIntegration() {
        try {
            // Try to access Immersive Portals API
            Class.forName("qouteall.immerseive_portals.api.PortalAPI");
            immersivePortalsIntegrated = true;
            logIntegration("Immersive Portals integration initialized successfully");
        } catch (ClassNotFoundException e) {
            logIntegration("Immersive Portals not detected - portal events will be limited");
            immersivePortalsIntegrated = false;
        }
    }

    /**
     * Check if Axiom is integrated
     */
    public static boolean isAxiomIntegrated() {
        return axiomIntegrated;
    }

    /**
     * Check if Immersive Portals is integrated
     */
    public static boolean isImmersivePortalsIntegrated() {
        return immersivePortalsIntegrated;
    }

    /**
     * Check if both required mods are integrated
     */
    public static boolean isFullyIntegrated() {
        return axiomIntegrated && immersivePortalsIntegrated;
    }

    /**
     * Extract Axiom-specific data from entity NBT
     * Safe to call even if Axiom isn't loaded
     */
    public static NbtCompound extractAxiomData(Entity entity) {
        NbtCompound data = new NbtCompound();
        
        if (!axiomIntegrated) return data;
        
        try {
            NbtCompound entityData = new NbtCompound();
            entity.writeNbt(entityData);
            
            String[] axiomKeys = {
                "axiomClipboard",
                "axiomSelection",
                "axiomUndoStack",
                "axiomRedoStack",
                "axiomBuffer"
            };
            
            for (String key : axiomKeys) {
                if (entityData.contains(key)) {
                    data.put(key, entityData.get(key));
                }
            }
        } catch (Exception e) {
            logError("Failed to extract Axiom data: " + e.getMessage());
        }
        
        return data;
    }

    /**
     * Apply Axiom data to entity NBT
     * Safe to call even if Axiom isn't loaded
     */
    public static void applyAxiomData(Entity entity, NbtCompound data) {
        if (!axiomIntegrated || data == null || data.isEmpty()) return;
        
        try {
            NbtCompound entityData = new NbtCompound();
            entity.writeNbt(entityData);
            
            for (String key : data.getKeys()) {
                entityData.put(key, data.get(key));
            }
            
            entity.readNbt(entityData);
        } catch (Exception e) {
            logError("Failed to apply Axiom data: " + e.getMessage());
        }
    }

    /**
     * Utility logging
     */
    private static void logIntegration(String message) {
        System.out.println("[Axiom-Portals Integration] " + message);
    }

    private static void logError(String message) {
        System.err.println("[Axiom-Portals Error] " + message);
    }
}
