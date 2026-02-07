package com.axiomportals.mixin.entity;

import com.axiomportals.PortalCompatibilityManager;
import com.axiomportals.PortalTraversalData;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for ServerPlayerEntity to handle player-specific portal traversal.
 * Ensures player data like selections, clipboard, and NBT tags persist.
 */
@Mixin(ServerPlayerEntity.class)
public class MixinServerPlayer {

    /**
     * Intercept player teleportation through portals
     */
    @Inject(
        method = "teleport",
        at = @At("HEAD"),
        cancellable = false
    )
    private void onPlayerTeleport(ServerWorld world, double x, double y, double z, 
                                   float yaw, float pitch, CallbackInfo info) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        PortalCompatibilityManager.onPrePortalTraverse(player);
    }

    /**
     * Restore player data after teleportation
     */
    @Inject(
        method = "teleport",
        at = @At("RETURN"),
        cancellable = false
    )
    private void afterPlayerTeleport(ServerWorld world, double x, double y, double z, 
                                      float yaw, float pitch, CallbackInfo info) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        PortalCompatibilityManager.onPostPortalTraverse(player);
    }

    /**
     * Sync player data with dimension changes
     */
    @Inject(
        method = "changeDimension",
        at = @At("HEAD"),
        cancellable = false
    )
    private void onChangeDimension(ServerWorld world, CallbackInfo info) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        ServerWorld oldWorld = player.getServerWorld();
        
        if (oldWorld != null && !oldWorld.equals(world)) {
            PortalTraversalData.preserveDataAcrossDimensions(player, oldWorld, world);
        }
    }
}
