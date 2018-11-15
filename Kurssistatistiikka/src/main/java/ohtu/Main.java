package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import org.apache.http.client.fluent.Request;

public class Main {

    public static void main(String[] args) throws IOException {
        // ÄLÄ laita githubiin omaa opiskelijanumeroasi
        String studentNr = "012345678";
        if (args.length > 0) {
            studentNr = args[0];
        }

        String url = "https://studies.cs.helsinki.fi/courses/students/" + studentNr + "/submissions";
        String bodyText = Request.Get(url).execute().returnContent().asString();
        Gson mapper = new Gson();
        Submission[] subs = mapper.fromJson(bodyText, Submission[].class);

        String courseUrl = "https://studies.cs.helsinki.fi/courses/courseinfo";
        String courseBodyText = Request.Get(courseUrl).execute().returnContent().asString();
        Gson courseMapper = new Gson();
        Course[] courses = courseMapper.fromJson(courseBodyText, Course[].class);

        System.out.println("Opiskelijanumero: " + studentNr);

        for (Course course : courses) {
            System.out.println("");
            System.out.println(" " + course.getFullName() + " " + course.getTerm() + " " + course.getYear());
            System.out.println("");

            int totalExercises = 0;
            int totalHours = 0;
            for (Submission sub : subs) {
                if (course.getName().equals(sub.getCourse())) {
                    System.out.println("  viikko " + sub.getWeek() + ":");
                    System.out.print("   tehtyjä tehtäviä " + sub.getExercises().size() + "/" + course.getExercises().get(sub.getWeek()));
                    System.out.print(" aikaa kului " + sub.getHours());
                    System.out.println(" tehdyt tehtävät " + sub.getExercises().toString());

                    totalExercises += sub.getExercises().size();
                    totalHours += sub.getHours();
                }
            }

            System.out.println("");
            System.out.print("yhteensä: " + totalExercises + "/" + course.sumOfExercises() + " tehtävää " + totalHours + " tuntia");
            
            System.out.println("");
        }

//        for (Submission submission : subs) {
//            System.out.println(" " + submission);
//        }
//
//        int totalExercises = 0;
//        for (Submission sub : subs) {
//            totalExercises += sub.getExercises().size();
//        }
//
//        int totalTime = 0;
//        for (Submission sub : subs) {
//            totalTime += sub.getHours();
//        }
//
//        System.out.println("");
//        System.out.println("Yhteensä: " + totalExercises + " tehtävää " + totalTime + " tuntia");
    }
}
