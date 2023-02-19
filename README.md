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

>**TODO (/check that this is done):**
Did it build and run as documented?
See the assignment for details; if everything works out of the box,
there is no need to write much here. If the first project(s) you picked
ended up being unsuitable, you can describe the "onboarding experience"
for each project, along with reason(s) why you changed to a different one.


The onboarding was pretty straight forward. The project's README included an easily accessible URL to "Setting Up" instructions, and elaborate documentation on an external website. The repository also contained all documentation in a directory: `docs`. The project's dependencies required some of us to downgrade our Java version, however, the instructions to configure the project accordingly were clearly described in the documentation.

The building process was run by first executing `./gradlew createConfigs`, then `gradle build`, which automatically installed the necessary components, without errors. If one wanted to install dependencies for front-end development, the steps for that were also clearly described in the documentation.

Back-end tests were run by executing `./gradlew componentTests`. Thanks to _gradle_ all component tests were run automatically, and when 635 tests had been run the testing was manually stopped. Of all tests, only 7 failed. Examples are failures that occurred due to lack of third-party dependencies (email services), and/or date/time tests that failed because they were run on a Swedish OS. 

In conclusion, the onboarding experience was smooth and we plan to continue with the project.


## Complexity
<p align="center">
    <img src="https://media2.giphy.com/media/WRQBXSCnEFJIuxktnw/giphy.gif">
</p>

>**TODO (/check that this is done):**
>1. What are your results for ten complex functions
>    - Did all methods (tools vs. manual count) get the same result?
>    - Are the results clear?
>2. Are the functions just complex, or also long?
>3. What is the purpose of the functions?
>4. Are exceptions taken into account in the given *measurements?
>5. Is the documentation clear w.r.t. all the possible outcomes?

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

### Manual complexity calculations
Out of these, the following five methods were chosen for manual complexity calculation:


| Cyclomatic Complexity | LOC | Method                                                                           | Manually Calculated Complexity |
|-----------------------|-----|----------------------------------------------------------------------------------|--------------------------------|
|                    17 |  68 | SessionResultsData::buildSingleResponseForStudent                                | Markus: 17, Edvin: 17          |
|                    17 |  52 | FeedbackResponseCommentsLogic::isFeedbackParticipantNameVisibleToUser            | Markus: 17, Linnéa: 17         |
|                    16 |  82 | FeedbackRankRecipientsResponseDetails::getUpdateOptionsForRankRecipientQuestions | Peter: 16, Linnéa: 16          |
|                    15 |  50 | SessionResultsData::initForStudent                                               |                                |
|                    16 |  82 | FeedbackRankRecipientsResponseDetails::getUpdateOptionsForRankRecipientQuestions |                                |
|                    15 |  50 | SessionResultsData::initForStudent                                               | Edvin: 15, Samuel: 16          |
|                    15 |  41 | FeedbackMsqQuestionDetails::shouldChangesRequireResponseDeletion                 |                                |
|                    15 |  50 | SessionResultsData::initForStudent                                               |                                |
|                    15 |  41 | FeedbackMsqQuestionDetails::shouldChangesRequireResponseDeletion                 | Samuel: 15, Peter: 15          |


### Questions answered for the 10 selected high complexity functions
1. What are your results? Did everyone get the same result? Is there something that is unclear? If you have a tool, is its result the same as yours?
2. Are the functions/methods with high CC also very long in terms of LOC?
3. What is the purpose of these functions? Is it related to the high CC? 
4. If your programming language uses exceptions: 
   - Are they taken into account by the tool? 
   - If you think of an exception as another possible branch (to the catch block or the end of the function), how is the CC affected? 
5. Is the documentation of the function clear about the different possible outcomes induced by different branches taken?

#### Function 1: `BasicFeedbackSubmissionAction::getRecipientSection`
1. This function's complexity was not calculated by hand.
2. The number of LOC is not significantly high.
3. The purpose of this function is to get a section of a recipient of a feedback action. Depending on the value of `giverType`, i.e. the type of user that is giving feedback, and the value of `recipientType`, the outcome of the function differs. Because there are many different combinations of these values, the complexity is high.
4. There are no exceptions. However, the function includes assertions that seem to be included in the count performed by `lizard`.
5. The documentation in the code does not contain any information of the outcomes.


#### Function 2: `FeedbackSessionsDb::updateFeedbackSession`
1. This function's complexity was not calculated by hand.
2. The number of LOC is quite high in relation to the CC, but not surprisingly high.
3. The purpose of the function is to update a feedback session, and the high complexity is a result of multiple values that are being checked if they have changed and should be updated.
4. The function throws exceptions, and these do not seem to be included as exit points. The functions do not include try/catch blocks.
5. There is no explicit documentation of the different possible outcomes of the branches, however, the code is pretty self-explanatory.


#### Function 3: `FeedbackResponsesLogic::isFeedbackParticipantNameVisibleToUser`
1. This function's complexity was not one of the functions calculated by hand.
2. The function has 60 LOC which I consider to be too long. There is a lot of exit points in the function that reduces the CC. The functions almost exclusively consists of if, else, case, switch, return, break.
3. As given in the name, it’s a function that checks if the participant receiving the feedback is visible to the user.  Without have used the software, I assume there is a high complexity if things should be visible or not because of privacy and integrity.
4. There are no exceptions. However, the function includes assertions that seem to be included in the count performed by lizard.
5. There is none documentation of the function and the different outcomes.

#### Function 4: `FeedbackMcqQuestionDetails::validateQuestionDetails`
1. This function's complexity was not one of the functions calculated by hand.
2. This function has 59 LOC, but most of it is comments. It’s a fairly short function with a lot of if-statements.
3. The purpose of the function is to check that every question is entered correctly. Since there are many options that need to be checked, the complexity will be high. However, the branching is very shallow, so the function is not experienced as complex.
4. There are no exceptions or assertions in the function.
5. This function is well documented in its different paths. Almost all if-statements has a comment explaining the branching.

#### Function 5: `GetFeedbackSessionLogsAction::execute`
1. lizard got a CCN of 17. This method's complexity was not calculated by hand.
2. The method has 71 LOC, which is related to the high CC. 
3. The purpose of the method is to get feedback session logs/json result data. There is much to be included/taken into account (e.g., when information isn't found), which relates to the high CC.
4. The method uses many exception throws, but these does not seem to be counted as exit points.
5. There is some documentation of the different possible outcomes of the branches, but only in certain places. It would be of benefit if it was clearer, especially since the code is a bit messy.

#### Function 6: `SessionResultsData::buildSingleResponseForStudent`
1.
2.
3.
4.
5.


#### Function 7: `FeedbackResponseCommentsLogic::isFeedbackParticipantNameVisibleToUser`
1. Both of the people that worked on it got the same result: CC=17. We used lizard to calculate the CC of the function, and same result was shown. 
2. The method has 52 LOC, which is not that bad as most of the lines come for the switch statement. It has 17 CC, which is not that bad considered to what the function does. The high CC is governed by the possible choices in the switch statement.
3. As it's name states, the function evaluated if the feedback participant name is visible to the user. It is indeed related to the high CC as the return value depends on the type of the participant, and there is at least 6 types from what I can see in the code.
4. There is no exception handling in the method.
5. The function is not at all documented, but, after reading the code, one can pretty much understand at a high level the possible outcomes induced by different branches in the switch statement.


#### Function 8: `FeedbackRankRecipientsResponseDetails::getUpdateOptionsForRankRecipientQuestions`
1. Both of the people that worked on it got the same result: CC=16. We used lizard to calculate the CC of the function, and same result was shown. 
2. This function has 82 LOC, which is fairly large for one function. I beilieve it would make sens to try to divide the method into submethods, as the function seems to do multiple steps(4 detected). Given that there's only 3 possible exit points, the function has a high CC of 16.
3. The purpose of the function is to provide updates of responses for 'rank recipient question', such that the ranks in the responses are consistent. It's natural to have a high CC as the function provdes the updates which seem to be immutable, hence a builder has been implemented
4. The are no try catch blocks, however one assertion is made, which seems to be taken into account.
5. The documentation is fairly clear about possible outcomes induced. However, condition statements are poorly commented, but the condition itself can be understood with the names of the variables used.

#### Function 9: `SessionResultsData::initForStudent`
1.  lizard calculated a CCN of 15. For the manual calculation, however, one of us got 15 and the other 16. 
    The reason for the discrepancy was *(at least between the manual calculations)* a call to `Map::computeIfAbsent` 
    which executes a lambda expression if a specific entry is not present in the map. It was not fully clear how to 
    count this since it technicaly is a single function call which always executes but if we also count the code in `Collections::forEach`-calls, 
    it's not unreasonable to count these calls as a branch as well.

2.  The method is 50 lines of code and quite sparse as well. *(Counting only lines with code present, it's more like 31 lines)*

3.  The purpose of the method is to collect responses of various kinds into a common class to *(probably)* send via an API to students. 
    The high CC comes mostly from the fact that the methods needs to identify the type of responses it's parsing. 
    These calculations could probably be refactored into separate methods to reduce the CC and make the function a little easier to understand. 

4.  The method does not throw any exceptions.

5.  The documentation of the method simply states: "Factory method to construct API output for student.", which gives little information about the possible branches. 
    However when looking at what the method returns, you can see that it constructs a list of `QuestionOuput` objects which provides further details on what the method     returns. With this in mind, the code *(and branches)* is relatively self explanitory. 

#### Function 10: `FeedbackMsqQuestionDetails::shouldChangesRequireResponseDeletion`
1.  lizard got a CCN of 15, which was consistent with the manual calculations of both group memmbers.

2.  The method is only 41 lines of code by lizard's calculations. If not counting empty lines and comments it's more like 23 lines. 
    Either way it's a very short method considering the high CC.

3.  The purpose of the method is to determine whether changes to a question would require the program to delete the coresponding responses.
    With this purpose in mind, it makes sence to have a high CC since the method essentially just checks a bunch of conditionals.

4.  The code does not throw any exceptions.

5.  The documentation and code itself is very clear about the possible outcomes since it only returns either `true` or `false` 
    based on if we need to delete responses. There is some documentation in the form of commetns explaining the different branch-conditions in text.
    The branches with no explanatory comments are relatively self explanatory but could probably benefit from some comments. 




## Refactoring
<img src="https://media.tenor.com/eQ8OVVGD5rIAAAAC/refactor.gif">

>**TODO:**
>Plan for refactoring complex code:
Estimated impact of refactoring (lower CC, but other drawbacks?).
Carried out refactoring (optional, P+):
git diff ...

## Coverage
<img src="https://y.yarn.co/3d5ad220-edc8-4601-b220-87e1ad9f5e2c_text.gif">

### Tools

>**TODO:**
>Document your experience in using a "new"/different coverage tool.
How well was the tool documented? Was it possible/easy/difficult to
integrate it with your build environment?

### Your own coverage tool

>**TODO:**
>Show a patch (or link to a branch) that shows the instrumented code to gather coverage measurements.
The patch is probably too long to be copied here, so please add the git command that is used to obtain the patch instead:
git diff ...
What kinds of constructs does your tool support, and how accurate is its output?

| # | Patch
|---|------------------------------------------------------------------------------------------------------------------------------------------|
| 1 | **Function**: `SessionResultsData::buildSingleResponseForStudent` <br> **Branch:** [manual instrumentation, function 1](https://github.com/DD2480G19/teammates/tree/37-manual-instrumentation-function-1) <br> Git command that is used to obtain the patch (from master): `git diff 37-manual-instrumentation-function-1` |
| 2 |  |
| 3 |  |
| 4 |  |
| 5 |  |

### Evaluation

>**TODO:**
>1. How detailed is your coverage measurement?
>2. What are the limitations of your own tool?
>3. Are the results of your tool consistent with existing coverage tools?

| # | Evaluation
|---|------------------------------------------------------------------------------------------------------------------------------------------|
| 1 | **Function**: `SessionResultsData::buildSingleResponseForStudent` <br><br> The tool takes advantage of _lazy evaluation_ for compound conditions (conjunctions) and detects overlapping branches (e.g. first term in SessionResultsData.java:90 and :91). Because of this, the number of branches differs from the results of the existing tool `Jacoco`. However, the coverage of the two tools is almost the same: 44% vs. Jacoco's 50%. Regarding limitations, if more tests were to be added to the project, the calls for outputting the coverage results would have to be placed at the end of the last tests, to make sure that every unit test is included. There are no ternary operators or exceptions, hence, the tool does not consider this.                   |
| 2 |  |
| 3 |  |
| 4 |  |
| 5 |  |

## Coverage improvement
<img src="https://media.tenor.com/LqG262WEznAAAAAC/that-is-progress-dan-levy.gif">

>**TODO:**
>Show the comments that describe the requirements for the coverage.
Report of old coverage: [link]
Report of new coverage: [link]
Test cases added:
git diff ...
Number of test cases added: two per team member (P) or at least four (P+).

## Self-assessment: Way of working

>**TODO:**
>Current state according to the Essence standard: ...
Was the self-assessment unanimous? Any doubts about certain items?
How have you improved so far?
Where is potential for improvement?

## Overall experience

>**TODO:**
>What are your main take-aways from this project? What did you learn?
Is there something special you want to mention here?

<img src="https://media.tenor.com/ADRUFfqOs9kAAAAC/i-learned-so-much-brad-mondo.gif">
