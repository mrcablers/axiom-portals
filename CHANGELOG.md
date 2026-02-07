# Changelog - Axiom-Portals Compatibility Mod

All notable changes to this project will be documented in this file.

## Versioning

This project follows [Semantic Versioning](https://semver.org/):
- MAJOR.MINOR.PATCH (e.g., 1.0.0)

## [1.0.0] - 2026-02-07

### Initial Release 🎉

#### Added
- Core portal traversal event system
- Axiom clipboard persistence across dimensions
- Axiom selection preservation through portals
- NBT data integrity preservation
- Entity data caching for concurrent access
- Configuration system for feature toggles
- Public API for third-party mod integration
- Event listener registration system
- Integration helpers for Axiom and Immersive Portals detection
- Fabric Loom multi-loader support (Fabric + Forge)
- Comprehensive mixin system:
  - Entity teleportation handling
  - ServerPlayer dimension changes
  - ServerWorld chunk management
  - Client-side rendering fixes
  - NBT safety utilities
- Thread-safe data structures:
  - Synchronized traversal cache
  - Concurrent entity tracking
  - WeakHashMap for automatic cleanup
- Debug logging and performance metrics
- Complete documentation:
  - API documentation
  - Development guide
  - Testing procedures
  - Configuration guide
  - Usage examples
- GitHub issue/PR templates
- Quick start guide for developers

#### Architecture
- **PortalCompatibilityManager**: Central event coordination
- **PortalTraversalData**: NBT data handling
- **PortalEventRegistry**: Public API for third-party integration
- **PortalIntegrationHelper**: Mod detection and compatibility
- **ClientPortalManager**: Client-side synchronization
- **Multiple Mixins**: Non-invasive integration with existing code

#### Compatibility
- ✅ Minecraft 1.20.1
- ✅ Fabric Loader 0.14.25+
- ✅ Forge 1.20.1-47.2.0+
- ✅ Immersive Portals (any 1.20.1 version)
- ✅ Axiom (any 1.20.1 version)

#### Bug Fixes
- N/A (initial release)

#### Performance
- Optimized event firing with exception handling
- Efficient NBT data copying
- Weak reference cleanup preventing memory leaks
- Configurable cache size limits

#### Known Limitations
- Configuration file parsing not yet implemented (defaults used)
- Forge event handler template requires manual integration
- Limited to 1.20.1 (version-specific mixins)

### Future Versions

#### Planned for 1.1.0
- [ ] Config file parsing and persistence
- [ ] More granular event hooks
- [ ] Performance monitoring/statistics
- [ ] Additional mod integrations
- [ ] World guard/protection compatibility
- [ ] Plot/claim plugin compatibility

#### Planned for 1.2.0
- [ ] Async clipboard transfers
- [ ] Selection compression for network efficiency
- [ ] Undo/redo stack preservation
- [ ] Multi-dimension selection groups

#### Possible 2.0.0+
- [ ] Support for additional Minecraft versions
- [ ] Plugin system for custom handlers
- [ ] GUI for configuration
- [ ] Modpack-specific optimizations

---

## Update Instructions

### From 1.0.0 → 1.1.0 (when released)
1. Replace mod JAR file
2. Restart Minecraft
3. No world conversion needed

### Backwards Compatibility
All versions maintain NBT compatibility - no data loss on update.

---

## Reporting Issues

Found a bug? Have a feature request?

1. Check [GitHub Issues](../../issues) - might already be reported
2. Enable debug logging in config
3. Reproduce consistently
4. Create issue with:
   - Steps to reproduce
   - Expected vs actual behavior
   - Logs from `.minecraft/logs/latest.log`
   - System specs (OS, Java version, etc.)

---

## Contributing

See [DEVELOPMENT.md](DEVELOPMENT.md) for contribution guidelines.

---

**Note**: This mod is in active development. Features, APIs, and NBT formats may change between versions. Always check release notes before updating.
