# Testing Guide - Axiom-Portals Compatibility

## Overview

This guide covers testing procedures to ensure Axiom-Portals works correctly.

## Test Environment Setup

### Minecraft Installation

```
.minecraft/
├── versions/
│   └── 1.20.1/
├── mods/
│   ├── axiom-[version]-fabric-1.20.1.jar
│   ├── immersive-portals-[version]+1.20.1-fabric.jar
│   └── axiom-portals-1.0.0.jar
├── config/
│   └── axiom_portals.properties
└── logs/
    └── latest.log
```

## Test Cases

### Test 1: Basic Portal Crossing

**Objective**: Verify entity data persists through simple portal crossing

**Steps**:
1. Create a Minecraft world
2. Place an Immersive Portal
3. Use Axiom to create a 10x10x10 selection
4. Walk through the portal
5. Check selection is still active

**Expected Result**: ✅ Selection remains visible and functional

**Failure Indicators**:
- ❌ Selection disappears
- ❌ Crash on portal crossing
- ❌ Game lag/freeze

### Test 2: Multiple Portal Crossings

**Objective**: Verify data persistence across multiple traversals

**Steps**:
1. Create selection with Axiom
2. Walk through portal A → cross to dimension B
3. Create second selection in dimension B
4. Walk back through portal → return to dimension A
5. Verify original selection still exists
6. Walk through portal again to B
7. Verify second selection still exists

**Expected Result**: ✅ Both selections intact and properly switched

### Test 3: Clipboard Persistence

**Objective**: Verify Axiom clipboard survives dimension changes

**Steps**:
1. Copy a structure with Axiom (15x15x15)
2. Verify clipboard shows in interface
3. Cross through Immersive Portal
4. Attempt to paste clipboard in new dimension
5. Verify paste works identically

**Expected Result**: ✅ Clipboard remains unchanged and functional

### Test 4: Large Selection

**Objective**: Test performance with large selections

**Steps**:
1. Create 500x500 selection (very large)
2. Cross through portal
3. Crossing should complete within 5 seconds
4. Selection should be fully intact

**Expected Result**: ✅ No lag, selection preserved, no data loss

### Test 5: NBT Data Integrity

**Objective**: Verify no NBC data corruption

**Steps**:
1. Export world before portal crossing
2. Cross through portal with active clipboard
3. Save and reload world
4. Verify clipboard data still exists

**Expected Result**: ✅ All NBT data intact on world reload

### Test 6: Rendering Compatibility

**Objective**: Check for visual artifacts

**Steps**:
1. Enable Axiom visualizations (selection boxes)
2. Position camera to see portal and selection
3. Cross through portal
4. Look for:
   - Rendering glitches
   - Missing textures
   - Z-fighting artifacts
   - Incorrect layer ordering

**Expected Result**: ✅ All rendering correct, no artifacts

### Test 7: Mod Absence Handling

**Objective**: Verify graceful handling when mods are missing

**Steps**:
1. Remove Axiom from mods folder
2. Start game with just Immersive Portals and Axiom-Portals
3. Cross portal - should work normally
4. Add Axiom back
5. Cross portal - data preservation should work

**Expected Result**: ✅ No crashes, appropriate fallback behavior

### Test 8: Server/Client Sync

**Objective**: Verify multiplayer functionality

**Steps**:
1. Create test server with all three mods
2. Join as client
3. Player 1: Create selection, cross portal
4. Player 2: Observe Player 1's clipboard
5. Verify no sync issues

**Expected Result**: ✅ Proper client-server synchronization

### Test 9: Performance Baseline

**Objective**: Measure performance impact

**Procedure**:
```
1. Install mods
2. Create world, enter dimension
3. Measure FPS (baseline)
4. Cross through portal multiple times
5. Measure FPS again
6. Record FPS difference
```

**Expected Result**: ✅ <5 FPS difference, no stuttering

### Test 10: Error Recovery

**Objective**: Test crash recovery

**Steps**:
1. Create selection and start portal crossing
2. Force quit while crossing (Alt+F4)
3. Restart
4. Load world
5. Check data integrity

**Expected Result**: ✅ World loads cleanly, no corruption

## Manual Testing Checklist

- [ ] Single portal crossing works
- [ ] Multiple crossings preserve data
- [ ] Clipboard survives journey
- [ ] Large selections work
- [ ] No visual glitches
- [ ] Graceful degradation without mods
- [ ] Server works in multiplayer
- [ ] Performance acceptable
- [ ] Recovery from crashes
- [ ] Debug logging works when enabled

## Automated Test Framework (Future)

```java
@Test
public void testPortalTraversal() {
    Entity entity = createTestEntity();
    NbtCompound before = captureNBT(entity);
    
    simulatePortalTraversal(entity);
    
    NbtCompound after = captureNBT(entity);
    assertEquals(before, after);
}
```

## Performance Metrics

Record these for each test:

```
Baseline FPS: ___
Portal Crossing FPS: ___
Post-Crossing FPS: ___
Average Traversal Time: ___ ms
Memory Usage (Before): ___ MB
Memory Usage (During): ___ MB
Memory Usage (After): ___ MB
```

## Bug Reporting

If a test fails:

1. Enable debug logging
2. Reproduce issue cleanly
3. Collect logs from `.minecraft/logs/latest.log`
4. Note exact reproduction steps
5. Report on GitHub with:
   - Test case number
   - Steps to reproduce
   - Expected vs actual behavior
   - Logs attached
   - System specs

## Compatibility Matrix

Test against:

| Axiom Version | IP Version | Fabric | Forge | Status |
|---|---|---|---|---|
| 2.0.0 | 1.0.0 | ✅ | ✅ | Tested |
| 2.0.0 | 1.1.0 | ✅ | ✅ | Tested |
| 2.1.0 | 1.0.0 | ✅ | ✅ | Tested |

---

Last Updated: February 2026
