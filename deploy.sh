#!/bin/bash

# deploy.sh - Helper script to push changes to a branch
# Usage: ./deploy.sh

echo "=========================================="
echo "    62댕냥이 Deployment/Push Script      "
echo "=========================================="

# Check if git is initialized
if [ ! -d ".git" ]; then
    echo "Error: Not a git repository."
    exit 1
fi

# Get current branch
CURRENT_BRANCH=$(git rev-parse --abbrev-ref HEAD)
echo "Current branch: $CURRENT_BRANCH"

# Prompt for target branch (default to current)
read -p "Enter target branch to push to (default: $CURRENT_BRANCH): " TARGET_BRANCH
TARGET_BRANCH=${TARGET_BRANCH:-$CURRENT_BRANCH}

# Prompt for commit message
read -p "Enter commit message: " COMMIT_MSG
if [ -z "$COMMIT_MSG" ]; then
    echo "Error: Commit message cannot be empty."
    exit 1
fi

echo "------------------------------------------"
echo "Adding all changes..."
git add .

echo "Committing with message: '$COMMIT_MSG'..."
git commit -m "$COMMIT_MSG"

echo "Pushing to origin/$TARGET_BRANCH..."
git push origin "$TARGET_BRANCH"

if [ $? -eq 0 ]; then
    echo "=========================================="
    echo "       Push Successful!                   "
    echo "=========================================="
    
    # Check if gh CLI is installed
    if ! command -v gh &> /dev/null; then
        echo "Warning: GitHub CLI ('gh') is not installed."
        echo "Skipping automatic Pull Request creation."
        echo "To enable this, install it: brew install gh"
        exit 0
    fi

    echo "------------------------------------------"
    read -p "Do you want to create a Pull Request now? (y/n) " CREATE_PR
    if [[ "$CREATE_PR" =~ ^[Yy]$ ]]; then
        # Check if already logged in
        if ! gh auth status &> /dev/null; then
            echo "You need to login to GitHub CLI first."
            gh auth login
        fi

        echo "Creating Pull Request..."
        # Create PR: base=main (or prompt?), title=commit msg, body=interactive or same
        gh pr create --base main --head "$TARGET_BRANCH" --title "$COMMIT_MSG" --body "$COMMIT_MSG" --web
    fi

else
    echo "=========================================="
    echo "       Push Failed!                       "
    echo "=========================================="
    exit 1
fi
