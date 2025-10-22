package src;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import src.data.Course;
import src.data.CourseCategory;

public class Main extends Application {

    public static void main(String[] args) {
        // Test code
        // Course course1 = new Course(
        //     "CS", 
        //     CourseCategory.MAJOR,
        //     1400,
        //     "Intro to Programming",
        //     3 
        //     );
        // List<Course> course1Prerequisites = new ArrayList<>();
        // course1Prerequisites.add(course1);
        // Course course2 = new Course(
        //     "CS", 
        //     CourseCategory.MAJOR_ELECTIVE,
        //     2450, 
        //     "UI/UX Design", 
        //     3, 
        //     course1Prerequisites
        //     );
        // System.out.println(course2);
        // System.out.println(course2.getPrerequisites());
        // System.out.println(course2.getCourseCategory());
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Info Page
        BorderPane infoPage = new BorderPane();
        VBox header = new VBox();
        HBox navBar = new HBox();
        Label title = new Label("Degree Progress");
        title.setId("app-title");

        header.getChildren().addAll(title, navBar);
        header.setAlignment(Pos.CENTER);

        infoPage.setTop(header);

        Scene scene = new Scene(infoPage, 600, 750);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}