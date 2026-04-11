
# CSS201 BorneoFresh Market — Group Project

> Read this before touching any code.

---

## Overview

This repository contains the CSS201 Programming 2 group project for BorneoFresh Market. Every team member is expected to read and follow these instructions before making any commits. The goal is to keep our work clean, organised, and conflict-free throughout the development process.

---

## Branch Structure

This repository has four branches in total. The `main` branch always contains stable, reviewed code. Each member has their own personal working branch for active development. Nobody commits directly to `main`.

| Branch | Owner | Purpose |
|---|---|---|
| `main` | All | Stable, reviewed code only. Do not commit here directly. |
| `feature/member1` | Member 1 | Member 1's working branch. |
| `feature/member2` | Member 2 | Member 2's working branch. |
| `feature/member3` | Member 3 | Member 3's working branch. |

---

## Getting Started — First-Time Setup

Follow these steps in order the first time you set up the project on your machine.

### Step 1 — Clone the Repository

Open your terminal and run the following commands to download the project to your local machine.

```bash
git clone https://github.com/your-username/CSS201-BorneoFresh.git
cd CSS201-BorneoFresh
```

### Step 2 — Switch to Your Branch

Once inside the project folder, switch to your assigned branch. Replace `your-branch-name` with your actual branch name (e.g. `feature/member2`).

```bash
git checkout feature/your-branch-name
```

You can verify you are on the correct branch by running `git branch`, which will show your current branch with an asterisk (*) next to it.

### Step 3 — Pull the Project Structure

The repository owner will push the agreed package structure to `main` before anyone starts coding. Pull it into your branch so you start from the same foundation.

```bash
git pull origin main
```

---

## Day-to-Day Workflow

Every time you sit down to work, follow this routine to keep your branch up to date and avoid conflicts.

**1. Pull the latest changes from your branch before starting any work.**
```bash
git pull origin feature/your-branch-name
```

**2. Make your code changes within your assigned files only.**

**3. Stage and commit your changes with a clear, descriptive message.**
```bash
git add .
git commit -m "[ClassName] Brief description of change"
```

**4. Push your changes to your remote branch.**
```bash
git push origin feature/your-branch-name
```
If you want to keep commits separate per class, stage individual files 
rather than using `git add .`. For example:

```bash
git add src/borneofresh/model/ClassName.java
git commit -m "[ClassName] Brief description of change"
```

This keeps the commit history clean and traceable to a single class 
per commit.

---

## Commit Message Format

All commit messages must follow this format so the commit history remains readable and traceable.

```
[ClassName] Brief description of what changed
```

Examples of correct commit messages:

```
[Customer] Add placeOrder method
[ProductCatalogue] Fix getAvailableProducts filter
[Order] Implement displayOrderSummary
```

---

## Merging Into Main

When your assigned feature or class is complete and fully tested, open a **Pull Request (PR)** on GitHub to merge your branch into `main`. Do not merge it yourself — the repository owner will review and approve all pull requests before merging. This ensures no untested code enters the shared codebase.

To open a Pull Request, go to the repository on GitHub, click the **Pull Requests** tab, click **New Pull Request**, set the base branch to `main` and the compare branch to your own branch, then click **Create Pull Request**. Include a short description of what you implemented.

---

## Task Delegation

Each member is responsible for the following classes and console modules. Only edit files within your assigned scope to avoid merge conflicts.

| Member | Assigned Classes | Console Module |
|---|---|---|
| Member 1 | `BorneoFreshSystem`, `User`, `Administrator` | Main menu, authentication (login/logout) |
| Member 2 | `ProductCatalogue`, `Product`, `FreshProduce`, `OrganicProduct`, `DailyEssential` | Administrative Module (add, update, view products) |
| Member 3 | `Customer`, `Order`, `OrderItem`, `CustomerRegistry` | Customer Account Management, Product Browsing, Order Management |

All three members are jointly responsible for Task 4 (Enhancements), Task 5 (Video Presentation), and Task 6 (Peer Evaluation). Each member should explain the parts of the system they developed in the video.

---

## Package Structure

The repository owner will push this structure to `main` before coding begins. Pull it before you start so everyone works from the same foundation.

```
CSS201-BorneoFresh/
  src/
    borneofresh/
      model/     - All entity classes (User, Product, Order, and subclasses)
      system/    - Controller classes (BorneoFreshSystem, ProductCatalogue, etc.)
      test/      - JUnit test classes
  docs/
  README.md
```

---

## Coding Conventions

The following standards apply to all code in this repository and directly affect the Code Quality marks in Task 2.

- Use `PascalCase` for class names (e.g. `CustomerRegistry`) and `camelCase` for method names and field names (e.g. `placeOrder`, `productId`).
- All fields must be `private`. Access them only through getters and setters.
- Every class and every method must have a Javadoc comment explaining its purpose and functionality. Example:

```java
/**
 * Represents a customer account in the BorneoFresh system.
 * Extends the User abstract class.
 */
public class Customer extends User { ... }
```

- Do not leave commented-out code or debug print statements in your final submission.

---

## Troubleshooting

### Branch does not exist locally

If you see the following error when pushing:

```
error: src refspec feature/memberX does not match any
error: failed to push some refs to '...'
```

This means your local machine does not have the branch created yet, 
even though it exists on GitHub. Fix it by running:

```bash
git checkout -b feature/memberX
git push origin feature/memberX
```

Replace `memberX` with your assigned branch name. From this point 
forward, all commits should be pushed to this branch.

### Commit went to the wrong branch

If you accidentally commit to `main` locally instead of your feature 
branch, do not panic. Run the following to move to your branch and 
carry the commit with you:

```bash
git checkout -b feature/memberX
git push origin feature/memberX
```

Your commit will be included in the push.

## Questions

If you are unsure about any implementation detail, refer to the UML Class Diagram first. If your question is not answered there, message the lecturer or any members before making assumptions. Do not change class names, method signatures, or package structure without group agreement, as this will break everyone else's code.