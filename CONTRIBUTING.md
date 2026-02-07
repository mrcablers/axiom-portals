# Contributing to Axiom-Portals

Thank you for your interest in contributing!

## Code Style

- Use 4 spaces for indentation
- Follow Java naming conventions
- Document all public methods with JavaDoc
- Keep methods focused and small

## Setting Up Development Environment

### Requirements

- Java 17 or higher
- Gradle 8.0+
- Git

### Clone and Setup

```bash
git clone https://github.com/mrcablers/axiom-portals.git
cd axiom-portals
./gradlew build
```

### Running Dev Environment

```bash
# Build mod JAR
./gradlew build

# Generate IDE run configurations
./gradlew genSources

# For Fabric dev environment
./gradlew runClient      # Run Minecraft with Fabric
./gradlew runServer      # Run dedicated server with Fabric
```

## Mixin Development

When adding new mixins:

1. Create mixin class in `src/main/java/com/axiomportals/mixin/`
2. Register in `src/main/resources/axiom_portals.mixins.json`
3. Use `@Inject` for non-invasive hooks
4. Document mixin purpose and target method

Example:
```java
@Mixin(TargetClass.class)
public class MixinExample {
    @Inject(method = "targetMethod", at = @At("HEAD"))
    private void onMethodCall(CallbackInfo info) {
        // Your code here
    }
}
```

## Testing

- Test with both Immersive Portals and Axiom installed
- Test dimension changes (Nether, End, custom)
- Test player selection persistence
- Test clipboard operations through portals

## Reporting Issues

When reporting bugs, include:

- Minecraft version
- Mod versions (IP, Axiom, Fabric/Forge versions)
- Reproduction steps
- Crash log or error message
- Screenshots if applicable

## Pull Request Process

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open Pull Request with description

## Architecture Notes

### Core Components

- **PortalCompatibilityManager**: Central event hub
- **PortalTraversalData**: NBT data handling
- **Mixins**: Bytecode injection points
- **Utilities**: Helper classes (NBTHelper, ModCompat)

### Data Flow

```
Portal Encounter
    ↓
onPrePortalTraverse (capture data)
    ↓
Entity Dimension Change (vanilla)
    ↓
Immersive Portals Handling
    ↓
onPostPortalTraverse (restore data)
    ↓
Axiom Data Restored
```

## Common Tasks

### Adding a new NBT tag to preserve

1. Add tag name to constants in `PortalTraversalData`
2. Update capture logic in `captureAxiomData()`
3. Update restore logic in `restoreAxiomData()`
4. Add to `NBTHelper.copyAxiomTags()`

### Adding a new mixin target

1. Create mixin class in appropriate package
2. Add to `axiom_portals.mixins.json`
3. Write `@Inject` hooks with targets
4. Test thoroughly with debug mode

### Adding Forge-specific compatibility

1. Create handler in `com.axiomportals.forge` package
2. Use `@SubscribeEvent` and `@Mod.EventBusSubscriber`
3. Delegate to `PortalCompatibilityManager` methods
4. Mirror any new logic from Fabric side

## Questions?

Open an issue or contact the maintainers!
