package com.axiomportals.config;

/**
 * Configuration options for Axiom-Portals compatibility mod.
 */
public class AxiomPortalsConfig {
    
    // Feature flags
    public static boolean ENABLE_CLIPBOARD_PRESERVATION = true;
    public static boolean ENABLE_SELECTION_PERSISTENCE = true;
    public static boolean ENABLE_NBT_PRESERVATION = true;
    public static boolean ENABLE_CHUNK_LOADING_FIX = true;
    public static boolean ENABLE_RENDERING_COMPATIBILITY = true;

    // Logging
    public static boolean DEBUG_MODE = false;
    public static boolean LOG_PORTAL_TRAVERSAL = false;
    public static boolean LOG_DATA_PRESERVATION = false;

    // Performance
    public static int MAX_TRAVERSAL_CACHE_SIZE = 1000;
    public static int TRAVERSAL_TIMEOUT_TICKS = 600; // 30 seconds
    public static boolean ENABLE_ASYNC_DATA_SYNC = true;

    // Compatibility
    public static boolean FORCE_COMPATIBILITY_MODE = false;
    public static boolean ENABLE_FORGE_SUPPORT = true;
    public static boolean ENABLE_FABRIC_SUPPORT = true;

    /**
     * Load configuration from file (if implemented)
     */
    public static void load() {
        // Config file loading would go here
        // For now, defaults are used
    }

    /**
     * Save configuration to file (if implemented)
     */
    public static void save() {
        // Config file saving would go here
    }

    /**
     * Reset to defaults
     */
    public static void reset() {
        ENABLE_CLIPBOARD_PRESERVATION = true;
        ENABLE_SELECTION_PERSISTENCE = true;
        ENABLE_NBT_PRESERVATION = true;
        ENABLE_CHUNK_LOADING_FIX = true;
        ENABLE_RENDERING_COMPATIBILITY = true;
        DEBUG_MODE = false;
        LOG_PORTAL_TRAVERSAL = false;
        LOG_DATA_PRESERVATION = false;
    }

    /**
     * Log message if debug mode is enabled
     */
    public static void debugLog(String message) {
        if (DEBUG_MODE) {
            System.out.println("[Axiom-Portals Debug] " + message);
        }
    }
}
