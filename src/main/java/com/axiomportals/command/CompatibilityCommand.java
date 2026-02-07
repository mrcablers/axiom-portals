package com.axiomportals.command;

import com.axiomportals.config.CompatibilityVerifier;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.literal;

/**
 * In-game command to check Axiom-Portals compatibility status
 * Usage: /axiom-portals status
 */
public class CompatibilityCommand {
    
    /**
     * Register the compatibility check command
     */
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                literal("axiom-portals")
                    .then(
                        literal("status")
                            .executes(CompatibilityCommand::executeStatus)
                    )
            );
        });
    }
    
    /**
     * Execute the status command
     */
    private static int executeStatus(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        String status = CompatibilityVerifier.getCompatibilityStatus();
        
        // Send status message to player
        source.sendFeedback(() -> net.minecraft.text.Text.of("§6" + status), false);
        
        return 1;
    }
}
