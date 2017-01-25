package run.makemy.services.workouts;

import org.hsqldb.lib.Collection;
import run.makemy.domains.workouts.Workout;

import java.util.ArrayList;

/**
 * Created by Ohlaph on 8/7/2016.
 */

public class Calculations {
    Iterable<Workout> list = new ArrayList<Workout>();

    public Calculations(ArrayList<Workout> list) {
        this.list = list;
    }

    public double calculateDistance() {
        double totalDistance = 0.0;
        for (Workout item : list) {
            totalDistance += item.getDistance();
        }
        return totalDistance;
    }

    public double calculateAverageRun(int runs) {
        double avgRun = 0;
        double total = calculateDistance();
        avgRun = total / runs;


        return round(avgRun, 2);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    //Build time for total time
    public String buildTime() {
        String stringTime = "";
        int hr = 0;
        int min = 0;
        int sec = 0;
        int mils = 0;

        for (Workout items : list) {
            hr += items.getT_hr();
            min += items.getT_min();
            sec += items.getT_sec();
            mils += items.getT_mils();
        }

        //Build Hour
        if (hr != 0) {
            stringTime = stringTime + hr + ":";
        }

        //Build Minute
        if (min != 0) {
            if (min >= 10) {
                stringTime = stringTime + min + ":";
            }
            else {
                stringTime = stringTime + "0" + min + ":";
            }
        }

        //Build Second
        if (sec != 0) {
            if (sec >= 10) {
                stringTime = stringTime + sec + ".";
            }
            else {
                stringTime = stringTime + "0" + sec + ".";
            }
        }

        //Build Milis
        if (mils != 0) {
            if (mils >= 10) {
                stringTime = stringTime + mils;
            }
            else {
                stringTime = stringTime + "0" + mils ;
            }
        }
        else stringTime = stringTime + "0";

        return stringTime;
    }

    //Below this line calculates paces


}
