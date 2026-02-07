package com.axiomportals.mixin.entity;

import com.axiomportals.PortalCompatibilityManager;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin to intercept entity changes and portal traversal events.
 * Preserves Axiom data during dimensional transitions.
 */
@Mixin(Entity.class)
public abstract class MixinEntity {
    @Shadow
    public abstract NbtCompound getTag();

    @Shadow
    public abstract void setTag(NbtCompound tag);

    /**
     * Capture Axiom data when entity is about to teleport
     * Hook point: Just before dimension change
     */
    @Inject(
        method = "changeDimension",
        at = @At("HEAD"),
        cancellable = false
    )
    private void onChangeDimension(CallbackInfo info) {
        Entity entity = (Entity) (Object) this;
        PortalCompatibilityManager.onPrePortalTraverse(entity);
    }

    /**
     * Restore Axiom data after dimension change
     * Hook point: After dimension change completes
     */
    @Inject(
        method = "changeDimension",
        at = @At("RETURN"),
        cancellable = false
    )
    private void afterChangeDimension(CallbackInfo info) {
        Entity entity = (Entity) (Object) this;
        PortalCompatibilityManager.onPostPortalTraverse(entity);
    }

    /**
     * Prevent entity unloading during portal traversal
     */
    @Inject(
        method = "tick",
        at = @At("HEAD"),
        cancellable = false
    )
    private void onEntityTick(CallbackInfo info) {
        Entity entity = (Entity) (Object) this;
        
        if (PortalCompatibilityManager.isEntityInTransit(entity)) {
            NbtCompound tag = this.getTag();
            if (tag != null) {
                tag.putBoolean("axiom_keep_alive", true);
            }
        }
    }
}
