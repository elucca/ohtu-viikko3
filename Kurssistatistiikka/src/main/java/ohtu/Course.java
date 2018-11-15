package ohtu;

import java.util.ArrayList;
import java.util.List;

public class Course {

    //_id: "5bb48ca56ec4c800e33cb76f",
    //name: "ohtu2018",
    //url: "https://github.com/mluukkai/ohjelmistotuotanto2018/wiki/Ohjelmistotuotanto-syksy-2018",
    //week: 2,
    //enabled: true,
    //term: "syksy",
    //year: 2018,
    //__v: 3,
    //fullName: "Ohjelmistotuotanto",
    //miniproject: true,
    //exercises:
    private String _id;
    private String name;
    private String url;
    private int week;
    private boolean enabled;
    private String term;
    private int year;
    private int __v;
    private String fullName;
    private boolean miniproject;
    private List<Integer> exercises;

    public Course() {
        this.exercises = new ArrayList();
    }

    public int sumOfExercises() {
        int sum = 0;
        for (Integer exercise : exercises) {
            sum += exercise;
        }
        return sum;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getV() {
        return __v;
    }

    public void setV(int __v) {
        this.__v = __v;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isMiniproject() {
        return miniproject;
    }

    public void setMiniproject(boolean miniproject) {
        this.miniproject = miniproject;
    }

    public List getExercises() {
        return exercises;
    }

    public void setExercises(List exercises) {
        this.exercises = exercises;
    }

}
