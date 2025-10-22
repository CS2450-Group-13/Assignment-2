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
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import src.data.Course;
import src.data.CourseCategory;
import src.data.Major;
import src.data.Student;

public class Main extends Application {

    private final List<Course> allCourses = new ArrayList<>();
    private final List<Major> allMajors = new ArrayList<>();
    private final List<Student> allStudents = new ArrayList<>();

    
    @Override
    public void start(Stage stage) throws Exception {
        int studentChoice = 2; // Change student 0-2

        // initialize data
        initializeCourses();
        initializeMajors();
        initializeStudents();

        
        Student currStudent = allStudents.get(0);
        Major currMajor = allMajors.get(0);

        if (studentChoice == 1) {
            currStudent = allStudents.get(1);
            currMajor = allMajors.get(1);
        } else if (studentChoice == 2) {
            currStudent = allStudents.get(2);
            currMajor = allMajors.get(0);
        }

        currStudent.setMajor(currMajor);

        // ******************** UI FUNCTIONALITY ********************

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
        navBar.setPadding(new Insets(10, 0, 40, 0));
        
        for (Node child : navBar.getChildren()) {
            child.getStyleClass().add("nav-bar-item");
        }

        int i = 0;
        // CONFIGURE
        for (Node child : navBar.getChildren()) {
            child.setOnMouseClicked(e -> {
                System.out.println("Testing: CLICKED!");
            });
            i++;
        }
            
        // ----------- | PAGES | -----------
        StackPane pages = new StackPane();
        
        // ## Info Page
        HBox topInfo = new HBox();
        HBox bottomInfo = new HBox();
        VBox infoPage = new VBox(topInfo, bottomInfo);

        // sections
        // - student info -
        String currStudentName = currStudent.getName();
        String currStudentId = Integer.toString(currStudent.getId());

        Label studentName = new Label(currStudentName);
        Label studentId = new Label("ID: " + currStudentId);
        VBox studentInfo = new VBox(studentName, studentId);
        topInfo.getChildren().add(studentInfo);
        
        studentInfo.setAlignment(Pos.CENTER);
        studentInfo.setId("student-info");
        topInfo.setAlignment(Pos.CENTER);
        HBox.setHgrow(studentInfo, Priority.ALWAYS);

        // - current academic objective -
        Major currStudentMajor = currStudent.getMajor();
        BorderPane currentAcademicObj = new BorderPane();
        Label currAcademicLabel = new Label("Current Academic Objective");
        Label currMajorLabel = new Label(currStudentMajor.toString() + ", " + currStudent.getUndergradStatus());
        Label semStartedLabel = new Label("Started: " + currStudent.getStartingSemester());
        Label gradLabel = new Label("Expected Grad Term: " + currStudent.getExpectedGradSem());
        Label gradStatus = new Label("Graduation Status: " + currStudent.getGradStatus());

        currentAcademicObj.setTop(currAcademicLabel);
        currentAcademicObj.setCenter(new VBox(
            currMajorLabel,
            semStartedLabel,
            gradLabel,
            gradStatus
        ));
 
        currentAcademicObj.getStyleClass().add("info-container");
        currentAcademicObj.setPrefHeight(180);
        currentAcademicObj.setPrefWidth(400);
        currAcademicLabel.setAlignment(Pos.CENTER);
        currAcademicLabel.setMaxWidth(Double.MAX_VALUE);
        currAcademicLabel.getStyleClass().add("info-label");

        topInfo.getChildren().add(currentAcademicObj);
        HBox.setHgrow(currentAcademicObj, Priority.ALWAYS);

        currMajorLabel.setMaxWidth(Double.MAX_VALUE);
        currMajorLabel.setAlignment(Pos.CENTER);
        currMajorLabel.setPadding(new Insets(8, 0, 0, 0));
        semStartedLabel.setMaxWidth(Double.MAX_VALUE);
        semStartedLabel.setAlignment(Pos.CENTER);
        gradLabel.setMaxWidth(Double.MAX_VALUE);
        gradLabel.setAlignment(Pos.CENTER);
        gradStatus.setMaxWidth(Double.MAX_VALUE);
        gradStatus.setAlignment(Pos.CENTER);

        // - current academic summary -

        // - course catalog - 

        infoPage.setPadding(new Insets(0, 20, 0, 20));

        // add pages to their parent node
        pages.getChildren().addAll(infoPage);
        
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

    // TO-DO
    private void initializeCourses() {
        Course CS1400 = new Course(
            "CS",
            CourseCategory.MAJOR,
            1400,
            "Intro to Programming",
            3
        );

        Course CS2400 = new Course(
            "CS",
            CourseCategory.MAJOR,
            2400,
            "Data Structures and Algorithms",
            4
        );
        CS2400.addPrerequisite(CS1400);

        Course CS2450 = new Course(
            "CS",
            CourseCategory.MAJOR_ELECTIVE,
            2450,
            "UI/UX Design",
            3
        );
        CS2450.addPrerequisite(CS1400);

        allCourses.add(CS1400);
        allCourses.add(CS2400);
        allCourses.add(CS2450);
    }

    // TO-DO
    private void initializeMajors() {
        Major CS = new Major("Computer Science");
        Major ME = new Major("Mechanical Engineering");

        allMajors.add(CS);
        allMajors.add(ME);
    }

    // TO-DO
    private void initializeStudents() {
        Student Alice = new Student(
            "Alice Eve",
            999000999,
            "Fall Semester 2022",
            "Spring Semester 2026",
            true,
            true
        );

        Student Bryce = new Student(
            "Bryce Canyon",
            888000888,
            "Fall Semester 2023",
            "Spring Semester 2027",
            true,
            false
        );

        Student Yelena = new Student(
            "Yelena Belova",
            607000607,
            "Fall Semester 2024",
            "Spring Semester 2026",
            false,
            false
        );

        allStudents.add(Alice);
        allStudents.add(Bryce);
        allStudents.add(Yelena);
    }

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
}