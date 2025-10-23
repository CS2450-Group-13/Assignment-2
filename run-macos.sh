#!/bin/bash
# =====================================================
# JavaFX Project Runner (macOS / Linux / WSL)
# =====================================================

# Stop immediately if a command fails
set -e

# Compile all Java files into /out
echo "Compiling Java source files..."
rm -rf out
mkdir -p out
find src -name "*.java" > sources.txt
javac -d out @sources.txt
rm sources.txt

# Copy stylesheet to compiled output
echo "Copying stylesheet..."
mkdir -p out/src/
cp src/styles.css out/src/ 2>/dev/null || echo "(No stylesheet found, skipping)"

# Run JavaFX app
echo "Running JavaFX app..."
java -cp out src.Main
