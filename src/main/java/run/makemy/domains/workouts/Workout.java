package run.makemy.domains.workouts;

import org.springframework.format.annotation.DateTimeFormat;
import run.makemy.services.workouts.WorkoutUtls;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Ohlaph on 5/8/2016.
 */
@Entity
@Table(name = "running_log")
public class Workout implements Comparable<Workout> {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "distance", nullable = false)
    private double distance;

    @Column(name = "name")
    private String name;

    @Column(name = "t_mils")
    private int t_mils;

    @Column(name = "t_sec")
    private int t_sec;

    @Column(name = "t_min")
    private int t_min;

    @Column(name = "t_hr")
    private int t_hr;

    @Column(name = "pace")
    private String pace;

    @Column(name = "notes")
    private String notes;

    @Column(name = "author")
    private Long author;


    @Column(name = "date")

    private Date date;




    //Getters and setters


    public int getT_mils() {
        return t_mils;
    }

    public void setT_mils(int t_mils) {
        this.t_mils = t_mils;
    }

    public int getT_sec() {
        return t_sec;
    }

    public void setT_sec(int t_sec) {
        this.t_sec = t_sec;
    }

    public int getT_min() {
        return t_min;
    }

    public void setT_min(int t_min) {
        this.t_min = t_min;
    }

    public int getT_hr() {
        return t_hr;
    }

    public void setT_hr(int t_hr) {
        this.t_hr = t_hr;
    }

    public Date getDate() {

        return date;

    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPace() {
        return pace;
    }

    public void setPace(String pace) {
        this.pace = pace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(Workout o) {
        if (getDate() == null || o.getDate() == null)
            return 0;
        return getDate().compareTo(o.getDate());
    }


    public String printDuration() {
        WorkoutUtls wkutls = new WorkoutUtls();
        String runDuration = "";

        //Build Hour
        if (t_hr != 0) {
            runDuration = runDuration + t_hr + ":";
        }

        //Build Minute
        runDuration = runDuration + wkutls.buildTime(t_min) + ":";

        //Build Second
        runDuration = runDuration + wkutls.buildTime(t_sec)  + ".";

        //Build Milis
        runDuration = runDuration + wkutls.buildTime(t_mils);

        //Return the duration
        return runDuration;
    }
}


