package com.ctci.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationVariables {

    // Shooter variables
    private String shooterName;
    private String shooterEmail;
    private String shooterCategory;

    // Competition variables
    private String competitionName;
    private String competitionModality;
    private String competitionCategory;
    private LocalDateTime eventDate;
    private String eventLocation;
    private BigDecimal registrationFee;
    private LocalDateTime paymentDeadline;
    private String pixKey;

    // Registration variables
    private String registrationStatus;
    private String paymentStatus;
    private BigDecimal amountPaid;
    private LocalDateTime registrationDate;
    private Integer queuePosition;

    // Award variables
    private Integer position;
    private String positionSuffix; // st, nd, rd, th
    private BigDecimal score;
    private BigDecimal prizeAmount;

    // System variables
    private String clubName;
    private String contactEmail;
    private String contactPhone;
    private String websiteUrl;

    /**
     * Converts this object to a Map for template processing
     */
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("shooterName", shooterName != null ? shooterName : "");
        map.put("shooterEmail", shooterEmail != null ? shooterEmail : "");
        map.put("shooterCategory", shooterCategory != null ? shooterCategory : "");
        map.put("competitionName", competitionName != null ? competitionName : "");
        map.put("competitionModality", competitionModality != null ? competitionModality : "");
        map.put("competitionCategory", competitionCategory != null ? competitionCategory : "");
        map.put("eventDate", eventDate != null ? eventDate.toString() : "");
        map.put("eventLocation", eventLocation != null ? eventLocation : "");
        map.put("registrationFee", registrationFee != null ? registrationFee.toString() : "");
        map.put("paymentDeadline", paymentDeadline != null ? paymentDeadline.toString() : "");
        map.put("pixKey", pixKey != null ? pixKey : "");
        map.put("registrationStatus", registrationStatus != null ? registrationStatus : "");
        map.put("paymentStatus", paymentStatus != null ? paymentStatus : "");
        map.put("amountPaid", amountPaid != null ? amountPaid.toString() : "");
        map.put("position", position != null ? position.toString() : "");
        map.put("positionSuffix", positionSuffix != null ? positionSuffix : "");
        map.put("score", score != null ? score.toString() : "");
        map.put("prizeAmount", prizeAmount != null ? prizeAmount.toString() : "");
        map.put("clubName", clubName != null ? clubName : "");
        map.put("contactEmail", contactEmail != null ? contactEmail : "");
        map.put("contactPhone", contactPhone != null ? contactPhone : "");
        map.put("websiteUrl", websiteUrl != null ? websiteUrl : "");
        return map;
    }

    /**
     * Helper method to get position suffix (1st, 2nd, 3rd, 4th, etc.)
     */
    public static String getPositionSuffix(Integer position) {
        if (position == null) return "";

        int lastDigit = position % 10;
        int lastTwoDigits = position % 100;

        // Handle special cases (11th, 12th, 13th)
        if (lastTwoDigits >= 11 && lastTwoDigits <= 13) {
            return "th";
        }

        return switch (lastDigit) {
            case 1 -> "st";
            case 2 -> "nd";
            case 3 -> "rd";
            default -> "th";
        };
    }
}
