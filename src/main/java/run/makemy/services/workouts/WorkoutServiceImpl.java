package run.makemy.services.workouts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.makemy.domains.workouts.Workout;
import run.makemy.repositories.WorkoutRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Component
public class WorkoutServiceImpl implements WorkoutService {

    @Autowired
    protected WorkoutRepository workoutRepository;

    @Override
    public Workout saveWorkout(Workout workout) {
        return workoutRepository.save(workout);
    }

    @Override
    public boolean deleteWorkout(Long workoutId) {
        Workout temp = workoutRepository.findOne(workoutId);
        if (temp!=null) {
            workoutRepository.delete(temp);
            return true;
        }
        return false;
    }

    @Override
    public Workout editWorkout(Workout workout) {
        return workoutRepository.save(workout);
    }

    @Override
    public Workout findWorkout(Long workoutId) {
        return workoutRepository.findOne(workoutId);
    }

    @Override
    public Collection<Workout> getAllWorkoutsOrderByDate() {
        Iterable<Workout> itr = workoutRepository.findAll();
        return (Collection<Workout>) itr;
    }

    @Override
    @Query(value = "SELECT * FROM running_log WHERE author = id ORDER BY date", nativeQuery = true)
    public Collection<Workout> getUserWorkoutsByOrderByDate(@Param("id") Long id) {


        Iterable<Workout> workout = workoutRepository.findAll();
        Collection<Workout> temp = new ArrayList<>();
        for (Workout item: workout) {
            if(item.getAuthor().equals(id)) {
                temp.add(item);
            }
        }

        Comparator<Workout> byPostDate =
                Collections.reverseOrder(Comparator.comparing((java.util.function.Function<Workout, Date>) (workout1) -> {
                    return workout1.getDate();
                }));

         workout = temp
                .stream()
                .sorted(byPostDate)
                .collect(Collectors.toList());

        return (Collection<Workout>) workout;
    }


    @Override
    public Workout create(Workout workout) {

        return workoutRepository.save(workout);
    }



}

