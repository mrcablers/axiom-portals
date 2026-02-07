#!/bin/bash
# Manual Build Script - Step by Step
# Shows every step of the build process

PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$PROJECT_DIR"

echo "╔════════════════════════════════════════════════════╗"
echo "║     Axiom-Portals Manual Build Process            ║"
echo "║     (Step-by-step with explanations)              ║"
echo "╚════════════════════════════════════════════════════╝"
echo ""

# Step 1: Check environment
echo "📋 STEP 1: Checking build environment..."
echo ""

if ! command -v javac &> /dev/null; then
    echo "❌ javac not found. Install JDK 17+"
    exit 1
fi

JAVA_VERSION=$(javac -version 2>&1 | head -1)
echo "✓ $JAVA_VERSION"

# Step 2: Create directories
echo ""
echo "📁 STEP 2: Creating build directories..."
mkdir -p build/classes
mkdir -p build/libs
mkdir -p build/sources
echo "✓ Directories created"

# Step 3: Copy sources
echo ""
echo "📄 STEP 3: Preparing source files..."
SRC_COUNT=$(find src/main/java -name "*.java" | wc -l)
echo "✓ Found $SRC_COUNT Java source files"

# Step 4: Compile
echo ""
echo "⚙️  STEP 4: Compiling Java source code..."
echo "   Target: Java 17 bytecode"

javac -d build/classes \
    -source 17 -target 17 \
    -encoding UTF-8 \
    -g \
    $(find src/main/java -name "*.java")

if [ $? -ne 0 ]; then
    echo "❌ Compilation failed!"
    exit 1
fi

CLASS_COUNT=$(find build/classes -name "*.class" | wc -l)
echo "✓ Compiled $CLASS_COUNT class files"

# Step 5: Create manifest
echo ""
echo "📝 STEP 5: Creating JAR manifest..."
cat > MANIFEST.MF << 'EOF'
Manifest-Version: 1.0
Implementation-Title: Axiom-Portals
Implementation-Version: 1.0.0
Implementation-Vendor: mrcablers
Specification-Title: Axiom-Portals Compatibility Mod
Specification-Version: 1.0.0
Specification-Vendor: AxiomGames
Created-By: Build Script
Multi-Release: true
EOF
echo "✓ Manifest created"

# Step 6: Package JAR
echo ""
echo "📦 STEP 6: Creating JAR archive..."
JAR_FILE="build/libs/axiom-portals-1.0.0.jar"

jar cfm "$JAR_FILE" MANIFEST.MF -C build/classes .

if [ $? -ne 0 ]; then
    echo "❌ JAR creation failed!"
    exit 1
fi

# Step 7: Add resources
echo ""
echo "🎨 STEP 7: Adding resource files..."
if [ -d "src/main/resources" ]; then
    jar uf "$JAR_FILE" -C src/main/resources .
    RESOURCE_COUNT=$(find src/main/resources -type f | wc -l)
    echo "✓ Added $RESOURCE_COUNT resource files"
else
    echo "⚠️  No resources found"
fi

# Step 8: Verify
echo ""
echo "✅ STEP 8: Verifying JAR..."
if [ -f "$JAR_FILE" ]; then
    SIZE=$(ls -lh "$JAR_FILE" | awk '{print $5}')
    ENTRIES=$(jar tf "$JAR_FILE" | wc -l)
    echo "✓ JAR file verified"
    echo "  File: $JAR_FILE"
    echo "  Size: $SIZE"
    echo "  Entries: $ENTRIES"
else
    echo "❌ JAR file not created!"
    exit 1
fi

# Summary
echo ""
echo "╔════════════════════════════════════════════════════╗"
echo "║              ✅ BUILD SUCCESSFUL!                 ║"
echo "╚════════════════════════════════════════════════════╝"
echo ""
echo "📦 Output JAR: $JAR_FILE"
echo ""
echo "📋 Next Steps:"
echo "   1. Verify the JAR:"
echo "      jar tf $JAR_FILE | head -20"
echo ""
echo "   2. Copy to Minecraft:"
echo "      cp $JAR_FILE ~/.minecraft/mods/"
echo ""
echo "   3. Install required mods:"
echo "      - Axiom 1.20.1"
echo "      - Immersive Portals 1.20.1"
echo ""
echo "   4. Launch Minecraft with Fabric"
echo ""
echo "⚠️  Note: This build skips Fabric Loom processing."
echo "   For production use, consider: gradle build"
echo ""
