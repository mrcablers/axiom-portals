package com.axiomportals.mixin.client;

import com.axiomportals.PortalCompatibilityManager;
import com.axiomportals.client.ClientPortalManager;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for ClientPlayerEntity to sync Axiom data with server during portal traversal.
 */
@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {

    /**
     * Sync client state when entering a portal
     */
    @Inject(
        method = "tick",
        at = @At("HEAD"),
        cancellable = false
    )
    private void onPlayerTick(CallbackInfo info) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        
        if (PortalCompatibilityManager.isEntityInTransit(player)) {
            ClientPortalManager.onPortalEnter();
        }
    }

    /**
     * Restore client state after portal traversal
     */
    @Inject(
        method = "tick",
        at = @At("TAIL"),
        cancellable = false
    )
    private void afterPlayerTick(CallbackInfo info) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        
        if (!PortalCompatibilityManager.isEntityInTransit(player)) {
            ClientPortalManager.onPortalExit();
        }
    }
}
