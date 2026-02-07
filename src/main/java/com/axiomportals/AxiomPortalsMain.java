package com.axiomportals;

import com.axiomportals.config.CompatibilityVerifier;
import com.axiomportals.command.CompatibilityCommand;

public class AxiomPortalsMain {
    public static final String MOD_ID = "axiom-portals";
    public static final String MOD_NAME = "Axiom-Portals Compatibility";

    public static void onInitialize() {
        // Verify and suppress incompatibility warnings
        CompatibilityVerifier.verify();
        
        // Register compatibility commands
        CompatibilityCommand.register();
        
        // Initialize portal compatibility
        PortalCompatibilityManager.init();
    }
}
