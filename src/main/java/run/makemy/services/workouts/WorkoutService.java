package run.makemy.services.workouts;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import run.makemy.domains.workouts.Workout;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Ohlaph on 5/8/2016.
 */
public interface WorkoutService {

    Workout saveWorkout(Workout workout);

    boolean deleteWorkout(Long workoutId);

    Workout editWorkout(Workout workout);

    Workout findWorkout(Long workoutId);

    Collection<Workout> getAllWorkoutsOrderByDate();

    Collection<Workout> getUserWorkoutsByOrderByDate(@Param("id") Long id);



    Workout create(Workout workout);
}
