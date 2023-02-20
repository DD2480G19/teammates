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
    /** For branch coverage. */
    public static final boolean[] REACHED_BRANCHES = new boolean[32];
    private int answer;

    public FeedbackRankRecipientsResponseDetails() {
        super(FeedbackQuestionType.RANK_RECIPIENTS);
        answer = Const.POINTS_NOT_SUBMITTED;
    }

    /**
     * Provides updates of responses for 'rank recipient question', such that the ranks in the responses are consistent.
     * (Branch coverage added through storing booleans in REACHED_BRANCHES.)
     * @param responses responses to one feedback question, from one giver
     * @param maxRank the maximum rank in each response
     * @return a list of {@code UpdateOptions} that contains the updates for the responses, if any
     */
    public static List<FeedbackResponseAttributes.UpdateOptions> getUpdateOptionsForRankRecipientQuestions(
            List<FeedbackResponseAttributes> responses, int maxRank) {
        List<FeedbackResponseAttributes.UpdateOptions> updateOptions = new ArrayList<>();
        // ------------------ Added for branch coverage ----------------
        // ---- REACHED_BRANCHES[i] = true means branch i is reached ----
        if (maxRank <= 0) {
            REACHED_BRANCHES[0] = true; // (maxRank <= 0) == true
            return updateOptions;
        }
        REACHED_BRANCHES[1] = true; // (maxRank <= 0) == false

        FeedbackResponseDetails details;
        FeedbackRankRecipientsResponseDetails responseDetails;
        boolean[] isRankUsed;
        Set<FeedbackResponseAttributes> updatedResponses = new HashSet<>();
        boolean isUpdateNeeded = false;
        int answer;
        int maxUnusedRank = 0;

        // Checks whether update is needed.
        for (FeedbackResponseAttributes response : responses) {
            REACHED_BRANCHES[2] = true; // Enters for loop
            details = response.getResponseDetails();
            if (!(details instanceof FeedbackRankRecipientsResponseDetails)) {
                REACHED_BRANCHES[4] = true; // (!(details [...])) == true
                continue;
            }
            REACHED_BRANCHES[5] = true; // (!(details [...])) == false
            responseDetails = (FeedbackRankRecipientsResponseDetails) details;
            answer = responseDetails.getAnswer();
            if (answer > maxRank) {
                REACHED_BRANCHES[6] = true; // (answer > maxRank) == true
                isUpdateNeeded = true;
                break;
            }
            REACHED_BRANCHES[7] = true; // (answer > maxRank) == false
        }
        if (REACHED_BRANCHES[2]) {
            REACHED_BRANCHES[3] = true; // Exits for loop
        }

        // Updates repeatedly, until all responses are consistent.
        while (isUpdateNeeded) {
            REACHED_BRANCHES[8] = true; // Enters while loop
            isUpdateNeeded = false; // will be set to true again once invalid rank appears after update
            isRankUsed = new boolean[maxRank];

            // Obtains the largest unused rank.
            for (FeedbackResponseAttributes response : responses) {
                REACHED_BRANCHES[10] = true; // Enters for loop
                details = response.getResponseDetails();
                if (!(details instanceof FeedbackRankRecipientsResponseDetails)) {
                    REACHED_BRANCHES[12] = true; // (!(details [...])) == true
                    continue;
                }
                REACHED_BRANCHES[13] = true; // (!(details [...])) == false
                responseDetails = (FeedbackRankRecipientsResponseDetails) details;
                answer = responseDetails.getAnswer();
                if (answer <= maxRank) {
                    REACHED_BRANCHES[14] = true; // (answer <= maxRank)) == true
                    isRankUsed[answer - 1] = true;
                } else {
                    REACHED_BRANCHES[15] = true; // (answer <= maxRank)) == false
                }
            }
            if (REACHED_BRANCHES[10]) {
                REACHED_BRANCHES[11] = true; // Exits for loop
            }
            for (int i = maxRank - 1; i >= 0; i--) {
                REACHED_BRANCHES[16] = true; // Enters for loop
                if (!isRankUsed[i]) {
                    REACHED_BRANCHES[18] = true; // (!isRankUsed[i]) == true
                    maxUnusedRank = i + 1;
                    break;
                }
                REACHED_BRANCHES[19] = true; // (!isRankUsed[i]) == false
            }
            if (REACHED_BRANCHES[16]) {
                REACHED_BRANCHES[17] = true; // Exits for loop
            }

            REACHED_BRANCHES[21] = true; // Assertion false (throw)
            assert maxUnusedRank > 0; // if update is needed, there must be at least one unused rank
            REACHED_BRANCHES[20] = true; // Assertion true
            REACHED_BRANCHES[21] = false; // Assertion true

            for (FeedbackResponseAttributes response : responses) {
                REACHED_BRANCHES[22] = true; // Enters for loop
                details = response.getResponseDetails();
                if (details instanceof FeedbackRankRecipientsResponseDetails) {
                    REACHED_BRANCHES[24] = true; // (details [...]) == true
                    responseDetails = (FeedbackRankRecipientsResponseDetails) details;
                    answer = responseDetails.getAnswer();
                    if (answer > maxUnusedRank) {
                        REACHED_BRANCHES[26] = true; // (answer > maxUnusedRank) == true
                        answer--;
                        responseDetails.setAnswer(answer);
                        updatedResponses.add(response);
                    } else {
                        REACHED_BRANCHES[27] = true; // (answer > maxUnusedRank) == false
                    }
                    if (answer > maxRank) {
                        REACHED_BRANCHES[28] = true; // (answer > maxRank) == true
                        isUpdateNeeded = true; // sets the flag to true if the updated rank is still invalid
                    } else {
                        REACHED_BRANCHES[29] = true; // (answer > maxRank) == false
                    }
                } else {
                    REACHED_BRANCHES[25] = true; // (details [...]) == false
                }
            }
            if (REACHED_BRANCHES[22]) {
                REACHED_BRANCHES[23] = true; // Exits for loop
            }

            // Adds the updated responses to the result list.
            FeedbackResponseAttributes.UpdateOptions updateOption;
            for (FeedbackResponseAttributes response : updatedResponses) {
                REACHED_BRANCHES[30] = true; // Enters for loop
                updateOption = FeedbackResponseAttributes.updateOptionsBuilder(response.getId())
                        .withFeedbackResponseDetails(response.getResponseDetails())
                        .build();
                updateOptions.add(updateOption);
            }
            if (REACHED_BRANCHES[30]) {
                REACHED_BRANCHES[31] = true; // Exits for loop
            }
        }
        if (REACHED_BRANCHES[8]) {
            REACHED_BRANCHES[9] = true; // Exits while loop
        }
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
