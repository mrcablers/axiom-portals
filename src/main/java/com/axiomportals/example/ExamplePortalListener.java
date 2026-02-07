package com.axiomportals.example;

import com.axiomportals.api.PortalEventListener;
import com.axiomportals.api.PortalEventRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;

/**
 * Example implementation of PortalEventListener for third-party mod developers.
 * 
 * This shows how to hook into Axiom-Portals events and respond to portal traversals.
 * Copy and modify this class in your own mod to integrate with the event system.
 */
public class ExamplePortalListener implements PortalEventListener {

    /**
     * Register this listener with the event system
     * Call this during your mod's initialization
     */
    public static void register() {
        PortalEventRegistry.registerListener(new ExamplePortalListener());
        System.out.println("[Example Mod] Portal event listener registered!");
    }

    @Override
    public boolean onPrePortalTraverse(Entity entity) {
        System.out.println("[Example Mod] Entity '" + entity.getName().getString() 
            + "' is about to traverse a portal at " 
            + entity.getBlockPos());
        
        // Return true to allow the traversal
        return true;
    }

    @Override
    public boolean onPostPortalTraverse(Entity entity) {
        System.out.println("[Example Mod] Entity '" + entity.getName().getString() 
            + "' successfully traversed portal and is now at " 
            + entity.getBlockPos());
        
        // Return true to indicate successful handling
        return true;
    }

    @Override
    public NbtCompound captureData(Entity entity) {
        NbtCompound data = new NbtCompound();
        
        // Example: Store custom data before portal traversal
        data.putString("example_mod_data", "This data persists through portals!");
        data.putLong("portal_traverse_time", entity.world.getTime());
        
        // Note: You can also access Axiom data via PortalIntegrationHelper
        // if you need to preserve it alongside your own data
        
        return data;
    }

    @Override
    public void restoreData(Entity entity, NbtCompound data) {
        if (data == null) return;
        
        // Restore custom data after portal traversal
        if (data.contains("example_mod_data")) {
            String customData = data.getString("example_mod_data");
            System.out.println("[Example Mod] Restored data: " + customData);
        }
        
        if (data.contains("portal_traverse_time")) {
            long traverseTime = data.getLong("portal_traverse_time");
            System.out.println("[Example Mod] Portal traversal time: " + traverseTime);
        }
    }
}
