# Axiom-Portals Mod API Documentation

## Overview

The Axiom-Portals compatibility mod provides seamless integration between **Immersive Portals** and **Axiom** mods. This document covers the public API for developers who want to extend or integrate with this mod.

## Core Classes

### PortalCompatibilityManager

The main manager class for all portal compatibility operations.

```java
// Initialize the mod
PortalCompatibilityManager.init();

// Handle entity portal traversal
PortalCompatibilityManager.onPrePortalTraverse(entity);
PortalCompatibilityManager.onPostPortalTraverse(entity);

// Handle dimension changes
PortalCompatibilityManager.onDimensionChange(entity, oldWorld, newWorld);

// Check entity status
boolean inTransit = PortalCompatibilityManager.isEntityInTransit(entity);

// Get cached data
NbtCompound data = PortalCompatibilityManager.getTraversalData(entity);

// Cleanup
PortalCompatibilityManager.clearAllCaches();
```

### PortalTraversalData

Handles NBT data capture and restoration for entities.

```java
// Capture Axiom data before portal traversal
NbtCompound data = PortalTraversalData.captureAxiomData(entity);

// Restore data after portal traversal
PortalTraversalData.restoreAxiomData(entity, data);

// Preserve data across dimensions
PortalTraversalData.preserveDataAcrossDimensions(entity, fromWorld, toWorld);

// Clear traversal markers
PortalTraversalData.clearTraversalMarkers(entity);
```

### ClientPortalManager (Client-side)

Manages client-side rendering and UI compatibility.

```java
// Initialize client
ClientPortalManager.init();

// Portal entry/exit events
ClientPortalManager.onPortalEnter();
ClientPortalManager.onPortalExit();

// Update rendering
ClientPortalManager.updateSelectionRendering();
```

## Utility Classes

### NBTHelper

Safe NBT data handling utilities.

```java
// Safely merge NBT
NBTHelper.mergeNBT(source, target);

// Copy Axiom tags
NBTHelper.copyAxiomTags(source, target);

// Validate NBT
boolean intact = NBTHelper.isNBTIntact(entity);

// Clear portal marks
NBTHelper.clearPortalMarks(entity);
```

### ModCompat

Mod detection and compatibility utilities.

```java
// Check if mods are loaded
boolean hasIP = ModCompat.isImmersivePortalsLoaded();
boolean hasAxiom = ModCompat.isAxiomLoaded();

// Generic mod detection
boolean loaded = ModCompat.isModLoaded("modid");

// Check if patch needed
boolean needsPatch = ModCompat.needsCompatibilityPatch();
```

## Mixin Points

The mod uses Mixins to hook into key methods:

### Entity Mixins
- `Entity#changeDimension` - Capture/restore data during dimension changes
- `Entity#tick` - Prevent unloading during portal traversal

### Player Mixins
- `ServerPlayerEntity#teleport` - Handle player teleportation
- `ClientPlayerEntity#tick` - Sync client state

### World Mixins
- `ServerWorld#removeEntity` - Prevent early entity removal
- `ServerWorld#addEntity` - Handle entity arrival after portal

### Client Mixins
- `GameRenderer#render` - Coordinate rendering layers
- `GameRenderer#renderWorld` - Portal-aware rendering order

## Axiom NBT Tags

The mod recognizes and preserves these Axiom NBT tags:

- `axiomClipboard` - Player's copied structure data
- `axiomSelection` - Current selection area
- `axiomUndoStack` - Undo history
- `axiomRedoStack` - Redo history
- `axiomBuffer` - Temporary data buffer
- `axiomData` - Generic Axiom data

## Portal Traversal Lifecycle

1. **Pre-Traversal**
   - `PortalCompatibilityManager.onPrePortalTraverse()` called
   - Entity NBT data is captured
   - Entity marked as "in transit"

2. **During Portal**
   - Immersive Portals handles dimension change
   - Entity chunks stay loaded
   - Player can't unload or lose data

3. **Post-Traversal**
   - Entity arrives in new dimension
   - `PortalCompatibilityManager.onPostPortalTraverse()` called
   - Axiom data is restored
   - Transit markers cleared

## Event Integration (Forge)

For Forge implementations, use these events:

```java
@SubscribeEvent
public static void onEntityTeleport(EntityTeleportEvent event) {
    PortalCompatibilityManager.onPrePortalTraverse(event.getEntity());
}

@SubscribeEvent
public static void onDimensionChange(ChangeDimensionEvent event) {
    PortalCompatibilityManager.onDimensionChange(event.getEntity(), 
                                                  event.getFromWorld(), 
                                                  event.getToWorld());
}

@SubscribeEvent
public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
    PortalCompatibilityManager.onPostPortalTraverse(event.getEntity());
}
```

## Known Limitations

- Axiom selections don't render across portal boundaries (by design)
- Clipboard data persists but may need resync when using very large structures
- Entity velocity is reset on portal traversal to prevent glitching

## Version Compatibility

- **Minecraft**: 1.20.1
- **Immersive Portals**: 1.0.0+
- **Axiom**: 2.0.0+
- **Fabric Loader**: 0.14.0+
- **Forge**: 47.2.0+

## License

MIT License - See LICENSE file
