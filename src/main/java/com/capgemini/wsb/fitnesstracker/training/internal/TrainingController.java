package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingTO;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
class TrainingController {

    private final TrainingServiceImpl trainingService;
    private final TrainingMapper trainingMapper;

    @GetMapping
    public List<TrainingTO> getAllTrainings() {
        return trainingService.findAllTrainings()
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @GetMapping("/{userId}")
    public List<TrainingTO> getTrainingsByUser(@PathVariable Long userId) {
        return trainingService.findTrainingsByUserId(userId)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @GetMapping("/finished/{afterTime}")
    public List<TrainingTO> getCompletedTrainings(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate afterTime) {
        return trainingService.findCompletedTrainings(afterTime)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @GetMapping("/activityType")
    public List<TrainingTO> getTrainingsByActivity(@RequestParam ActivityType activityType) {
        return trainingService.findTrainingsByActivityType(activityType)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrainingTO addTraining(@RequestBody TrainingTO trainingTO) {
        Training training = trainingMapper.toEntity(trainingTO);
        Training createdTraining = trainingService.createNewTraining(training);
        return trainingMapper.toDto(createdTraining);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TrainingTO updateTraining(@PathVariable Long id, @RequestBody TrainingTO trainingTO) {
        return trainingMapper.toDto(trainingService.updateTraining(id, trainingTO));
    }
}