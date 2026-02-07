# Development Guide - Axiom-Portals Compatibility Mod

## Building the Project

### Requirements
- JDK 17+
- Gradle 7.0+
- Git

### Setup

```bash
git clone https://github.com/mrcablers/axiom-portals.git
cd axiom-portals
./gradlew build
```

The compiled JAR will be in `build/libs/`

## Project Structure

```
axiom-portals/
├── src/main/
│   ├── java/com/axiomportals/
│   │   ├── AxiomPortalsMain.java
│   │   ├── PortalCompatibilityManager.java
│   │   ├── PortalTraversalData.java
│   │   ├── api/
│   │   │   ├── PortalEventListener.java
│   │   │   └── PortalEventRegistry.java
│   │   ├── client/
│   │   │   ├── AxiomPortalsClient.java
│   │   │   └── ClientPortalManager.java
│   │   ├── config/
│   │   │   └── AxiomPortalsConfig.java
│   │   ├── integration/
│   │   │   └── PortalIntegrationHelper.java
│   │   ├── mixin/
│   │   │   ├── entity/
│   │   │   ├── world/
│   │   │   ├── util/
│   │   │   └── client/
│   │   ├── util/
│   │   │   ├── NBTHelper.java
│   │   │   └── ModCompat.java
│   │   └── forge/
│   │       └── ForgePortalEventHandler.java
│   └── resources/
│       ├── fabric.mod.json
│       └── axiom_portals.mixins.json
├── gradle.properties
├── build.gradle
└── settings.gradle
```

## Core Components

### PortalCompatibilityManager
Central manager for all portal event handling. Uses thread-safe collections for concurrent access.

**Key Methods:**
- `init()` - Initialize the mod
- `onPrePortalTraverse(Entity)` - Called before portal crossing
- `onPostPortalTraverse(Entity)` - Called after portal crossing
- `isEntityInTransit(Entity)` - Check if entity is currently traversing
- `getTraversalData(Entity)` - Get cached data for an entity

### PortalTraversalData
Handles NBT data capture and restoration for entities.

**Key Methods:**
- `captureAxiomData(Entity)` - Extract Axiom data before traversal
- `restoreAxiomData(Entity, NbtCompound)` - Restore Axiom data after traversal
- `preserveDataAcrossDimensions()` - Mark data as needing preservation

### PortalEventRegistry
Public API for plugin developers to listen to portal events.

**Usage:**
```java
PortalEventRegistry.registerListener(new MyListener());
```

## Mixin System

Mixins are used to inject code into existing classes without modifying them:

### Entity Mixins (`MixinEntity`)
- Hooks `changeDimension()` to capture/restore data
- Hooks `tick()` to prevent entity unloading during transit

### ServerPlayer Mixins (`MixinServerPlayer`)
- Handles player-specific portal crossing logic
- Syncs clipboard and selection data

### World Mixins (`MixinServerWorld`)
- Prevents premature entity removal during transit
- Manages chunk loading for portal-crossing entities

### Client Mixins
- `MixinGameRenderer` - Fixes rendering conflicts
- `MixinClientPlayerEntity` - Syncs client state during traversal

## Adding New Functionality

### Example: Adding a Custom Portal Event

1. Add to `PortalEventListener` interface:
```java
boolean onPortalOpen(Portal portal);
```

2. Implement in event registry:
```java
public static void firePortalOpen(Portal portal) {
    for (PortalEventListener listener : LISTENERS) {
        listener.onPortalOpen(portal);
    }
}
```

3. Call from mixin:
```java
PortalEventRegistry.firePortalOpen(portal);
```

## Testing

### Manual Testing
1. Build mod with `./gradlew build`
2. Place in Minecraft mods folder
3. Start with both Axiom and Immersive Portals
4. Test:
   - Make a selection with Axiom
   - Walk through an Immersive Portal
   - Verify selection is preserved

### Debug Logging
Enable in config:
```properties
debug_mode=true
log_portal_traversal=true
```

Then check logs for detailed traversal information.

## Dual Loader Support

The project uses Fabric Loom which allows compiling for both Fabric and Forge.

### Fabric
Uses standard Fabric entry points and mixins.

### Forge
Requires event handler registration in ForgePortalEventHandler.java

## Contributing Guidelines

### Code Style
- Use 4-space indentation
- Follow Java naming conventions
- Add Javadoc to public methods
- Keep methods focused and small

### Testing Before PR
1. Build successfully: `./gradlew build`
2. Test on both Fabric and Forge
3. Test with both mods present and absent
4. Enable debug logging to verify behavior

### NBT Compatibility
When modifying NBT code:
1. Always use safe accessor methods (`.contains()` before `.get()`)
2. Never assume tag structure from other mods
3. Update `axiomDataVersion` tag if format changes
4. Test with actual Axiom mod to verify data integrity

## Troubleshooting Development

### Build Fails
```bash
./gradlew clean build --refresh-dependencies
```

### Mixins Not Working
- Ensure `axiom_portals.mixins.json` is in resources
- Check mixin class names match exactly
- Verify `@Mixin` annotation targets correct class

### Import Issues
- Rebuild with `./gradlew clean build`
- Refresh IDE project

## Performance Considerations

### Caching Strategy
Uses `WeakHashMap` for automatic cleanup when entities are GC'd

### Thread Safety
- All caches are synchronized collections
- Event firing is thread-safe
- NBT operations are atomic

### Optimization
- Only capture data when needed
- Clear caches on server shutdown
- Use efficient NBT tag names

## Future Enhancements

Potential improvements:
- Config file parsing (currently using defaults)
- Advanced event system with priorities
- Performance monitoring
- Integration with more portal mods
- Custom rendering layers for selections

## Release Process

1. Update version in `gradle.properties`
2. Create release notes
3. Build with `./gradlew build`
4. Upload JAR to releases page
5. Tag commit in Git

---

For questions or issues, open a GitHub issue or discussion.
