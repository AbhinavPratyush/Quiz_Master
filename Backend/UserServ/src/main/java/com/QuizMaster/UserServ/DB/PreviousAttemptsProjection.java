package com.QuizMaster.UserServ.DB;

import java.time.Instant;

public interface PreviousAttemptsProjection {

    Long getAttemptId();

    Integer getScore();

    Boolean getHasSubmitted();

    Instant getStartedAt();

    Integer getTimeLimit();
}