#!/bin/bash
# Simple Java Compiler Build Script
# Compiles Java source files directly without Gradle/Loom
# Useful for quick compilation without dependency resolution

PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SRC_DIR="$PROJECT_DIR/src/main/java"
OUT_DIR="$PROJECT_DIR/build/classes"
RESOURCES_DIR="$PROJECT_DIR/src/main/resources"
JAR_OUTPUT="$PROJECT_DIR/build/libs/axiom-portals-simple-1.0.0.jar"

echo "==================================="
echo "Axiom-Portals Simple Compiler"
echo "==================================="

# Create output directories
mkdir -p "$OUT_DIR"
mkdir -p "$(dirname "$JAR_OUTPUT")"

# Compile Java files
echo "Compiling Java source files..."
javac -d "$OUT_DIR" \
    -source 17 -target 17 \
    -encoding UTF-8 \
    $(find "$SRC_DIR" -name "*.java")

if [ $? -ne 0 ]; then
    echo "❌ Compilation failed!"
    exit 1
fi

echo "✓ Java compilation successful"

# Create JAR with manifest
echo "Creating JAR file..."
cd "$OUT_DIR"

# Create manifest
MANIFEST="$PROJECT_DIR/MANIFEST.MF"
cat > "$MANIFEST" << 'EOF'
Manifest-Version: 1.0
Implementation-Title: Axiom-Portals
Implementation-Version: 1.0.0
Implementation-Vendor: AxiomPortals
Specification-Title: Axiom-Portals Compatibility Mod
Specification-Version: 1.0.0
EOF

# Package into JAR
jar cfm "$JAR_OUTPUT" "$MANIFEST" -C "$OUT_DIR" .

# Copy resources
if [ -d "$RESOURCES_DIR" ]; then
    jar uf "$JAR_OUTPUT" -C "$RESOURCES_DIR" .
fi

echo "✓ JAR created: $JAR_OUTPUT"
echo ""
echo "==================================="
echo "✅ Build Complete!"
echo "==================================="
echo "JAR Location: $JAR_OUTPUT"
echo "Size: $(ls -lh "$JAR_OUTPUT" | awk '{print $5}')"
echo ""
echo "⚠️  Note: This is a simple compilation without:"
echo "   - Mixin processing"
echo "   - Bytecode remapping"
echo "   - Dependency resolution"
echo ""
echo "For production, use: gradle build"
