# Get Your Compiled JAR - Three Options

## Option 1: Docker Build (Recommended ⭐)

**Best for:** Most users, works reliably, no local setup needed

### Prerequisites
- Docker installed (free, available for all OS)

### One Command:
```bash
./build-docker.sh
```

### What happens:
1. Docker downloads Gradle 8.5 + Java 17 (auto)
2. Builds your mod in isolated container (2-5 min)
3. Puts compiled JAR in `build/libs/axiom-portals-1.0.0.jar`

**Done!** No version conflicts, no manual configuration.

---

## Option 2: GitHub Actions CI/CD

**Best for:** Hands-off, automatic building

### Steps:
1. Push this folder to any GitHub repository
2. Create file `.github/workflows/build.yml`:

```yaml
name: Build Axiom-Portals

on: [push, pull_request]

jobs:
  build:
    runs-on: ubuntu-latest
    
    steps:
      - uses: actions/checkout@v3
      
      - name: Set up Java
        uses: actions/setup-java@v3
        with:
          java-version: '17'
      
      - name: Build with Gradle
        run: |
          chmod +x gradlew
          ./gradlew clean build -x test
      
      - name: Upload JAR
        uses: actions/upload-artifact@v3
        with:
          name: axiom-portals-jar
          path: build/libs/axiom-portals-1.0.0.jar
```

3. Push the workflow file
4. After push, go to "Actions" tab
5. Download JAR from "Artifacts" section

**Bonus:** Rebuilds automatically on every code change!

---

## Option 3: Manual Local Build

**Best for:** Developers who want full control

### Requirements
- Gradle 8.5 (or compatible version)
- Java 17 or 21
- Git

### Steps:

**Step 1: Install Gradle 8.5**

macOS:
```bash
brew tap gradle/gradle
brew install gradle@8.5
```

Linux (Ubuntu):
```bash
sudo apt-get update
sudo apt-get install gradle=8.5-*
```

Windows:
- Download from https://gradle.org/releases/
- Add to PATH

**Step 2: Verify installation**
```bash
gradle --version
java -version
```

**Step 3: Build**
```bash
cd /workspaces/axiom-portals
./gradlew clean build -x test
```

**Step 4: Find JAR**
```bash
ls -lh build/libs/axiom-portals-1.0.0.jar
```

### Troubleshooting:

If you get version errors:
```bash
# Check Java version
java -version

# Should be 17 or higher
# If not, set JAVA_HOME:
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
./gradlew clean build -x test
```

---

## Option 4: Use Pre-built Mod Download

**Best for:** Just wanting to use it now**

If you just want the compiled mod without building:

1. Go to CurseForge or Modrinth
2. Search: "Axiom Portals Integration"
3. Download JAR file directly
4. Drop into `.minecraft/mods/` folder

(Note: Pre-built versions may not be available yet.)

---

## After Getting JAR

### Install These Mods Too:
- Fabric Loader 0.14.25+ (loader)
- Fabric API (base library)
- Axiom (structural editing)
- Immersive Portals (portal mechanics)

### Copy JAR:
```bash
# On macOS/Linux:
cp build/libs/axiom-portals-1.0.0.jar ~/.minecraft/mods/

# On Windows:
copy build\libs\axiom-portals-1.0.0.jar %APPDATA%\.minecraft\mods\
```

### Launch Minecraft:
1. Select "Fabric" as loader
2. Click "Play"
3. Mod loads automatically

### In-Game Use:
- Place Immersive Portal blocks
- Select with Axiom tools
- Data automatically syncs

---

## Comparison

| Feature | Docker | GitHub Actions | Local Gradle |
|---------|--------|-----------------|--------------|
| Setup time | 2 min | 5 min | 10-30 min |
| Speed | 2-5 min | Auto | Depends |
| Reliability | Very high | 100% | Depends on versions |
| Requires Docker | Yes | No | No |
| Full control | No | No | Yes |
| Best for | Most users | CI/CD | Developers |

---

## Help!

### Docker won't start
- Make sure Docker daemon is running
- macOS: Open Docker.app
- Linux: `sudo systemctl start docker`
- Windows: Start Docker Desktop

### Gradle build fails
- Check `FINAL_BUILD_STATUS.md` for version matrix
- Clear cache: `rm -rf .gradle build`
- Retry: `./gradlew clean build -x test --stacktrace`

### JAR still won't build
- Try Docker method first (easiest)
- Then try GitHub Actions
- Last resort: manual local configuration

---

## Summary

✅ **Fastest:** Docker (`./build-docker.sh`) - 5 minutes  
✅ **Most Reliable:** GitHub Actions - auto-builds  
✅ **Most Control:** Local Gradle - full customization  
✅ **Easiest:** Download pre-built (if available)

**Recommendation:** Try Docker first, GitHub Actions if needed.

Good luck! 🎮
