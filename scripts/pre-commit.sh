#!/bin/bash

check_current_branch() {
    echo -e "\nChecking current branch..."
    CURRENT_BRANCH=$(git rev-parse --abbrev-ref HEAD)
    if [[ "$CURRENT_BRANCH" == "main" ]]; then
        echo -e "Direct commits to '$CURRENT_BRANCH' are not allowed. Switch to a feature branch."
        exit 1
    else
        echo -e "You are on branch '$CURRENT_BRANCH'. Proceeding."
    fi
}

check_for_changes() {
    if git diff --cached --quiet; then
        echo -e "\nNo staged changes to commit. Aborting pre-commit hook."
        exit 0
    fi
}

print_success_message() {
    GIT_USERNAME=$(git config user.name)
    echo -e "\nGood job, $GIT_USERNAME. Ready to commit."
}

check_current_branch
check_for_changes
print_success_message

exit 0
