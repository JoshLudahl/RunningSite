package run.makemy.services.workouts;

/**
 * Created by Ohlaph on 8/13/2016.
 */
public class WorkoutUtls {

    public String buildTime(int time) {
        String duration = "";
        if (time == 0) {
            return "00";
        }

        if (time >= 10) {
            return duration + time;
        }
        else {
            duration = "0" + time;
        }

        return duration;
    }
}
