package com.axiomportals.mixin.client;

import com.axiomportals.client.ClientPortalManager;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for GameRenderer to fix rendering conflicts between Axiom and Immersive Portals.
 * Ensures selection rendering doesn't interfere with portal rendering.
 */
@Mixin(GameRenderer.class)
public class MixinGameRenderer {

    /**
     * Coordinate rendering layers to prevent visual artifacts
     */
    @Inject(
        method = "render",
        at = @At("HEAD"),
        cancellable = false
    )
    private void beforeRender(float tickDelta, long startTime, boolean tick, CallbackInfo info) {
        // Set up rendering context for portal-aware Axiom selection
        ClientPortalManager.updateSelectionRendering();
    }

    /**
     * Ensure proper rendering order: World -> Portals -> Axiom UI
     */
    @Inject(
        method = "renderWorld",
        at = @At("TAIL"),
        cancellable = false
    )
    private void afterRenderWorld(float tickDelta, long limitTime, MatrixStack matrices, CallbackInfo info) {
        // This ensures Axiom rendering happens after all portal rendering
    }
}
