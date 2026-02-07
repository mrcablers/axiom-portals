#!/bin/bash
# Gradle Build Wrapper Script
# Downloads/uses system gradle to build the project

PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$PROJECT_DIR"

echo "==================================="
echo "Axiom-Portals Gradle Builder"
echo "==================================="

# Check if gradle is available
if ! command -v gradle &> /dev/null; then
    echo "❌ Gradle not found. Install with: brew install gradle (Mac) or apt install gradle (Linux)"
    exit 1
fi

GRADLE_VERSION=$(gradle --version | head -1)
echo "Using: $GRADLE_VERSION"
echo ""

# Run gradle build
echo "Building Minecraft mod..."
gradle clean build -x test

if [ $? -eq 0 ]; then
    echo ""
    echo "==================================="
    echo "✅ Build Successful!"
    echo "==================================="
    echo ""
    JAR_FILE=$(find build/libs -name "axiom-portals-*.jar" -type f | head -1)
    if [ -n "$JAR_FILE" ]; then
        echo "📦 JAR Location: $JAR_FILE"
        echo "📏 Size: $(ls -lh "$JAR_FILE" | awk '{print $5}')"
        echo ""
        echo "✓ Installation:"
        echo "  1. Place in .minecraft/mods/"
        echo "  2. Install Axiom 1.20.1"
        echo "  3. Install Immersive Portals 1.20.1"
        echo "  4. Launch Minecraft"
    fi
else
    echo ""
    echo "==================================="
    echo "❌ Build Failed!"
    echo "==================================="
    echo ""
    echo "Try alternatives:"
    echo "  ./build-simple.sh    (Simple Java compilation)"
    echo "  ./build-manual.sh    (Manual build with detailed steps)"
    exit 1
fi
