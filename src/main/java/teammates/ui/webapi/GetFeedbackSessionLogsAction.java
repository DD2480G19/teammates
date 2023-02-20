package teammates.ui.webapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

import teammates.common.datatransfer.FeedbackSessionLogEntry;
import teammates.common.datatransfer.attributes.CourseAttributes;
import teammates.common.datatransfer.attributes.FeedbackSessionAttributes;
import teammates.common.datatransfer.attributes.InstructorAttributes;
import teammates.common.datatransfer.attributes.StudentAttributes;
import teammates.common.datatransfer.logs.FeedbackSessionLogType;
import teammates.common.util.Const;
import teammates.common.util.TimeHelper;
import teammates.ui.output.FeedbackSessionLogsData;

/**
 * Action: gets the feedback session logs of feedback sessions of a course.
 */
public class GetFeedbackSessionLogsAction extends Action {
    public static boolean[] branchTaken = new boolean[29];
    @Override
    AuthType getMinAuthLevel() {
        return AuthType.LOGGED_IN;
    }

    @Override
    void checkSpecificAccessControl() throws UnauthorizedAccessException {
        if (!userInfo.isInstructor) {
            throw new UnauthorizedAccessException("Instructor privilege is required to access this resource.");
        }

        String courseId = getNonNullRequestParamValue(Const.ParamsNames.COURSE_ID);
        CourseAttributes courseAttributes = logic.getCourse(courseId);

        if (courseAttributes == null) {
            throw new EntityNotFoundException("Course is not found");
        }

        InstructorAttributes instructor = logic.getInstructorForGoogleId(courseId, userInfo.getId());
        gateKeeper.verifyAccessible(instructor, courseAttributes, Const.InstructorPermissions.CAN_MODIFY_STUDENT);
        gateKeeper.verifyAccessible(instructor, courseAttributes, Const.InstructorPermissions.CAN_MODIFY_SESSION);
        gateKeeper.verifyAccessible(instructor, courseAttributes, Const.InstructorPermissions.CAN_MODIFY_INSTRUCTOR);
    }

    @Override
    public JsonResult execute() {
        String courseId = getNonNullRequestParamValue(Const.ParamsNames.COURSE_ID);
        if (logic.getCourse(courseId) == null) {
            branchTaken[0] = true;
            throw new EntityNotFoundException("Course not found");
        }
        else{
            branchTaken[1] = true;
        }
        String email = getRequestParamValue(Const.ParamsNames.STUDENT_EMAIL);
        if (email != null) {
            branchTaken[2] = true;
            if (logic.getStudentForEmail(courseId, email) == null){
                branchTaken[3] = true;
                throw new EntityNotFoundException("Student not found");
            }
            else{
                branchTaken[4] = true;
            } 
        }
        else{
            branchTaken[5] = true;
        }
        String feedbackSessionName = getRequestParamValue(Const.ParamsNames.FEEDBACK_SESSION_NAME);

        if (feedbackSessionName != null) {
            branchTaken[6] = true;
            if (logic.getFeedbackSession(feedbackSessionName, courseId) == null){
                branchTaken[7] = true;
                throw new EntityNotFoundException("Feedback session not found");
            }
            else{
                branchTaken[8] = true;
            }
        }
        else{
            branchTaken[9] = true;
        }

        String fslTypes = getRequestParamValue(Const.ParamsNames.FEEDBACK_SESSION_LOG_TYPE);
        List<FeedbackSessionLogType> convertedFslTypes = new ArrayList<>();
        if (fslTypes != null) {
            branchTaken[10] = true;
            // Multiple log types are separated by a comma e.g access,submission
            for (String fslType : fslTypes.split(",")) {
                FeedbackSessionLogType convertedFslType = FeedbackSessionLogType.valueOfLabel(fslType);

                if (convertedFslType == null) {
                    branchTaken[11] = true;
                    throw new InvalidHttpParameterException("Invalid log type");
                }
                else{
                    branchTaken[12] = true;
                }

                convertedFslTypes.add(convertedFslType);
            }
        }
        else{
            branchTaken[13] = true;
        }

        String startTimeStr = getNonNullRequestParamValue(Const.ParamsNames.FEEDBACK_SESSION_LOG_STARTTIME);
        String endTimeStr = getNonNullRequestParamValue(Const.ParamsNames.FEEDBACK_SESSION_LOG_ENDTIME);
        long startTime;
        long endTime;
        try {
            startTime = Long.parseLong(startTimeStr);
            endTime = Long.parseLong(endTimeStr);
        } catch (NumberFormatException e) {
            branchTaken[14] = true;
            throw new InvalidHttpParameterException("Invalid start or end time", e);
        }
        // TODO: we might want to impose limits on the time range from startTime to endTime

        if (endTime < startTime) {
            branchTaken[15] = true;
            throw new InvalidHttpParameterException("The end time should be after the start time.");
        }
        else{
            branchTaken[16] = true;
        }

        long earliestSearchTime = TimeHelper.getInstantDaysOffsetBeforeNow(Const.LOGS_RETENTION_PERIOD.toDays())
                .toEpochMilli();
        if (startTime < earliestSearchTime) {
            branchTaken[17] = true;
            throw new InvalidHttpParameterException(
                    "The earliest date you can search for is " + Const.LOGS_RETENTION_PERIOD.toDays() + " days before today."
            );
        }
        else{
            branchTaken[18] = true;
        }

        List<FeedbackSessionLogEntry> fsLogEntries =
                logsProcessor.getFeedbackSessionLogs(courseId, email, startTime, endTime, feedbackSessionName);
        Map<String, StudentAttributes> studentsMap = new HashMap<>();
        Map<String, FeedbackSessionAttributes> sessionsMap = new HashMap<>();
        List<FeedbackSessionAttributes> feedbackSessions = logic.getFeedbackSessionsForCourse(courseId);
        feedbackSessions.forEach(fs -> sessionsMap.put(fs.getFeedbackSessionName(), fs));

        fsLogEntries = fsLogEntries.stream().filter(logEntry -> {
            String logType = logEntry.getFeedbackSessionLogType();
            FeedbackSessionLogType convertedLogType = FeedbackSessionLogType.valueOfLabel(logType);
            if (convertedLogType == null) {
                branchTaken[19] = true;
                // If the feedback session log type retrieved from the log is invalid
                // or not the type being queried, ignore the log
                return false;
            }
            else{
                branchTaken[20] = true;
            }
            if (fslTypes != null){
                branchTaken[21] = true;
                if (!convertedFslTypes.contains(convertedLogType)){
                    branchTaken[22] = true;
                    return false;
                }
                else{
                    branchTaken[23] = true;
                }
            }
            else{
                branchTaken[24] = true;
            }

            if (!studentsMap.containsKey(logEntry.getStudentEmail())) {
                branchTaken[25] = true;
                StudentAttributes student = logic.getStudentForEmail(courseId, logEntry.getStudentEmail());
                if (student == null) {
                    branchTaken[26] = true;
                    // If the student email retrieved from the log is invalid, ignore the log
                    return false;
                }
                else{
                    branchTaken[27] = true;
                }
                studentsMap.put(logEntry.getStudentEmail(), student);
            }
            else{
                branchTaken[28] = true;
            }
            // If the feedback session retrieved from the log is invalid, ignore the log
            return sessionsMap.containsKey(logEntry.getFeedbackSessionName());
        }).collect(Collectors.toList());

        Map<String, List<FeedbackSessionLogEntry>> groupedEntries =
                groupFeedbackSessionLogEntries(fsLogEntries);
        feedbackSessions.forEach(fs -> groupedEntries.putIfAbsent(fs.getFeedbackSessionName(), new ArrayList<>()));

        FeedbackSessionLogsData fslData = new FeedbackSessionLogsData(groupedEntries, studentsMap, sessionsMap);
        return new JsonResult(fslData);
    }

    private Map<String, List<FeedbackSessionLogEntry>> groupFeedbackSessionLogEntries(
            List<FeedbackSessionLogEntry> fsLogEntries) {
        Map<String, List<FeedbackSessionLogEntry>> groupedEntries = new LinkedHashMap<>();
        for (FeedbackSessionLogEntry fsLogEntry : fsLogEntries) {
            String fsName = fsLogEntry.getFeedbackSessionName();
            groupedEntries.computeIfAbsent(fsName, k -> new ArrayList<>()).add(fsLogEntry);
        }
        return groupedEntries;
    }
}
