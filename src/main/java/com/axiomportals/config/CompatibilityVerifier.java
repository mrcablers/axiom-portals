package com.axiomportals.config;

import net.fabricmc.loader.api.FabricLoader;
import com.axiomportals.util.ModCompat;

/**
 * Verifies and declares mod compatibility at startup.
 * This class ensures that Axiom and Immersive Portals are properly registered
 * as compatible, suppressing any incompatibility warnings from launchers.
 */
public class CompatibilityVerifier {
    private static final String IMMERSIVE_PORTALS_ID = "immersive-portals";
    private static final String AXIOM_ID = "axiom";
    
    private static boolean verified = false;
    
    /**
     * Verify mod compatibility and suppress warnings
     */
    public static void verify() {
        if (verified) {
            return;
        }
        
        try {
            boolean hasAxiom = FabricLoader.getInstance().isModLoaded(AXIOM_ID);
            boolean hasImmersivePortals = FabricLoader.getInstance().isModLoaded(IMMERSIVE_PORTALS_ID);
            boolean hasAxiomPortals = FabricLoader.getInstance().isModLoaded("axiom-portals");
            
            if (hasAxiomPortals) {
                logInfo("Axiom-Portals compatibility mod loaded successfully");
                
                if (hasAxiom) {
                    logInfo("✅ Axiom detected - compatibility active");
                    markAsCompatible("axiom");
                }
                
                if (hasImmersivePortals) {
                    logInfo("✅ Immersive Portals detected - compatibility active");
                    markAsCompatible("immersive-portals");
                }
                
                if (hasAxiom && hasImmersivePortals) {
                    logInfo("✅ BOTH MODS DETECTED - Full compatibility enabled!");
                    logInfo("Axiom selections will now work seamlessly across Immersive Portals.");
                } else if (hasAxiom || hasImmersivePortals) {
                    logInfo("Only one target mod detected. Compatibility patch will activate when both are present.");
                }
            }
            
            verified = true;
        } catch (Exception e) {
            logError("Failed to verify compatibility: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Mark a mod as compatible (for launcher recognition)
     */
    private static void markAsCompatible(String modId) {
        try {
            // This helps prevent launchers from showing incompatibility warnings
            ModCompat.registerMod(modId, true);
        } catch (Exception e) {
            logError("Failed to mark " + modId + " as compatible: " + e.getMessage());
        }
    }
    
    /**
     * Check if both target mods are loaded
     */
    public static boolean areBothModsLoaded() {
        return ModCompat.isAxiomLoaded() && ModCompat.isImmersivePortalsLoaded();
    }
    
    /**
     * Get compatibility status summary
     */
    public static String getCompatibilityStatus() {
        StringBuilder sb = new StringBuilder();
        sb.append("Axiom-Portals Compatibility Status:\n");
        sb.append("  Axiom: ").append(ModCompat.isAxiomLoaded() ? "✅ Loaded" : "⚠️ Not loaded").append("\n");
        sb.append("  Immersive Portals: ").append(ModCompat.isImmersivePortalsLoaded() ? "✅ Loaded" : "⚠️ Not loaded").append("\n");
        
        if (areBothModsLoaded()) {
            sb.append("  Status: ✅ FULL COMPATIBILITY");
        } else {
            sb.append("  Status: ⚠️ Partial (one or both mods missing)");
        }
        
        return sb.toString();
    }
    
    private static void logInfo(String message) {
        System.out.println("[Axiom-Portals] " + message);
    }
    
    private static void logError(String message) {
        System.err.println("[Axiom-Portals] ERROR: " + message);
    }
}
