#!/bin/bash
set -e

echo "=========================================="
echo "  62댕냥이 AUTO Deploy Script 🚀  "
echo "=========================================="

if [ ! -d ".git" ]; then
    echo "❌ Error: Not a git repository."
    exit 1
fi

CURRENT_BRANCH=$(git rev-parse --abbrev-ref HEAD)
echo "📌 Current branch: $CURRENT_BRANCH"

PROTECTED_BRANCHES=("main")
for branch in "${PROTECTED_BRANCHES[@]}"; do
    if [ "$CURRENT_BRANCH" = "$branch" ]; then
        echo "❌ Direct push to '$branch' is not allowed."
        exit 1
    fi
done

if git diff --quiet && git diff --cached --quiet; then
    echo "⚠️ No changes to commit."
    exit 0
fi

echo "✅ Auto adding all changes..."
git config user.name "62댕냥이 Bot"
git config user.email "bot@github.com"
git add .
COMMIT_MSG="🚀 $(date '+%Y-%m-%d %H:%M:%S') Auto deploy from $CURRENT_BRANCH"
git commit -m "$COMMIT_MSG"

echo "🚀 Pushing to origin/$CURRENT_BRANCH..."
git push origin "$CURRENT_BRANCH"

echo "=========================================="
echo "  ✅ Push Successful!                  "
echo "=========================================="
