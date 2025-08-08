package com.ctci.domain.repository;

import com.ctci.domain.model.ShooterCategory;

public interface ShooterNotificationInfo {
    Long getId();
    String getName();
    String getEmail();
    String getPushToken();
    ShooterCategory getCategory();
}
