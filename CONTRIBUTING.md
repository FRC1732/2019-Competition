# READ BEFORE CHANGING CODE

The `integration` branch should never be directly modified. Instead, create a new branch to commit your code to.

## Branches

* `integration` The integration branch is stable, working code.
* `feature/<issue>` The feature branches are work-in-progress code branches.

Branches should be named after what they are working on, e.g., the branch working on the drivetrain should be called `drivetrain`.

When your branch works, and you want to include your code into the final code, make a pull request to pull your code into `testing`. To make a pull request, go onto github, open the repository, and select pull request from along the top. You will need to select `testing` as the branch to pull into, and provide a short description of what you added, and what does and doesn't work. 


## Creating a pull request

The basic process of creating changes to the code to pull into `testing`, and then `master` is as follows

### 1. Creating a branch for your code

Use `Ctrl+Shift+P` to open the command palette, run 'Git: Create Branch...', and enter a name for your branch. You will automatically
be switched to the new branch for editing your code.

### 2. Edit code

Edit some files, add functionality, etc. You need to have something worth adding to the testing and master branches. Don't forget to commit often, and push/pull at the end of every meeting.

### 3. Pull request

On [github.com/FRC1732/2019-Competition](https://github.com/FRC1732/2019-Competition), navigate to your branch on the pull down menu (where it says integration). Once on your branch, click the 'New Pull Request' option next to the branch selection drop down. A page will come up asking for the branch to pull from, and the branch to pull to. You will be pulling from your branch, to `integration`, but your pull request will have to be approved by someone else on the team before it is officially pulled into the testing branch. The area where a description is requested, provide details on what was added, and what testing has been performed.

Include Matthew Pomes and Ryan Guinn as reviewers on every pull request as well as anyone who you discussed the issue with.

Include the term "fixes" followed by the issue number it resolves.
EX: "fixes #43"
