#!/bin/bash
# AXIOM-PORTALS: Complete Build & Usage Guide
# Everything you need to know in one place

echo "╔════════════════════════════════════════════════════════════╗"
echo "║                                                            ║"
echo "║          AXIOM-PORTALS COMPATIBILITY MOD v1.0             ║"
echo "║     Minecraft 1.20.1 - Immersive Portals + Axiom          ║"
echo "║                                                            ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""

PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$PROJECT_DIR"

echo "📍 What you have:"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo ""
echo "✓ Complete source code (19 Java classes)"
echo "✓ Comprehensive documentation (10+ files)"
echo "✓ Build system (Gradle + alternatives)"
echo "✓ Multiple build methods available"
echo ""

echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo ""
echo "🎯 WHAT DO YOU WANT TO DO?"
echo ""
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo ""
echo "1️⃣  GET THE JAR FILE"
echo "    └─ Quick methods to get axiom-portals-1.0.0.jar"
echo "       Run: cat GET_JAR.md"
echo ""
echo "2️⃣  BUILD THE MOD MYSELF"
echo "    └─ Step-by-step build instructions"
echo "       Requirements: Gradle + Java 17+"
echo "       Time: ~5 minutes"
echo ""
echo "3️⃣  UNDERSTAND THE PROJECT"
echo "    └─ Documentation and architecture"
echo "       Run: cat START_HERE.md"
echo ""
echo "4️⃣  INSTALLATION INSTRUCTIONS"
echo "    └─ How to set up in Minecraft"
echo ""
echo "5️⃣  BUILD STATUS REPORT"
echo "    └─ Current build environment analysis"
echo "       Run: bash build-status.sh"
echo ""
echo "0️⃣  EXIT"
echo ""
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo ""

read -p "Choose option [0-5]: " CHOICE

case "$CHOICE" in
    1)
        echo ""
        cat GET_JAR.md
        ;;
    2)
        echo ""
        echo "🔨 BUILD OPTIONS"
        echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
        echo ""
        echo "📦 OPTION A: Gradle Build (Recommended)"
        echo "   gradle build"
        echo "   Output: build/libs/axiom-portals-1.0.0.jar"
        echo ""
        echo "   Requirements:"
        echo "   • Gradle 8.0+"
        echo "   • Java 17+"
        echo "   • Internet (for dependencies)"
        echo ""
        
        if command -v gradle &> /dev/null; then
            GRADLE_VER=$(gradle --version 2>/dev/null | head -1)
            echo "   ✓ GRADLE FOUND: $GRADLE_VER"
            echo "   ✓ Ready to build!"
            echo ""
            read -p "   Run gradle build now? [y/n]: " BUILD_NOW
            if [ "$BUILD_NOW" = "y" ]; then
                gradle build
            fi
        else
            echo "   ⚠️  Gradle not found"
            echo "   Install: brew install gradle (Mac) or apt install gradle (Linux)"
        fi
        ;;
    3)
        echo ""
        cat START_HERE.md
        ;;
    4)
        echo ""
        echo "📱 INSTALLATION INSTRUCTIONS"
        echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
        echo ""
        echo "STEP 1: Get the JAR"
        echo "────────────────────────────────────────────────────────"
        echo "You need: axiom-portals-1.0.0.jar"
        echo ""
        echo "Options:"
        echo "  a) Download from releases (easiest)"
        echo "  b) Build yourself with gradle build"
        echo ""
        echo "STEP 2: Prepare Minecraft"
        echo "────────────────────────────────────────────────────────"
        echo "1. Install Fabric Loader for 1.20.1"
        echo "   → fabricmc.net"
        echo ""
        echo "STEP 3: Install Mods"
        echo "────────────────────────────────────────────────────────"
        echo "Place these in ~/.minecraft/mods/:"
        echo "  • axiom-portals-1.0.0.jar"
        echo "  • axiom-2.0.0-fabric-1.20.1.jar"
        echo "  • immersive-portals-mod-1.0.0+1.20.1-fabric.jar"
        echo ""
        echo "STEP 4: Launch Game"
        echo "────────────────────────────────────────────────────────"
        echo "1. Open Minecraft Launcher"
        echo "2. Select Fabric 1.20.1 profile"
        echo "3. Click Play"
        echo ""
        echo "STEP 5: Verify Installation"
        echo "────────────────────────────────────────────────────────"
        echo "1. Create new world"
        echo "2. Use Axiom to create a selection"
        echo "3. Walk through an Immersive Portal"
        echo "4. Selection should persist in the new dimension ✓"
        echo ""
        echo "✅ All set! You're ready to use the mod!"
        echo ""
        ;;
    5)
        echo ""
        bash build-status.sh
        ;;
    0)
        echo "Exiting..."
        exit 0
        ;;
    *)
        echo "❌ Invalid option"
        exit 1
        ;;
esac

echo ""
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo ""
echo "📚 Documentation Files:"
echo ""
echo "  START_HERE.md      - Quick start guide"
echo "  README.md          - Main overview"
echo "  BUILD_GUIDE.md     - Build instructions"
echo "  API_DOCS.md        - API reference"
echo "  DEVELOPMENT.md     - Architecture details"
echo "  GET_JAR.md         - How to get the JAR"
echo "  INDEX.md           - File navigation"
echo ""
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo ""
echo "✨ Happy modding! 🎮"
echo ""
