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

run_ktlint_checks() {
    echo -e "\nRunning ktlint checks..."
    ./gradlew.bat ktlintCheck --daemon > /tmp/ktlint-result 2>&1
    KT_EXIT_CODE=$?

    if [ ${KT_EXIT_CODE} -ne 0 ]; then
        cat /tmp/ktlint-result
        echo -e "\nktlint found issues. Run './gradlew ktlintFormat' to fix them automatically."
        exit ${KT_EXIT_CODE}
    else
        echo -e "ktlint passed. Code style is clean."
    fi
}

cleanup_temp_files() {
    rm -f /tmp/ktlint-result
}

print_success_message() {
    GIT_USERNAME=$(git config user.name)
    echo -e "\nGood job, $GIT_USERNAME. Ready to push."
}

trap cleanup_temp_files EXIT

check_current_branch
run_ktlint_checks
print_success_message

exit 0
