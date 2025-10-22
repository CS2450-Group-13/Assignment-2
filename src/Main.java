package src;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
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
        // ----------- | NAV BAR | -----------
        // title of app
        Label title = new Label("Degree Progress");
        title.setId("app-title");

        // nav elements
        VBox header = new VBox();
        HBox navBar = new HBox();
        header.getChildren().addAll(title, navBar);
        header.setAlignment(Pos.CENTER);

        Label infoLabel = new Label("Student Info");
        Label genEdLabel = new Label("General Education");
        Label majorCoursesLabel = new Label("Major Courses");
        Label majorElecLabel = new Label("Major Electives");
        Label catalogueLabel = new Label("Course Catalogue");

        navBar.getChildren().addAll(
            infoLabel, 
            genEdLabel, 
            majorCoursesLabel,
            majorElecLabel,
            catalogueLabel
            );

        navBar.setSpacing(90);
        navBar.setAlignment(Pos.CENTER);
        navBar.setPadding(new Insets(10, 0, 0, 0));

        for (Node child: navBar.getChildren()) {
            child.getStyleClass().add("nav-bar-item");
        }

        // ----------- | PAGES | -----------
        StackPane pages = new StackPane();

        //
        
        // ----------- | ROOT | -----------
        // instantiate root node and layout
        BorderPane root = new BorderPane();
        root.setTop(header);
        root.setCenter(pages);

        Scene scene = new Scene(root, 1250, 1000);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}