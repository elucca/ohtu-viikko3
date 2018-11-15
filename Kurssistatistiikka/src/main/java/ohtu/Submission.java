package ohtu;

import java.util.ArrayList;
import java.util.List;

public class Submission {

    private int week;
    private int hours;
    private List<Integer> exercises;
    private String course;

    public Submission() {
        this.exercises = new ArrayList<>();
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getWeek() {
        return week;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public List<Integer> getExercises() {
        return exercises;
    }

    public void setExercises(List<Integer> exercises) {
        this.exercises = exercises;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(course + ", viikko " + week + " tehtyjä tehtäviä yhteensä " + exercises.size() + " aikaa kului " + hours + " tehdyt tehtävät: ");
        for (int i = 0; i < exercises.size(); i++) {
            if (i == exercises.size() - 1) {
                s.append(exercises.get(i));
            } else {
                s.append(exercises.get(i) + (", "));
            }
        }

        return s.toString();
    }

}
