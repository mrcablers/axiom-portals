package com.axiomportals;

import com.axiomportals.config.CompatibilityVerifier;
import com.axiomportals.command.CompatibilityCommand;
import com.axiomportals.util.ConflictPatcher;

public class AxiomPortalsMain {
    public static final String MOD_ID = "axiom-portals";
    public static final String MOD_NAME = "Axiom-Portals Compatibility";

    public static void onInitialize() {
        // Log compatibility information
        ConflictPatcher.logConflictInfo();
        
        // Attempt to patch any runtime conflicts
        ConflictPatcher.patchAllConflicts();
        
        // Verify and suppress incompatibility warnings
        CompatibilityVerifier.verify();
        
        // Register compatibility commands
        CompatibilityCommand.register();
        
        // Initialize portal compatibility
        PortalCompatibilityManager.init();
    }
}
