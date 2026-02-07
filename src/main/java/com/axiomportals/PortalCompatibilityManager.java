package com.axiomportals;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import com.axiomportals.api.PortalEventRegistry;
import com.axiomportals.integration.PortalIntegrationHelper;
import com.axiomportals.config.AxiomPortalsConfig;
import java.util.*;

/**
 * Core compatibility manager for Immersive Portals and Axiom integration.
 * Handles event registration and coordination between mods.
 */
public class PortalCompatibilityManager {
    private static final Map<UUID, NbtCompound> PORTAL_TRAVERSAL_CACHE = Collections.synchronizedMap(new WeakHashMap<>());
    private static final Set<UUID> ENTITIES_IN_TRANSIT = Collections.synchronizedSet(new HashSet<>());
    private static volatile boolean initialized = false;

    public static void init() {
        if (initialized) return;
        initialized = true;

        // Load configuration
        AxiomPortalsConfig.load();
        
        // Initialize integrations with other mods
        PortalIntegrationHelper.initializeIntegrations();

        registerEventListeners();
        registerDimensionHandlers();
        
        AxiomPortalsConfig.debugLog("PortalCompatibilityManager initialized");
    }

    /**
     * Registers event listeners for portal traversal events
     */
    private static void registerEventListeners() {
        // This will be linked via mixins to actual Immersive Portals events
        registerEntityTeleportHandler();
        registerChunkLoadHandler();
        registerRenderingHandler();
    }

    /**
     * Entity teleportation through portals
     */
    private static void registerEntityTeleportHandler() {
        // Mixin hook: net.qouteall.immersive_portals.api.PortalAPI.onEntityTeleport
        // Handled by: MixinEntity
    }

    /**
     * Chunk loading/unloading during portal traversal
     */
    private static void registerChunkLoadHandler() {
        // Mixin hook: ServerWorld.addEntity and removeEntity
        // Handled by: MixinServerWorld
    }

    /**
     * Rendering layer coordination
     */
    private static void registerRenderingHandler() {
        // Client-side mixin hook for render event
        // Handled by: MixinGameRenderer
    }

    /**
     * Register dimension-specific handlers
     */
    private static void registerDimensionHandlers() {
        // Handles the nether, end, and custom dimensions
    }

    /**
     * Stores entity data before portal traversal
     */
    public static void onPrePortalTraverse(Entity entity) {
        if (entity == null || !AxiomPortalsConfig.ENABLE_NBT_PRESERVATION) return;
        
        UUID uuid = entity.getUuid();
        NbtCompound data = PortalTraversalData.captureAxiomData(entity);
        PORTAL_TRAVERSAL_CACHE.put(uuid, data);
        ENTITIES_IN_TRANSIT.add(uuid);
        
        // Fire event for external listeners
        PortalEventRegistry.firePrePortalTraverse(entity);
        
        if (AxiomPortalsConfig.LOG_PORTAL_TRAVERSAL) {
            AxiomPortalsConfig.debugLog("Pre-portal traverse: " + entity.getName().getString());
        }
    }

    /**
     * Restores entity data after portal traversal
     */
    public static void onPostPortalTraverse(Entity entity) {
        if (entity == null) return;
        
        UUID uuid = entity.getUuid();
        
        if (ENTITIES_IN_TRANSIT.contains(uuid)) {
            if (AxiomPortalsConfig.ENABLE_NBT_PRESERVATION) {
                NbtCompound data = PORTAL_TRAVERSAL_CACHE.get(uuid);
                if (data != null) {
                    PortalTraversalData.restoreAxiomData(entity, data);
                    
                    if (AxiomPortalsConfig.LOG_DATA_PRESERVATION) {
                        AxiomPortalsConfig.debugLog("Restored data for " + entity.getName().getString());
                    }
                }
            }
            
            ENTITIES_IN_TRANSIT.remove(uuid);
            PortalTraversalData.clearTraversalMarkers(entity);
        }
        
        // Fire event for external listeners
        PortalEventRegistry.firePostPortalTraverse(entity);
        
        if (AxiomPortalsConfig.LOG_PORTAL_TRAVERSAL) {
            AxiomPortalsConfig.debugLog("Post-portal traverse: " + entity.getName().getString());
        }
    }

    /**
     * Handles dimension changes
     */
    public static void onDimensionChange(Entity entity, ServerWorld oldWorld, ServerWorld newWorld) {
        if (entity == null || oldWorld == null || newWorld == null) return;
        
        PortalTraversalData.preserveDataAcrossDimensions(entity, oldWorld, newWorld);
    }

    /**
     * Cleans up traversal data for despawned entities
     */
    public static void onEntityRemove(Entity entity) {
        UUID uuid = entity.getUuid();
        PORTAL_TRAVERSAL_CACHE.remove(uuid);
        ENTITIES_IN_TRANSIT.remove(uuid);
    }

    /**
     * Check if entity is currently traversing a portal
     */
    public static boolean isEntityInTransit(Entity entity) {
        return entity != null && ENTITIES_IN_TRANSIT.contains(entity.getUuid());
    }

    /**
     * Get cached traversal data for an entity
     */
    public static NbtCompound getTraversalData(Entity entity) {
        if (entity == null) return null;
        return PORTAL_TRAVERSAL_CACHE.get(entity.getUuid());
    }

    /**
     * Clear all traversal caches
     */
    public static void clearAllCaches() {
        PORTAL_TRAVERSAL_CACHE.clear();
        ENTITIES_IN_TRANSIT.clear();
    }

    public static boolean isInitialized() {
        return initialized;
    }
}
