package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingTO;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

// TODO: Provide Impl
@RequiredArgsConstructor
@Service
public class TrainingServiceImpl implements TrainingProvider {
    private final TrainingRepository trainingRepository;
    @Override
    public Optional<User> getTraining(final Long trainingId) {
        throw new UnsupportedOperationException("Not finished yet");
    }
    @Override
    public void deleteTrainingByUserId(Long userId) {
        Optional<Training> first = trainingRepository.findAll().stream()
                .filter(training -> training.getUser().getId().equals(userId))
                .findFirst();
        first.ifPresent(trainingRepository::delete);
    }

    @Override
    public List<Training> findAllTrainings() {
        return trainingRepository.findAll();
    }

    @Override
    public List<Training> findTrainingsByUserId(Long userId) {
        return trainingRepository.findAll()
                .stream()
                .filter(training -> training.getUser().getId().equals(userId))
                .toList();
    }

    @Override
    public List<Training> findCompletedTrainings(LocalDate date) {
        return trainingRepository.findAll()
                .stream()
                .filter(training -> training.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isAfter(date))
                .toList();
    }

    @Override
    public List<Training> findTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findAll()
                .stream()
                .filter(training -> training.getActivityType() == activityType)
                .toList();
    }

    @Override
    public Training createNewTraining(Training training) {
        return trainingRepository.save(training);
    }

    @Override
    public Training updateTraining(Long id, TrainingTO trainingTO) {
        Training training = trainingRepository.findById(id)
                .orElseThrow(() -> new TrainingNotFoundException(id));
        training.setStartTime(trainingTO.getStartTime());
        training.setEndTime(trainingTO.getEndTime());
        training.setActivityType(trainingTO.getActivityType());
        training.setDistance(trainingTO.getDistance());
        training.setAverageSpeed(trainingTO.getAverageSpeed());
        return trainingRepository.save(training);
    }
}
