#!/bin/bash
# Axiom-Portals: Smart Build Selector
# Tries all build methods and helps you choose the best one

PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$PROJECT_DIR"

echo "╔══════════════════════════════════════════════════════════╗"
echo "║        Axiom-Portals Mod Builder v1.0                   ║"
echo "║     Choose your preferred build method below:           ║"
echo "╚══════════════════════════════════════════════════════════╝"
echo ""

# Check available tools
echo "🔍 Checking available build tools..."
echo ""

HAS_GRADLE=false
HAS_JAVAC=false

if command -v gradle &> /dev/null; then
    GRADLE_VER=$(gradle --version 2>/dev/null | head -1)
    echo "✓ Gradle found: $GRADLE_VER"
    HAS_GRADLE=true
else
    echo "✗ Gradle not found"
fi

if command -v javac &> /dev/null; then
    JAVA_VER=$(javac -version 2>&1 | head -1)
    echo "✓ JavaC found: $JAVA_VER"
    HAS_JAVAC=true
else
    echo "✗ JavaC not found"
fi

echo ""
echo "╔══════════════════════════════════════════════════════════╗"
echo "║                    BUILD OPTIONS                         ║"
echo "╚══════════════════════════════════════════════════════════╝"
echo ""

if [ "$HAS_GRADLE" = true ]; then
    echo "1️⃣  Gradle Build (Recommended for Production)"
    echo "   • Full Fabric Loom integration"
    echo "   • Proper mixin processing"
    echo "   • Bytecode remapping"
    echo "   • All dependencies resolved"
    echo "   Command: ./build.sh"
    echo ""
fi

if [ "$HAS_JAVAC" = true ]; then
    echo "2️⃣  Simple Java Compilation (Quick & Simple)"
    echo "   • Direct Java compilation"
    echo "   • No dependency resolution"
    echo "   • Faster build time (~10 seconds)"
    echo "   • Basic JAR packaging"
    echo "   Command: ./build-simple.sh"
    echo ""
    
    echo "3️⃣  Manual Step-by-Step Build (Educational)"
    echo "   • Shows every compilation step"
    echo "   • Great for understanding the process"
    echo "   • Detailed progress output"
    echo "   • Perfect for debugging"
    echo "   Command: ./build-manual.sh"
    echo ""
fi

echo "4️⃣  Show All Available Options"
echo "   Command: ./build-all.sh"
echo ""
echo "0️⃣  Exit"
echo ""

# Interactive selection or command-line arg
if [ -n "$1" ]; then
    CHOICE="$1"
else
    read -p "Choose option [0-4]: " CHOICE
fi

echo ""

case "$CHOICE" in
    1)
        if [ "$HAS_GRADLE" = true ]; then
            echo "▶️  Starting Gradle build..."
            echo ""
            ./build.sh
        else
            echo "❌ Gradle not available!"
            exit 1
        fi
        ;;
    2)
        if [ "$HAS_JAVAC" = true ]; then
            echo "▶️  Starting simple build..."
            echo ""
            ./build-simple.sh
        else
            echo "❌ JavaC not available!"
            exit 1
        fi
        ;;
    3)
        if [ "$HAS_JAVAC" = true ]; then
            echo "▶️  Starting manual build..."
            echo ""
            ./build-manual.sh
        else
            echo "❌ JavaC not available!"
            exit 1
        fi
        ;;
    4)
        echo "Attempting all available build methods..."
        echo ""
        
        if [ "$HAS_GRADLE" = true ]; then
            echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            echo "Option 1: Gradle Build"
            echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            ./build.sh 2>&1 | tail -20
            echo ""
        fi
        
        if [ "$HAS_JAVAC" = true ]; then
            echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            echo "Option 2: Simple Build"
            echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            ./build-simple.sh 2>&1 | tail -20
            echo ""
        fi
        
        echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
        echo "Available JARs:"
        echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
        if [ -d "build/libs" ]; then
            ls -lh build/libs/*.jar 2>/dev/null || echo "No JARs found yet"
        else
            echo "Build directory not created"
        fi
        ;;
    0)
        echo "Exiting..."
        exit 0
        ;;
    *)
        echo "❌ Invalid option. Please choose 0-4"
        exit 1
        ;;
esac
