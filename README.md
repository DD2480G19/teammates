<p align="center">
    <a href="https://github.com/DD2480G19/teammates/commits/master">
        <img alt="Last Commit" src="https://img.shields.io/github/last-commit/simonsimon006/kth_a2_continuous_integration.svg?style=flat-square&logo=github&logoColor=white">
    </a>
    <a href="https://github.com/DD2480G19/teammates/issues">
        <img alt="Issues" src="https://img.shields.io/github/issues-raw/DD2480G19/teammates.svg?style=flat-square&logo=github&logoColor=white">
    </a>
    <a href="https://github.com/DD2480G19/teammates/pulls">
        <img alt="Pull Requests" src="https://img.shields.io/github/issues-pr-raw/DD2480G19/teammates.svg?style=flat-square&logo=github&logoColor=white">
    </a>
</p>

# Assignment: Code Complexity, Coverage

<img src="https://upload.wikimedia.org/wikipedia/en/thumb/e/e0/KTH_Royal_Institute_of_Technology_logo.svg/1200px-KTH_Royal_Institute_of_Technology_logo.svg.png" alt="KTH Logo" align="left" width="90" height="90" style="vertical-align:middle;margin:0px 20px">

This is the repository for *Assignment: Code Complexity, Coverage* in the course DD2480 Software Engineering Fundamentals at KTH Royal Institute of Technology, Sweden. In this assignment, the subject is to experiment with complexity and coverage metrics. The goals are to get an understanding and appreciation of the benefits and drawbacks of metrics and their tools, and to create new test cases, or to enhance existing tests that improve statement or branch coverage. The tasks are formed on the open-source project [teammates](https://github.com/TEAMMATES/teammates).


## Onboarding experience
<span style="color:orange; font-weight: bold;">
    TODO (/check that this is done):<br><br>
    Did it build and run as documented?<br><br>
    See the assignment for details; if everything works out of the box,
    there is no need to write much here. If the first project(s) you picked
    ended up being unsuitable, you can describe the "onboarding experience"
    for each project, along with reason(s) why you changed to a different one.<br><br>
</span>

The onboarding was pretty straight forward. The project's README included an easily accessible URL to "Setting Up" instructions, and elaborate documentation on an external website. The repository also contained all documentation in a directory: `docs`. The project's dependencies required some of us to downgrade our Java version, however, the instructions to configure the project accordingly were clearly described in the documentation.

The building process was run by first executing `./gradlew createConfigs`, then `gradle build`, which automatically installed the necessary components, without errors. If one wanted to install dependencies for front-end development, the steps for that were also clearly described in the documentation.

Back-end tests were run by executing `./gradlew componentTests`. Thanks to _gradle_ all component tests were run automatically, and when 635 tests had been run the testing was manually stopped. Of all tests, only 7 failed. Examples are failures that occurred due to lack of third-party dependencies (email services), and/or date/time tests that failed because they were run on a Swedish OS. 

In conclusion, the onboarding experience was smooth and we plan to continue with the project.


## Complexity
<p align="center">
    <img src="https://media2.giphy.com/media/WRQBXSCnEFJIuxktnw/giphy.gif">
</p>

<span style="color:orange; font-weight: bold;">TODO (/check that this is done):<br></span>
<span style="color:orange; font-weight: bold;">1. What are your results for ten complex functions? - Did all methods (tools vs. manual count) get the same result? - Are the results clear?
2. Are the functions just complex, or also long?
3. What is the purpose of the functions?
4. Are exceptions taken into account in the given measurements?
5. Is the documentation clear w.r.t. all the possible outcomes?<br></span>

We used `lizard` to inspect the complexity of the repository. The results were collected to google [sheets](https://docs.google.com/spreadsheets/d/1pyIgCouwoHa9f4Q0DBhj_9gXWa7iI7MTI_X0eoj8KbU/edit#gid=1684196533). From the analyzed methods, the following ten methods were deemed sufficiently complex:

|# | Cyclomatic Complexity | LOC | Method                                                                           |
|--|-----------------------|-----|----------------------------------------------------------------------------------|
|1 |                    21 |  40 | BasicFeedbackSubmissionAction::getRecipientSection                               |
|2 |                    19 |  76 | FeedbackSessionsDb::updateFeedbackSession                                        |
|3 |                    19 |  60 | FeedbackResponsesLogic::isFeedbackParticipantNameVisibleToUser                   |
|4 |                    18 |  59 | FeedbackMcqQuestionDetails::validateQuestionDetails                              |
|5 |                    17 |  88 | GetFeedbackSessionLogsAction::execute                                            |
|6 |                    17 |  68 | SessionResultsData::buildSingleResponseForStudent                                |
|7 |                    17 |  52 | FeedbackResponseCommentsLogic::isFeedbackParticipantNameVisibleToUser            |
|8 |                    16 |  82 | FeedbackRankRecipientsResponseDetails::getUpdateOptionsForRankRecipientQuestions |
|9 |                    15 |  50 | SessionResultsData::initForStudent                                               |
|10|                    15 |  41 | FeedbackMsqQuestionDetails::shouldChangesRequireResponseDeletion                 |

Out of these, the following five methods were chosen for manual complexity calculation:


| Cyclomatic Complexity | LOC | Method                                                                           | Manualy Calculate Complexity |
|-----------------------|-----|----------------------------------------------------------------------------------|------------------------------|
|                    17 |  68 | SessionResultsData::buildSingleResponseForStudent                                | Markus: 17, Edvin: 17        |
|                    17 |  52 | FeedbackResponseCommentsLogic::isFeedbackParticipantNameVisibleToUser            |                              |
|                    16 |  82 | FeedbackRankRecipientsResponseDetails::getUpdateOptionsForRankRecipientQuestions | Peter: 16, Linea: 16         |                                                                           
|                    15 |  50 | SessionResultsData::initForStudent                                               |                              |
|                    16 |  82 | FeedbackRankRecipientsResponseDetails::getUpdateOptionsForRankRecipientQuestions |                              |
|                    15 |  50 | SessionResultsData::initForStudent                                               | Edvin: 15, Samuel: 16        |
|                    15 |  41 | FeedbackMsqQuestionDetails::shouldChangesRequireResponseDeletion                 |                              |
|                    15 |  50 | SessionResultsData::initForStudent                                               |                              |
|                    15 |  41 | FeedbackMsqQuestionDetails::shouldChangesRequireResponseDeletion                 | Samuel: 15, Peter: 15        |


## Refactoring
<img src="https://media.tenor.com/eQ8OVVGD5rIAAAAC/refactor.gif">

<span style="color:orange; font-weight: bold;">
    <br>TODO:<br><br>
    Plan for refactoring complex code:<br>
    Estimated impact of refactoring (lower CC, but other drawbacks?).<br>
    Carried out refactoring (optional, P+):<br>
    git diff ...]<br>
</span>

## Coverage
<img src="https://y.yarn.co/3d5ad220-edc8-4601-b220-87e1ad9f5e2c_text.gif">

### Tools
<span style="color:orange; font-weight: bold;">
    TODO:<br><br>
    Document your experience in using a "new"/different coverage tool.
    How well was the tool documented? Was it possible/easy/difficult to
    integrate it with your build environment?
</span>

### Your own coverage tool
<span style="color:orange; font-weight: bold;">
    TODO:<br><br>
    Show a patch (or link to a branch) that shows the instrumented code to gather coverage measurements.<br>
    The patch is probably too long to be copied here, so please add the git command that is used to obtain the patch instead:<br>
    git diff ...<br>
    What kinds of constructs does your tool support, and how accurate is its output?<br><br>
</span>

### Evaluation
<span style="color:orange; font-weight: bold;">TODO:<br></span>
<span style="color:orange; font-weight: bold;">1. How detailed is your coverage measurement?
2. What are the limitations of your own tool?
3. Are the results of your tool consistent with existing coverage tools?<br>
</span>

## Coverage improvement
<img src="https://media.tenor.com/LqG262WEznAAAAAC/that-is-progress-dan-levy.gif">

<span style="color:orange; font-weight: bold;">
    <br>
    TODO:<br><br>
    Show the comments that describe the requirements for the coverage.<br> 
    Report of old coverage: [link]<br> 
    Report of new coverage: [link]<br> 
    Test cases added:<br> 
    git diff ...<br> 
    Number of test cases added: two per team member (P) or at least four (P+).<br><br> 
</span>

## Self-assessment: Way of working
<span style="color:orange; font-weight: bold;">
    TODO:<br><br>
    Current state according to the Essence standard: ...<br>
    Was the self-assessment unanimous? Any doubts about certain items?<br>
    How have you improved so far?<br>
    Where is potential for improvement?
    <br><br> 
</span>

## Overall experience
<span style="color:orange; font-weight: bold;">
    TODO:<br><br>
    What are your main take-aways from this project? What did you learn?
    Is there something special you want to mention here?
    <br><br> 
</span>

<img src="https://media.tenor.com/ADRUFfqOs9kAAAAC/i-learned-so-much-brad-mondo.gif">