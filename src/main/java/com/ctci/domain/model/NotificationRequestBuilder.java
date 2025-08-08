package com.ctci.domain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestBuilder {

    private Long shooterId;
    private String templateCode;
    private List<NotificationChannel> channels;
    private Map<String, Object> variables;
    private NotificationPriority priority;
    private LocalDateTime sendAt;
    private Long competitionId;
    private Long registrationId;

    public NotificationRequest build(NotificationTemplate template, Shooter shooter) {
        return NotificationRequest.builder()
                .shooter(shooter)
                .template(template)
                .channels(channels != null ? channels : template.getChannelTypes())
                .variables(variables)
                .priority(priority != null ? priority : template.getPriority())
                .sendAt(sendAt != null ? sendAt : LocalDateTime.now())
                .build();
    }
}
