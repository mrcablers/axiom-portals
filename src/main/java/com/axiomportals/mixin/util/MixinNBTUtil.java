package com.axiomportals.mixin.util;

import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for NBT utilities to ensure Axiom data integrity.
 * Validates and preserves Axiom-specific NBT tags during serialization.
 */
@Mixin(NbtCompound.class)
public class MixinNBTUtil {

    /**
     * Ensure Axiom clipboard data is properly preserved during NBT operations
     */
    @Inject(
        method = "copy",
        at = @At("RETURN"),
        cancellable = false
    )
    private void onNBTCopy(CallbackInfoReturnable<NbtCompound> info) {
        NbtCompound original = (NbtCompound) (Object) this;
        NbtCompound copy = info.getReturnValue();

        // Preserve Axiom-specific NBT tags during copy
        if (original.contains("axiomClipboard")) {
            copy.put("axiomClipboard", original.get("axiomClipboard"));
        }

        if (original.contains("axiomSelection")) {
            copy.put("axiomSelection", original.get("axiomSelection"));
        }

        if (original.contains("axiomUndoStack")) {
            copy.put("axiomUndoStack", original.get("axiomUndoStack"));
        }
    }

    /**
     * Validate NBT integrity for portal traversal
     */
    @Inject(
        method = "asString",
        at = @At("RETURN"),
        cancellable = false
    )
    private void onNBTSerialization(CallbackInfoReturnable<String> info) {
        NbtCompound nbt = (NbtCompound) (Object) this;
        
        // Ensure critical Axiom keys are present if any other Axiom keys exist
        if (nbt.contains("axiomClipboard") || nbt.contains("axiomSelection")) {
            if (!nbt.contains("axiomDataVersion")) {
                nbt.putInt("axiomDataVersion", 1);
            }
        }
    }
}
