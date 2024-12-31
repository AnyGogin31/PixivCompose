# Contributing to PixivCompose

Thank you for considering contributing to **PixivCompose!** Your time and effort are greatly appreciated. This guide
outlines the steps to contribute effectively.

---

## Table of Contents

1. [How Can You Contribute?](#how-can-you-contribute)
2. [Setting Up the Project](#setting-up-the-project)
3. [Submitting a Pull Request](#submitting-a-pull-request)
4. [Style Guide](#style-guide)
5. [Reporting Issues](#reporting-issues)

---

## How Can You Contribute?

Here are some ways to contribute to the project:

- Reporting bugs or issues.
- Suggesting new features or enhancements.
- Writing or improving documentation.
- Submitting pull requests for fixes or new functionality.
- Reviewing existing pull requests.

---

## Setting Up the Project

1. Fork the repository to your GitHub account.
2. Clone your fork locally:
   ```bash
   git clone https://github.com/your-username/PixivCompose.git
   ```
3. Navigate to the project directory:
   ```bash
   cd PixivCompose
   ```
4. Install the required dependencies:
   ```bash
   ./gradlew build
   ```
5. Run the tests to verify the setup:
   ```bash
   ./gradlew test
   ```

---

## Submitting a Pull Request

1. Create a new branch for your changes:
   ```bash
   git checkout -b feature/your-feature-name
   ```
2. Make your changes and commit them:
   ```bash
   git add .
   git commit -m "feat/fix(...): brief description of the feature/fix"
   ```
3. Push the branch to your fork:
   ```bash
   git push origin feature/your-feature-name
   ```
4. Open a pull request:
    - Go to the original repository on GitHub.
    - Click the **"New Pull Request"** button.
    - Select your branch and submit the pull request.

### Pull Request Guidelines

- Reference any related issues (e.g., `Fixes #123`).
- Ensure your branch is up to date with the main branch.
- Add tests for new features or bug fixes, if applicable.
- Follow the [Style Guide](#style-guide).

---

## Style Guide

- Follow the [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html).
- Ensure all code is formatted using the project’s configured linter or formatter.

---

## Reporting Issues

If you encounter a bug or want to suggest a new feature:

1. Check the [Issues](https://github.com/AnyGogin31/PixivCompose/issues) tab to see if it has already been reported.
2. If not, open a new issue and include the following:
    - A clear and concise description.
    - Steps to reproduce (for bugs).
    - Expected vs. actual behavior.
    - Relevant screenshots or logs, if applicable.

---

Thank you for contributing to **PixivCompose!** 🚀
