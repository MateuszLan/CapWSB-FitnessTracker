package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.*;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor()
@AllArgsConstructor
public class TrainingTO {
    private Long id;
    private Long userId;
    private User user;
    private Date startTime;
    private Date endTime;
    private ActivityType activityType;
    private double distance;
    private double averageSpeed;
}
