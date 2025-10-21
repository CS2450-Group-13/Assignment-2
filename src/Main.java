package src;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;
import src.data.Course;
import src.data.CourseCategory;

public class Main extends Application {

    public static void main(String[] args) {
        Course course1 = new Course(
            "CS", 
            CourseCategory.MAJOR,
            1400,
            "Intro to Programming",
            3 
            );
        List<Course> course1Prerequisites = new ArrayList<>();
        course1Prerequisites.add(course1);
        Course course2 = new Course(
            "CS", 
            CourseCategory.MAJOR_ELECTIVE,
            2450, 
            "UI/UX Design", 
            3, 
            course1Prerequisites
            );
        System.out.println(course2);
        System.out.println(course2.getPrerequisites());
        System.out.println(course2.getCourseCategory());
        // launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        // Implement main stage here, could make some optional files for other "pages" in our nav
        throw new UnsupportedOperationException("Unimplemented method 'start'");
    }
}