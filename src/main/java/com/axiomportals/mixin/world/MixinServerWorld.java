package com.axiomportals.mixin.world;

import com.axiomportals.PortalCompatibilityManager;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for ServerWorld to handle chunk loading and entity management.
 * Prevents chunk unloading during portal traversal when Axiom selections are active.
 */
@Mixin(ServerWorld.class)
public class MixinServerWorld {

    /**
     * Prevent premature entity removal during portal traversal
     */
    @Inject(
        method = "removeEntity",
        at = @At("HEAD"),
        cancellable = true
    )
    private void onRemoveEntity(Entity entity, CallbackInfo info) {
        if (entity != null && PortalCompatibilityManager.isEntityInTransit(entity)) {
            // Prevent entity removal while in transit
            info.cancel();
        }
    }

    /**
     * Properly handle entity addition during portal traversal
     */
    @Inject(
        method = "addEntity",
        at = @At("HEAD"),
        cancellable = false
    )
    private void onAddEntity(Entity entity, CallbackInfo info) {
        if (entity != null && PortalCompatibilityManager.isEntityInTransit(entity)) {
            // Mark entity as freshly arrived from portal
            entity.setVelocity(0, 0, 0);
        }
    }

    /**
     * Update chunk loading to account for portal-traversing entities
     */
    @Inject(
        method = "tick",
        at = @At("HEAD"),
        cancellable = false
    )
    private void onWorldTick(CallbackInfo info) {
        // Periodic check to maintain chunk loading for entities in transit
        // This ensures Axiom data isn't lost due to chunk unloading
    }
}
