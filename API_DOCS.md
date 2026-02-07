# API Documentation - Axiom-Portals

## Overview

The Axiom-Portals mod provides a public event-based API for third-party mods to interact with portal traversal events and integrate custom functionality.

## Core Interfaces

### PortalEventListener

```java
public interface PortalEventListener {
    boolean onPrePortalTraverse(Entity entity);
    boolean onPostPortalTraverse(Entity entity);
    NbtCompound captureData(Entity entity);
    void restoreData(Entity entity, NbtCompound data);
}
```

#### Methods

**`onPrePortalTraverse(Entity entity)`**
- Called before an entity begins traversing a portal
- Return value: true if event handled successfully
- Use case: Prepare entity for dimensional transition

**`onPostPortalTraverse(Entity entity)`**
- Called after an entity completes portal traversal
- Return value: true if event handled successfully
- Use case: Verify and clean up after traversal

**`captureData(Entity entity)`**
- Called to capture entity-specific data before traversal
- Return value: NbtCompound with data to preserve (can be empty)
- Use case: Store custom mod data for restoration in new dimension

**`restoreData(Entity entity, NbtCompound data)`**
- Called to restore captured data after traversal
- Use case: Restore custom mod data to entity

### PortalEventRegistry

```java
public class PortalEventRegistry {
    public static void registerListener(PortalEventListener listener);
    public static void unregisterListener(PortalEventListener listener);
    public static List<PortalEventListener> getListeners();
    public static void clearListeners();
}
```

#### Methods

**`registerListener(PortalEventListener listener)`**
- Register a listener to receive portal events
- Safe to call at any time
- Listeners persist until manually unregistered

**`unregisterListener(PortalEventListener listener)`**
- Remove a previously registered listener
- Returns true if listener was found and removed

**`getListeners()`**
- Get list of all registered listeners
- Returns defensive copy to prevent external modification

**`clearListeners()`**
- Remove all registered listeners
- Use with caution!

## Helper Classes

### PortalIntegrationHelper

Detects and coordinates with Axiom and Immersive Portals installations.

```java
public static void initializeIntegrations();
public static boolean isAxiomIntegrated();
public static boolean isImmersivePortalsIntegrated();
public static boolean isFullyIntegrated();
public static NbtCompound extractAxiomData(Entity entity);
public static void applyAxiomData(Entity entity, NbtCompound data);
```

### AxiomPortalsConfig

Configuration options for the mod.

```java
public static boolean ENABLE_CLIPBOARD_PRESERVATION;
public static boolean ENABLE_SELECTION_PERSISTENCE;
public static boolean ENABLE_NBT_PRESERVATION;
public static boolean ENABLE_CHUNK_LOADING_FIX;
public static boolean ENABLE_RENDERING_COMPATIBILITY;
public static boolean DEBUG_MODE;
public static int MAX_TRAVERSAL_CACHE_SIZE;
```

All properties can be modified at runtime.

## Usage Examples

### Basic Event Listener

```java
import com.axiomportals.api.PortalEventListener;
import com.axiomportals.api.PortalEventRegistry;

public class MyPortalListener implements PortalEventListener {
    public MyPortalListener() {
        // Register on creation
        PortalEventRegistry.registerListener(this);
    }

    @Override
    public boolean onPrePortalTraverse(Entity entity) {
        // Called before entity enters portal
        System.out.println("Entity entering portal: " + entity);
        return true;
    }

    @Override
    public boolean onPostPortalTraverse(Entity entity) {
        // Called after entity exits portal
        System.out.println("Entity exited portal: " + entity);
        return true;
    }

    @Override
    public NbtCompound captureData(Entity entity) {
        // Capture data before dimensional change
        NbtCompound data = new NbtCompound();
        data.putString("my_mod_key", "my_mod_value");
        return data;
    }

    @Override
    public void restoreData(Entity entity, NbtCompound data) {
        // Restore data after dimensional change
        if (data.contains("my_mod_key")) {
            String value = data.getString("my_mod_key");
            // Do something with the value
        }
    }
}
```

### Player-Specific Logic

```java
import net.minecraft.entity.player.PlayerEntity;

@Override
public boolean onPostPortalTraverse(Entity entity) {
    if (entity instanceof PlayerEntity player) {
        // Player-specific logic
        player.sendMessage(Text.literal("Welcome to the other side!"), false);
    }
    return true;
}
```

### Conditional Registration

```java
import com.axiomportals.integration.PortalIntegrationHelper;

public void initializeMod() {
    // Only register if both mods are present
    if (PortalIntegrationHelper.isFullyIntegrated()) {
        PortalEventRegistry.registerListener(new MyListener());
    }
}
```

### Data Preservation

```java
@Override
public NbtCompound captureData(Entity entity) {
    NbtCompound data = new NbtCompound();
    
    // Preserve custom entity data
    if (entity instanceof CustomEntity custom) {
        data.putInt("custom_counter", custom.getCounter());
        data.put("custom_data", custom.serialize());
    }
    
    return data;
}

@Override
public void restoreData(Entity entity, NbtCompound data) {
    if (entity instanceof CustomEntity custom && data != null) {
        if (data.contains("custom_counter")) {
            custom.setCounter(data.getInt("custom_counter"));
        }
        if (data.contains("custom_data")) {
            custom.deserialize(data.getCompound("custom_data"));
        }
    }
}
```

## Integration Checklist

When integrating with Axiom-Portals:

- [ ] Add mod dependency to build.gradle
- [ ] Create listener class implementing PortalEventListener
- [ ] Register listener during mod initialization
- [ ] Test with both Fabric and Forge loaders
- [ ] Test with Axiom and Immersive Portals installed
- [ ] Test with mods installed separately (fallback behavior)
- [ ] Verify data persistence through portals
- [ ] Check for NBT data loss

## Error Handling

All listener callbacks are wrapped in try-catch blocks by the event registry. Errors are printed to System.err but won't crash the game.

Always validate NBT data before accessing:
```java
if (data != null && data.contains("my_key")) {
    value = data.get("my_key");
}
```

## Performance Considerations

- Event callbacks should be fast
- Avoid heavy computations in listeners
- Use thread-safe data structures if needed
- Clean up resources in appropriate listeners

## Best Practices

1. **Always null-check parameters**
   ```java
   if (entity != null && data != null) { ... }
   ```

2. **Handle missing mods gracefully**
   ```java
   if (PortalIntegrationHelper.isAxiomIntegrated()) {
       // Use Axiom-specific code
   }
   ```

3. **Keep listeners deterministic**
   - Same input should produce same output
   - Avoid random state changes

4. **Document custom NBT keys**
   - Use prefixes to avoid conflicts
   - Include version info for migration

## Support

- GitHub Issues: [Report bugs/request features](../../issues)
- Development Guide: See DEVELOPMENT.md
- Example Implementation: See ExamplePortalListener.java
