package com.axiomportals.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import com.axiomportals.config.CompatibilityVerifier;
import com.axiomportals.command.CompatibilityCommand;

/**
 * Server lifecycle event handler for Axiom-Portals
 */
public class ServerEventHandler {
    
    /**
     * Register server lifecycle events
     */
    public static void register() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            // Verify compatibility when server starts
            CompatibilityVerifier.verify();
            
            // Register commands (done via CommandRegistrationCallback)
            CompatibilityCommand.register();
        });
    }
}
