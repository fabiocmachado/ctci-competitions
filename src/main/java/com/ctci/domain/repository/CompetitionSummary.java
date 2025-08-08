package com.ctci.domain.repository;

import com.ctci.domain.model.CompetitionModality;
import com.ctci.domain.model.CompetitionStatus;

import java.time.LocalDateTime;

public interface CompetitionSummary {
    Long getId();
    String getName();
    CompetitionModality getModality();
    LocalDateTime getEventDate();
    CompetitionStatus getStatus();
    Integer getCurrentParticipants();
    Integer getMaxParticipants();
}