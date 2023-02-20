package teammates.common.datatransfer.questions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import teammates.common.datatransfer.attributes.FeedbackResponseAttributes;
import teammates.common.util.Const;

/**
 * Contains specific structure and processing logic for rank recipients feedback responses.
 */
public class FeedbackRankRecipientsResponseDetails extends FeedbackResponseDetails {
    private int answer;

    public static boolean[] reachedBranches = new boolean[32];  // For branch coverage

    public FeedbackRankRecipientsResponseDetails() {
        super(FeedbackQuestionType.RANK_RECIPIENTS);
        answer = Const.POINTS_NOT_SUBMITTED;
    }

    /**
     * Provides updates of responses for 'rank recipient question', such that the ranks in the responses are consistent.
     * (Branch coverage added through storing booleans in reachedBranches.)
     * @param responses responses to one feedback question, from one giver
     * @param maxRank the maximum rank in each response
     * @return a list of {@code UpdateOptions} that contains the updates for the responses, if any
     */
    public static List<FeedbackResponseAttributes.UpdateOptions> getUpdateOptionsForRankRecipientQuestions(
            List<FeedbackResponseAttributes> responses, int maxRank) {
        List<FeedbackResponseAttributes.UpdateOptions> updateOptions = new ArrayList<>();
        
        // ------------------ Added for branch coverage ----------------
        // ---- reachedBranches[i] = true means branch i is reached ----
        if (maxRank <= 0) {
            reachedBranches[0] = true;  // (maxRank <= 0) == true
            return updateOptions;
        }
        reachedBranches[1] = true;  // (maxRank <= 0) == false

        FeedbackResponseDetails details;
        FeedbackRankRecipientsResponseDetails responseDetails;
        boolean[] isRankUsed;
        Set<FeedbackResponseAttributes> updatedResponses = new HashSet<>();
        boolean isUpdateNeeded = false;
        int answer;
        int maxUnusedRank = 0;

        // Checks whether update is needed.
        for (FeedbackResponseAttributes response : responses) {
            reachedBranches[2] = true;  // Enters for loop
            details = response.getResponseDetails();
            if (!(details instanceof FeedbackRankRecipientsResponseDetails)) {
                reachedBranches[4] = true;  // (!(details [...])) == true
                continue;
            }
            reachedBranches[5] = true;  // (!(details [...])) == false
            responseDetails = (FeedbackRankRecipientsResponseDetails) details;
            answer = responseDetails.getAnswer();
            if (answer > maxRank) {
                reachedBranches[6] = true;  // (answer > maxRank) == true
                isUpdateNeeded = true;
                break;
            }
            reachedBranches[7] = true;  // (answer > maxRank) == false
        }
        if (reachedBranches[2] == true) reachedBranches[3] = true;  // Exits for loop

        // Updates repeatedly, until all responses are consistent.
        while (isUpdateNeeded) {
            reachedBranches[8] = true;  // Enters while loop
            isUpdateNeeded = false; // will be set to true again once invalid rank appears after update
            isRankUsed = new boolean[maxRank];

            // Obtains the largest unused rank.
            for (FeedbackResponseAttributes response : responses) {
                reachedBranches[10] = true;  // Enters for loop
                details = response.getResponseDetails();
                if (!(details instanceof FeedbackRankRecipientsResponseDetails)) {
                    reachedBranches[12] = true;  // (!(details [...])) == true
                    continue;
                }
                reachedBranches[13] = true;  // (!(details [...])) == false
                responseDetails = (FeedbackRankRecipientsResponseDetails) details;
                answer = responseDetails.getAnswer();
                if (answer <= maxRank) {
                    reachedBranches[14] = true;  // (answer <= maxRank)) == true
                    isRankUsed[answer - 1] = true;
                }
                else reachedBranches[15] = true;  // (answer <= maxRank)) == false
            }
            if (reachedBranches[10] == true) reachedBranches[11] = true;  // Exits for loop
            for (int i = maxRank - 1; i >= 0; i--) {
                reachedBranches[16] = true;  // Enters for loop
                if (!isRankUsed[i]) {
                    reachedBranches[18] = true;  // (!isRankUsed[i]) == true
                    maxUnusedRank = i + 1;
                    break;
                }
                reachedBranches[19] = true;  // (!isRankUsed[i]) == false
            }
            if (reachedBranches[16] == true) reachedBranches[17] = true;  // Exits for loop

            reachedBranches[21] = true;  // Assertion false (throw)
            assert maxUnusedRank > 0; // if update is needed, there must be at least one unused rank
            reachedBranches[20] = true; reachedBranches[21] = false;  // Assertion true

            for (FeedbackResponseAttributes response : responses) {
                reachedBranches[22] = true;  // Enters for loop
                details = response.getResponseDetails();
                if (details instanceof FeedbackRankRecipientsResponseDetails) {
                    reachedBranches[24] = true;  // (details [...]) == true
                    responseDetails = (FeedbackRankRecipientsResponseDetails) details;
                    answer = responseDetails.getAnswer();
                    if (answer > maxUnusedRank) {
                        reachedBranches[26] = true;  // (answer > maxUnusedRank) == true
                        answer--;
                        responseDetails.setAnswer(answer);
                        updatedResponses.add(response);
                    }
                    else reachedBranches[27] = true;  // (answer > maxUnusedRank) == false
                    if (answer > maxRank) {
                        reachedBranches[28] = true;  // (answer > maxRank) == true
                        isUpdateNeeded = true; // sets the flag to true if the updated rank is still invalid
                    }
                    else reachedBranches[29] = true;  // (answer > maxRank) == false
                }
                else reachedBranches[25] = true;  // (details [...]) == false
            }
            if (reachedBranches[22] == true) reachedBranches[23] = true;  // Exits for loop

            // Adds the updated responses to the result list.
            FeedbackResponseAttributes.UpdateOptions updateOption;
            for (FeedbackResponseAttributes response : updatedResponses) {
                reachedBranches[30] = true;  // Enters for loop
                updateOption = FeedbackResponseAttributes.updateOptionsBuilder(response.getId())
                        .withFeedbackResponseDetails(response.getResponseDetails())
                        .build();
                updateOptions.add(updateOption);
            }
            if (reachedBranches[30] == true) reachedBranches[31] = true;  // Exits for loop
        }
        if (reachedBranches[8] == true) reachedBranches[9] = true;  // Exits while loop
        return updateOptions;
        // -------------------------------------------------------
    }

    @Override
    public String getAnswerString() {
        return Integer.toString(answer);
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
