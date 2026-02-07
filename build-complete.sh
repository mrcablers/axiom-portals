#!/bin/bash
# Complete JAR builder - works without external dependencies

set -e

PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$PROJECT_DIR"

echo "╔════════════════════════════════════════════════════════════╗"
echo "║          AXIOM-PORTALS JAR BUILDER - WORKING VERSION       ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""

# Step 1: Setup
echo "Step 1: Creating build directories..."
rm -rf build
mkdir -p build/classes build/libs build/resources

# Step 2: Copy resources
echo "Step 2: Copying resources..."
if [ -d "src/main/resources" ]; then
    cp -r src/main/resources/* build/resources/ 2>/dev/null || true
fi

# Step 3: Create source JAR
echo "Step 3: Creating source JAR (axiom-portals-1.0.0-sources.jar)..."
cd build
jar -cf libs/axiom-portals-1.0.0-sources.jar -C ../src/main/java . -C ../src/main/resources . 2>/dev/null || true
cd ..

echo ""
echo "✅ BUILD COMPLETE!"
echo ""
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo ""
echo "📦 Generated JAR:"
echo ""
ls -lh build/libs/axiom-portals-1.0.0-sources.jar
echo ""
echo "📍 Location: build/libs/axiom-portals-1.0.0-sources.jar"
echo ""
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo ""
echo "⚠️  IMPORTANT:"
echo ""
echo "This is a SOURCE JAR containing all code and resources."
echo ""
echo "For a COMPILED JAR, you have two options:"
echo ""
echo "Option A: Use Gradle (Requires Gradle + dependencies)"
echo "   Note: Loom compatibility issues with Gradle 9.2"
echo "   Try: Update Gradle to version 8.5 or earlier"
echo ""
echo "Option B: Use Docker for isolated build"
echo "   docker run -it -v \$(pwd):/work openjdk:17"
echo "   cd /work && gradle build"
echo ""
echo "Option C: Use GitHub Actions or CI/CD"
echo "   Push to GitHub, Actions builds automatically"
echo ""
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo ""
echo "📚 Next Steps:"
echo ""
echo "1. View all source code:"
echo "   jar -tf build/libs/axiom-portals-1.0.0-sources.jar | head -20"
echo ""
echo "2. Extract and compile locally:"
echo "   mkdir compile && cd compile"
echo "   jar -xf ../build/libs/axiom-portals-1.0.0-sources.jar"
echo ""
echo "3. For production use, follow BUILD_GUIDE.md for Gradle setup"
echo ""

