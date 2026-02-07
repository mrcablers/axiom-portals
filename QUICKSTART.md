# Axiom-Portals Quick Start Guide

## Installation

### Option 1: Fabric

1. **Install Fabric Loader**
   - Go to [fabricmc.net](https://fabricmc.net)
   - Download installer for 1.20.1
   - Run installer

2. **Install Required Mods** (in `%appdata%/.minecraft/mods` or `~/.minecraft/mods`)
   - Axiom mod JAR
   - Immersive Portals mod JAR
   - axiom-portals JAR (this mod)
   - Fabric API JAR

3. **Launch Minecraft** with Fabric profile

### Option 2: Forge

1. **Install Forge**
   - Go to [minecraftforge.net](https://minecraftforge.net)
   - Download installer for 1.20.1
   - Run installer

2. **Install Required Mods** (in `%appdata%/.minecraft/mods` or `~/.minecraft/mods`)
   - Same mods as Fabric option

3. **Launch Minecraft** with Forge profile

### Option 3: Hybrid (Fabric + Forge)

If you have a mod combiner (e.g., Patchwork):

1. Install Fabric loader
2. Install Patchwork mod
3. Place Forge mods in `mods/` alongside Fabric mods
4. Launch with Fabric

## Verification

After installation, verify the mod is working:

1. Create a new world with both Axiom and Immersive Portals mods
2. Enable cheats: `/say hello` should work
3. You should see Axiom buttons and UI elements
4. You should be able to use Immersive Portals' `/ipconfig` command

## Features Overview

### 1. Persistent Clipboard Through Portals

**Before This Mod:**
- Copying a structure with Axiom, then going through a portal = lost clipboard data

**With This Mod:**
- Your Axiom clipboard persists through portal traversal
- Your selection area is maintained
- No data loss when entering/exiting portals

### 2. Seamless Dimension Changes

```
Overworld → (use portal) → Nether
  ✓ Clipboard preserved
  ✓ Selection maintained
  ✓ Undo/Redo stack safe
```

### 3. Builder-Friendly Experience

- Copy structure in Overworld
- Push through portal to Nether
- Paste structure without any issues
- All your editing tools work perfectly

## Common Use Cases

### Copying structures across dimensions

1. Select area with Axiom (`/` then select region)
2. Copy structure (Axiom shortcut)
3. Use Immersive Portal to travel to another dimension
4. Paste structure (Axiom shortcut)

### Moving builds between worlds

1. Copy your Overworld build
2. Create Immersive Portal to custom dimension
3. Paste build in new dimension
4. All data preserved perfectly

### Team building across dimensions

- Players can collaborate using Axiom across portals
- One builder copies, another pastes
- Works smoothly without data corruption

## Troubleshooting

### Clipboard data lost after portal

**Solution:**
- Ensure axiom-portals mod is installed
- Check mod loading order in launcher
- Verify both Axiom and Immersive Portals are installed
- Check latest.log for errors

### Crashes through portals

**Solutions:**
- Update all mods to latest 1.20.1 versions
- Ensure Java 17+ is installed
- Remove conflicting mods
- Try in fresh world

### UI glitches (rendering issues)

**Solutions:**
- Update GPU drivers
- Allocate more RAM to Minecraft (2GB minimum)
- Disable optifine if installed
- Check graphics settings

### Selection disappears

**This is normal!** Axiom selections intentionally don't render across portal boundaries. They will reappear once you load normally.

## Performance Tips

- Larger Axiom selections may take time through portals
- If experiencing lag, reduce render distance temporarily
- Close unnecessary mods
- Allocate 3-4GB RAM to Minecraft

## Updating the Mod

1. Download latest axiom-portals JAR
2. Replace old JAR in mods folder
3. Backup your worlds first
4. Restart Minecraft

## Support

If you encounter issues:

1. Check [API.md](API.md) for technical details
2. Review [Troubleshooting](#troubleshooting) section
3. Check Minecraft logs: `logs/latest.log`
4. Open issue on GitHub with:
   - Version numbers (Minecraft, Axiom, IP, Fabric/Forge)
   - Steps to reproduce
   - Crash log if applicable
   - Java version

## Frequently Asked Questions

**Q: Do I need this mod if I don't use portals?**
A: No, this mod only affects Immersive Portals compatibility.

**Q: Can I use this with other building mods?**
A: Yes! This mod is compatible with other building tools.

**Q: Does it work on servers?**
A: Yes, but all players need the mod installed.

**Q: Will it affect performance?**
A: Minimal impact. Data preservation happens only during portal traversal.

**Q: Can I customize the behavior?**
A: Currently not configurable, but you can modify source code.

## Version History

### v1.0.0 (Current)
- Initial release
- Core compatibility between Axiom and Immersive Portals
- Full NBT data preservation
- Client/server sync
- Fabric and Forge support

## License

MIT License - This mod is free and open source.

## Credits

- Axiom Team for their amazing building mod
- Immersive Portals Team for dimensional travel mechanics
- Minecraft community for feedback

---

Happy building across dimensions! 🌍✨
