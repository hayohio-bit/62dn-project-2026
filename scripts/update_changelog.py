import os
import sys
from datetime import datetime

CHANGELOG_PATH = os.path.join(os.path.dirname(__file__), '..', 'CHANGELOG.md')

def update_changelog(message, category='Added'):
    if not os.path.exists(CHANGELOG_PATH):
        print(f"Error: {CHANGELOG_PATH} not found.")
        return

    with open(CHANGELOG_PATH, 'r', encoding='utf-8') as f:
        lines = f.readlines()

    new_lines = []
    unreleased_found = False
    category_found = False
    
    target_category = f"### {category}"
    entry = f"- {message}\n"

    for i, line in enumerate(lines):
        new_lines.append(line)
        if "## [Unreleased]" in line:
            unreleased_found = True
            continue
        
        if unreleased_found:
            if line.startswith("## ["): # Next version header
                if not category_found:
                    # If category wasn't found in Unreleased, add it before next header
                    new_lines.insert(-1, f"\n{target_category}\n{entry}")
                unreleased_found = False
                continue
            
            if line.strip() == target_category:
                category_found = True
                new_lines.append(entry)
                continue

    if unreleased_found and not category_found:
        # If we reached the end of the file and unreleased was the last section
        new_lines.append(f"\n{target_category}\n{entry}")

    with open(CHANGELOG_PATH, 'w', encoding='utf-8') as f:
        f.writelines(new_lines)
    print(f"Updated CHANGELOG.md with: [{category}] {message}")

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Usage: python update_changelog.py \"message\" [category]")
        sys.exit(1)
    
    msg = sys.argv[1]
    cat = sys.argv[2] if len(sys.argv) > 2 else 'Added'
    update_changelog(msg, cat)
