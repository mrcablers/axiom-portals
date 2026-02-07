# Axiom-Portals Compatibility Mod

A Minecraft 1.20.1 mod that enables seamless compatibility between **Immersive Portals** and **Axiom**, allowing builders to use world editing/copying across dimensional portals without data loss.

## Features

✨ **Core Features:**
- ✅ Persistent clipboard and selection data through portals
- ✅ Entity NBT data preservation during dimension changes
- ✅ Automatic chunk loading prevention for portal-traversing entities
- ✅ Rendering layer coordination to prevent visual artifacts
- ✅ Thread-safe data caching for concurrent access
- ✅ Graceful fallback if either mod is missing

## Compatibility

- **Minecraft Version**: 1.20.1 only
- **Loaders**: Fabric & Forge (dual compatibility)
- **Immersive Portals**: Any 1.20.1 version
- **Axiom**: Any 1.20.1 version

## Installation

### Fabric
1. Install Fabric Loader for 1.20.1
2. Download the mod JAR from [Releases](../../releases)
3. Place in `mods/` folder

### Forge
1. Install Forge for 1.20.1
2. Download the mod JAR from [Releases](../../releases)
3. Place in `mods/` folder

## API Usage

Third-party mods can integrate with Axiom-Portals events:

```java
import com.axiomportals.api.PortalEventListener;
import com.axiomportals.api.PortalEventRegistry;

public class MyPortalListener implements PortalEventListener {
    public MyPortalListener() {
        PortalEventRegistry.registerListener(this);
    }

    @Override
    public boolean onPrePortalTraverse(Entity entity) {
        System.out.println("Entity about to traverse: " + entity.getName().getString());
        return true;
    }

    @Override
    public boolean onPostPortalTraverse(Entity entity) {
        System.out.println("Entity traversed portal!");
        return true;
    }

    @Override
    public NbtCompound captureData(Entity entity) {
        NbtCompound data = new NbtCompound();
        // Store custom data here
        return data;
    }

    @Override
    public void restoreData(Entity entity, NbtCompound data) {
        // Restore custom data here
    }
}
```

## Configuration

Features can be toggled in `config/axiom_portals.properties`:

```properties
# Feature toggles
enable_clipboard_preservation=true
enable_selection_persistence=true
enable_nbt_preservation=true
enable_chunk_loading_fix=true
enable_rendering_compatibility=true

# Logging
debug_mode=false
log_portal_traversal=false
log_data_preservation=false

# Performance
max_traversal_cache_size=1000
traversal_timeout_ticks=600
enable_async_data_sync=true
```

## How It Works

1. **Pre-Traversal**: When an entity approaches a portal, Axiom clipboard and selection data is captured
2. **Traversal**: Entity passes through the portal to another dimension
3. **Post-Traversal**: Axiom data is restored to the entity's NBT in the new dimension
4. **Cleanup**: Temporary portal markers are removed from entity NBT

### NBT Tags Used

The mod uses these custom NBT tags:
- `axiomClipboard` - Axiom's clipboard data
- `axiomSelection` - Current selection bounds
- `axiomUndoStack` - Undo history
- `axiomPortal_traversing` - Internal: marks entity in transit
- `axiomPortal_source_world` - Internal: tracks source dimension

## Troubleshooting

### Selections disappear after portal
- Ensure both mods are installed
- Check that mod version matches 1.20.1
- Enable `log_data_preservation=true` to debug

### Rendering issues with portals
- Try disabling `enable_rendering_compatibility=true`
- Check for mod conflicts with other rendering mods

### Performance lag when using portals
- Reduce `max_traversal_cache_size` in config
- Disable `enable_async_data_sync` if issues persist

## Development

### Building from Source

```bash
./gradlew build
```

### Debug Logging

Enable debug mode in config:
```properties
debug_mode=true
log_portal_traversal=true
log_data_preservation=true
```

### Project Structure

```
src/main/java/com/axiomportals/
├── AxiomPortalsMain.java          # Main entry point
├── PortalCompatibilityManager.java # Core logic
├── PortalTraversalData.java        # Data handling
├── api/
│   ├── PortalEventListener.java    # Event interface
│   └── PortalEventRegistry.java    # Event system
├── client/
│   ├── AxiomPortalsClient.java     # Client side
│   └── ClientPortalManager.java    # Client logic
├── config/
│   └── AxiomPortalsConfig.java     # Configuration
├── integration/
│   └── PortalIntegrationHelper.java # Mod integration
├── mixin/
│   ├── entity/                      # Entity mixins
│   ├── world/                       # World mixins
│   ├── util/                        # Utility mixins
│   └── client/                      # Client mixins
├── util/
│   ├── NBTHelper.java               # NBT utilities
│   └── ModCompat.java               # Mod detection
└── forge/
    └── ForgePortalEventHandler.java # Forge support
```

## Contributing

Contributions welcome! Please ensure:
1. Code follows existing style
2. All changes tested on both Fabric and Forge
3. Documentation is updated
4. NBT compatibility is maintained

## License

MIT License - See LICENSE file

## Credits

- Immersive Portals: qouteall
- Axiom: AxiomGames
- This mod: Community effort for compatibility
