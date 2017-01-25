package run.makemy.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import run.makemy.domains.workouts.Workout;

/**
 * Created by Ohlaph on 5/8/2016.
 */
public interface WorkoutRepository extends CrudRepository<Workout, Long> {
    Iterable<Workout> findAll(Sort date);
    //empty
}