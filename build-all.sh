#!/bin/bash
# Build All Methods - Compile using all available methods

PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$PROJECT_DIR"

echo "╔══════════════════════════════════════════════════════════╗"
echo "║          Building Axiom-Portals with All Methods         ║"
echo "╚══════════════════════════════════════════════════════════╝"
echo ""

FOUND_JAR=false

# Try Gradle if available
if command -v gradle &> /dev/null; then
    echo "▶️  Method 1: Gradle Build..."
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    ./build.sh
    if [ -f "build/libs/axiom-portals-1.0.0.jar" ]; then
        FOUND_JAR=true
    fi
    echo ""
fi

# Try Simple Build if javac available
if command -v javac &> /dev/null && [ "$FOUND_JAR" = false ]; then
    echo "▶️  Method 2: Simple Java Build..."
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    ./build-simple.sh
    if [ -f "build/libs/axiom-portals-simple-1.0.0.jar" ]; then
        FOUND_JAR=true
    fi
    echo ""
fi

# Try Manual Build if javac available
if command -v javac &> /dev/null && [ "$FOUND_JAR" = false ]; then
    echo "▶️  Method 3: Manual Build..."
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    ./build-manual.sh
    if [ -f "build/libs/axiom-portals-1.0.0.jar" ]; then
        FOUND_JAR=true
    fi
    echo ""
fi

# Report results
echo "╔══════════════════════════════════════════════════════════╗"
echo "║                      BUILD SUMMARY                       ║"
echo "╚══════════════════════════════════════════════════════════╝"
echo ""

if [ -d "build/libs" ]; then
    JAR_COUNT=$(ls build/libs/*.jar 2>/dev/null | wc -l)
    if [ $JAR_COUNT -gt 0 ]; then
        echo "✅ Successfully built $JAR_COUNT JAR file(s):"
        echo ""
        ls -lh build/libs/*.jar | awk '{print "   📦 " $9 " (" $5 ")"}'
        echo ""
        echo "📋 Installation:"
        echo "   1. Copy JAR to ~/.minecraft/mods/"
        echo "   2. Install Axiom 1.20.1 and Immersive Portals 1.20.1"
        echo "   3. Launch Minecraft with Fabric loader"
        echo ""
    else
        echo "❌ No JAR files created. Check logs above for errors."
        exit 1
    fi
else
    echo "❌ Build failed. Build directory not found."
    exit 1
fi
