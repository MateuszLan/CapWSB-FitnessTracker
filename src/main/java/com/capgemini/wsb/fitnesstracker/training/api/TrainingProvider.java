package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import java.util.Optional;
import java.time.LocalDate;
import java.util.List;

public interface TrainingProvider {

    /**
     * Retrieves a training based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param trainingId id of the training to be searched
     * @return An {@link Optional} containing the located Training, or {@link Optional#empty()} if not found
     */
    Optional<User> getTraining(Long trainingId);
    void deleteTrainingByUserId(Long userId);
    List<Training> findAllTrainings();
    List<Training> findTrainingsByUserId(Long userId);
    List<Training> findCompletedTrainings(LocalDate date);
    List<Training> findTrainingsByActivityType(ActivityType activityType);
    Training createNewTraining(Training training);
    Training updateTraining(Long id, TrainingTO trainingTO);
}
