#!/bin/bash
# Docker-based JAR builder - Works reliably without local dependency conflicts

set -e

PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$PROJECT_DIR"

echo "╔════════════════════════════════════════════════════════════╗"
echo "║      AXIOM-PORTALS JAR BUILDER - DOCKER VERSION           ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""
echo "This script compiles the mod using Docker (no local setup needed!"
echo ""

# Check if Docker is installed
if ! command -v docker &> /dev/null
then
    echo "❌ ERROR: Docker is not installed!"
    echo ""
    echo "Please install Docker first:"
    echo "  - macOS: brew install docker"
    echo "  - Windows: Download Docker Desktop"
    echo "  - Linux: sudo apt-get install docker.io"
    echo ""
    exit 1
fi

echo "Step 1: Checking Docker daemon..."
if ! docker info &> /dev/null
then
    echo "❌ ERROR: Docker daemon is not running!"
    echo ""
    echo "Please start Docker:"
    echo "  - macOS: Open Docker.app"
    echo "  - Windows: Open Docker Desktop"
    echo "  - Linux: sudo systemctl start docker"
    echo ""
    exit 1
fi
echo "✅ Docker is running"
echo ""

echo "Step 2: Building mod in Docker container..."
echo "  Image: gradle:8.5-jdk17"
echo "  Gradle: 8.5"
echo "  Java: 17"
echo ""

# Run gradle build in Docker
docker run --rm \
    -v "$PROJECT_DIR":/workspace \
    -w /workspace \
    gradle:8.5-jdk17 \
    bash -c "./gradlew clean build -x test --no-daemon"

BUILD_RESULT=$?

echo ""
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo ""

if [ $BUILD_RESULT -eq 0 ]; then
    echo "✅ BUILD SUCCESSFUL!"
    echo ""
    echo "📦 Generated JAR Files:"
    echo ""
    if [ -f "build/libs/axiom-portals-1.0.0.jar" ]; then
        echo "  ✅ build/libs/axiom-portals-1.0.0.jar"
        ls -lh "build/libs/axiom-portals-1.0.0.jar"
    fi
    if [ -f "build/libs/axiom-portals-1.0.0-sources.jar" ]; then
        echo ""
        echo "  ✅ build/libs/axiom-portals-1.0.0-sources.jar"
        ls -lh "build/libs/axiom-portals-1.0.0-sources.jar"
    fi
    echo ""
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    echo ""
    echo "📍 Installation:"
    echo ""
    echo "  1. Copy JAR to Minecraft mods directory:"
    echo "     cp build/libs/axiom-portals-1.0.0.jar ~/.minecraft/mods/"
    echo ""
    echo "  2. Also install dependencies:"
    echo "     - Fabric Loader 0.14.25+"
    echo "     - Fabric API"
    echo "     - Axiom mod"
    echo "     - Immersive Portals mod"
    echo ""
    echo "  3. Launch Minecraft with Fabric"
    echo ""
    exit 0
else
    echo "❌ BUILD FAILED!"
    echo ""
    echo "See error messages above for details"
    echo ""
    exit 1
fi
