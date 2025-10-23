package src;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Region;
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
        int studentChoice = 0; // Change student 0-2

        // initialize data
        initializeCourses();
        initializeMajors();
        initializeStudents();

        
        Student currStudent = allStudents.get(0);
        Major currMajor = allMajors.get(0);
        currStudent.setMajor(currMajor);

        if (studentChoice == 1) {
            currStudent = allStudents.get(1);
            currMajor = allMajors.get(1);
            currStudent.setMajor(currMajor);
        } else if (studentChoice == 2) {
            currStudent = allStudents.get(2);
            currMajor = allMajors.get(0);
            currStudent.setMajor(currMajor);
        }


        java.util.Set<Integer> electiveIds = new java.util.HashSet<>(java.util.Arrays.asList(
        3520, 3700, 3800, 4110, 4200, 4210, 4220, 4230, 4250, 4350,
        4440, 4450, 4500, 4600, 4650, 4651, 4680, 4700, 4750, 4810, 4990
        ));

        java.util.List<Course> electiveCourses = allCourses.stream()
        .filter(c -> "CS".equalsIgnoreCase(c.getCourseMajor()))
        .filter(c ->c.getCourseCategory() == CourseCategory.MAJOR_ELECTIVE)
        .collect(java.util.stream.Collectors.toList());

        java.util.Set<Integer> completedCourseIds = new java.util.HashSet<>();

        completedCourseIds.add(1400); 
        completedCourseIds.add(1300); 

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
        Label catalogLabel = new Label("Course Catalog");
        
        navBar.getChildren().addAll(
            infoLabel, 
            genEdLabel, 
            majorCoursesLabel,
            majorElecLabel,
            catalogLabel
            );
            
        navBar.setSpacing(90);
        navBar.setAlignment(Pos.CENTER);
        navBar.setPadding(new Insets(10, 0, 40, 0));
        
        for (Node child : navBar.getChildren()) {
            child.getStyleClass().add("nav-bar-item");
        }

        
        // ----------- | PAGES | -----------
        StackPane pages = new StackPane();
        
        // ## Info Page
        HBox topInfo = new HBox();
        HBox bottomInfo = new HBox();
        VBox infoPage = new VBox(topInfo, bottomInfo);
        
        infoPage.setPadding(new Insets(0, 20, 0, 20));
        infoPage.setSpacing(10);

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
        BorderPane currAcademicObj = new BorderPane();
        Label currAcademicLabel = new Label("Current Academic Objective");
        Label currMajorLabel = new Label(currMajor.toString() + ", " + currStudent.getUndergradStatus());
        Label semStartedLabel = new Label("Started: " + currStudent.getStartingSemester());
        Label gradLabel = new Label("Expected Grad Term: " + currStudent.getExpectedGradSem());
        Label gradStatusLabel = new Label("Graduation Status: " + currStudent.getGradStatus());
        
        currAcademicObj.setTop(currAcademicLabel);
        currAcademicObj.setCenter(new VBox(
            currMajorLabel,
            semStartedLabel,
            gradLabel,
            gradStatusLabel
            ));
            
        currAcademicObj.getStyleClass().add("info-container");
        currAcademicObj.setPrefHeight(180);
        currAcademicObj.setPrefWidth(350);
        currAcademicLabel.setAlignment(Pos.CENTER);
        currAcademicLabel.setMaxWidth(Double.MAX_VALUE);
        currAcademicLabel.getStyleClass().add("info-label");
        
        HBox.setHgrow(currAcademicObj, Priority.ALWAYS);
        
        currMajorLabel.setMaxWidth(Double.MAX_VALUE);
        currMajorLabel.setAlignment(Pos.CENTER);
        currMajorLabel.setPadding(new Insets(8, 0, 0, 0));
        semStartedLabel.setMaxWidth(Double.MAX_VALUE);
        semStartedLabel.setAlignment(Pos.CENTER);
        gradLabel.setMaxWidth(Double.MAX_VALUE);
        gradLabel.setAlignment(Pos.CENTER);
        gradStatusLabel.setMaxWidth(Double.MAX_VALUE);
        gradStatusLabel.setAlignment(Pos.CENTER);
        
        topInfo.getChildren().add(currAcademicObj); 
        
        // - course catalog - 
        BorderPane currCourseCatalog = new BorderPane();
        Label currCourseCatalogLabel = new Label("Course Catalogue");
        Label currCourseCatalogValue = new Label(currStudent.getCourseCatalog());
            
        currCourseCatalog.setTop(currCourseCatalogLabel);
        currCourseCatalog.setCenter(new VBox(
            currCourseCatalogValue
            )); 
            
        currCourseCatalog.getStyleClass().add("info-container");
        currCourseCatalog.setPrefHeight(90);
        currCourseCatalogLabel.setAlignment(Pos.CENTER);
        currCourseCatalogLabel.setMaxWidth(Double.MAX_VALUE);
        currCourseCatalogLabel.getStyleClass().add("info-label");
        
        HBox.setHgrow(currCourseCatalog, Priority.ALWAYS);

        currCourseCatalogValue.setMaxWidth(Double.MAX_VALUE);
        currCourseCatalogValue.setAlignment(Pos.CENTER);
        currCourseCatalogValue.setPadding(new Insets(30, 0, 0, 0));
        
        bottomInfo.getChildren().add(currCourseCatalog);
        
        // - current academic summary -
        BorderPane currAcademicSum = new BorderPane();
        Label currAcademicSumLabel = new Label("Current Academic Summary");
        Label currAcademicStandingLabel = new Label("Academic Standing: " + currStudent.getStanding());
        Label overallGPALabel = new Label("Overall GPA: " + currStudent.getGPA());
        Label cppGPALabel = new Label("CPP GPA: " + currStudent.getGPA());
        
        currAcademicSum.setTop(currAcademicSumLabel);
        currAcademicSum.setCenter(new VBox(
            currAcademicStandingLabel,
            overallGPALabel,
            cppGPALabel
            ));
            
        currAcademicSum.getStyleClass().add("info-container");
        currAcademicSum.setPrefHeight(150);
        currAcademicSum.setPrefWidth(400);
        currAcademicSumLabel.setAlignment(Pos.CENTER);
        currAcademicSumLabel.setMaxWidth(Double.MAX_VALUE);
        currAcademicSumLabel.getStyleClass().add("info-label");
        
        HBox.setHgrow(currAcademicSum, Priority.ALWAYS);
        
        currAcademicStandingLabel.setAlignment(Pos.CENTER);
        currAcademicStandingLabel.setMaxWidth(Double.MAX_VALUE);
        currAcademicStandingLabel.setPadding(new Insets(10, 0, 0, 0));
        overallGPALabel.setAlignment(Pos.CENTER);
        overallGPALabel.setMaxWidth(Double.MAX_VALUE);
        cppGPALabel.setAlignment(Pos.CENTER);
        cppGPALabel.setMaxWidth(Double.MAX_VALUE);
        
        bottomInfo.getChildren().add(currAcademicSum);
        bottomInfo.setSpacing(10);
        
        // Major Courses Page with TreeView 
        VBox majorCoursesPage = new VBox();
        majorCoursesPage.setPadding(new Insets(10, 20, 10, 20));
        majorCoursesPage.setSpacing(8);

        Label majorCoursesHeader = new Label("Major Courses");
        majorCoursesHeader.getStyleClass().add("info-label");
        
        // Create TreeView for expandable course information
        TreeView<String> courseTree = new TreeView<>();
        courseTree.setShowRoot(false);

        TreeItem<String> rootItem = new TreeItem<>("Courses");
        rootItem.setExpanded(true);
        courseTree.setRoot(rootItem);
        

        Map<TreeItem<String>, Course> nodeToCourse = new HashMap<>();
        
       
        if (currMajor != null && currMajor.getCourses() != null) {
            
            Map<Integer, Course> coursesById = new HashMap<>();
            Map<Integer, TreeItem<String>> courseNodes = new HashMap<>();
            
            
            for (Course c : currMajor.getCourses()) {
                String courseDisplay = String.format("%s - %s (%d units)", 
                    c.toString(), c.getCourseCategory().getDisplayName(), c.getUnits());
                TreeItem<String> courseItem = new TreeItem<>(courseDisplay);
                rootItem.getChildren().add(courseItem);
                coursesById.put(c.getCourseId(), c);
                courseNodes.put(c.getCourseId(), courseItem);

                nodeToCourse.put(courseItem, c);
            }
            
            for (Course c : currMajor.getCourses()) {
                TreeItem<String> courseItem = courseNodes.get(c.getCourseId());
                
                if (!c.getPrerequisites().isEmpty()) {
                    TreeItem<String> prereqsItem = new TreeItem<>("Prerequisites:");
                    for (Course prereq : c.getPrerequisites()) {
                        prereqsItem.getChildren().add(new TreeItem<>(prereq.toString()));
                    }
                    courseItem.getChildren().add(prereqsItem);
                }
                
                List<Course> dependentCourses = currMajor.getCourses().stream()
                    .filter(otherCourse -> otherCourse.getPrerequisites().contains(c))
                    .collect(Collectors.toList());
                
                if (!dependentCourses.isEmpty()) {
                    TreeItem<String> dependentsItem = new TreeItem<>("Required for:");
                    for (Course dep : dependentCourses) {
                        dependentsItem.getChildren().add(new TreeItem<>(dep.toString()));
                    }
                    courseItem.getChildren().add(dependentsItem);
                }
            }
        } else {
            rootItem.getChildren().add(new TreeItem<>("No courses available for this major."));
        }
        
        courseTree.setPrefHeight(450);
        courseTree.setStyle("""
            -fx-font-size: 14px;
            -fx-background-color: white;
        """);

        courseTree.setCellFactory(tv -> new TreeCell<String>() {

            private final HBox row = new HBox();
            private final Label textLbl = new Label();
            private final Region spacer = new Region();
            private final Label statusDot = new Label(); 

            {
                HBox.setHgrow(spacer, Priority.ALWAYS);
                row.setAlignment(Pos.CENTER_LEFT);
                row.setSpacing(8);
                row.getChildren().addAll(textLbl, spacer, statusDot);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                setText(null);
                setGraphic(null);
                setStyle("");
                row.setStyle("");
                textLbl.setText("");
                textLbl.setStyle("");
                statusDot.setText("");
                statusDot.setStyle("");

                if (empty || item == null) return;

                textLbl.setText(item);
                TreeItem<String> ti = getTreeItem();
                boolean isTopLevel = (ti != null && ti.getParent() == rootItem);

                if (isTopLevel) {
           
                    Course course = nodeToCourse.get(ti);
                    boolean completed = (course != null && completedCourseIds.contains(course.getCourseId()));

                    textLbl.setStyle("-fx-font-weight: bold;");

                    if (completed) {
                        row.setStyle("-fx-background-color: #e6ffe6; -fx-background-radius: 6;");
                        statusDot.setText(""); 
                    } else {
                        statusDot.setText("■");
                        statusDot.setStyle("-fx-text-fill: #d32f2f; -fx-font-size: 15px;");
                }

                setGraphic(row);
            } else {
                if (item.endsWith(":")) {
                    setStyle("-fx-font-weight: bold;"); 
                }
                setText(item);
            }
        }
    });


/////////////////////////////////////////////////////////////// 
    majorCoursesPage.setSpacing(16);
    
    majorCoursesPage.getChildren().addAll(majorCoursesHeader, courseTree);

    VBox majorElectivesPage = new VBox();
    majorElectivesPage.setPadding(new Insets(10, 20, 10, 20));
    majorElectivesPage.setSpacing(8);

    Label majorElectivesHeader = new Label("Major Electives");
    majorElectivesHeader.getStyleClass().add("info-label");

// TreeView for electives
    TreeView<String> electiveTree = new TreeView<>();
    electiveTree.setShowRoot(false);
    TreeItem<String> electRoot = new TreeItem<>("Electives");
    electiveTree.setRoot(electRoot);


    Map<Integer, Course> electById = new HashMap<>();
    Map<Integer, TreeItem<String>> electNodes = new HashMap<>();
    Map<TreeItem<String>, Course> electNodeToCourse = new HashMap<>();

    if (!electiveCourses.isEmpty()) {
    
        for (Course c : electiveCourses) {
            String label = String.format("%s - %s (%d units)",
                c.toString(), c.getCourseCategory().getDisplayName(), c.getUnits());
            TreeItem<String> item = new TreeItem<>(label);
            electRoot.getChildren().add(item);
            electById.put(c.getCourseId(), c);
            electNodes.put(c.getCourseId(), item);
            electNodeToCourse.put(item, c);
        }

        for (Course c : electiveCourses) {
            TreeItem<String> parent = electNodes.get(c.getCourseId());

            if (!c.getPrerequisites().isEmpty()) {
                TreeItem<String> prereqs = new TreeItem<>("Prerequisites:");
                for (Course p : c.getPrerequisites()) {
                    prereqs.getChildren().add(new TreeItem<>(p.toString()));
                }
                parent.getChildren().add(prereqs);
            }

            List<Course> dependents = electiveCourses.stream()
                .filter(other -> other.getPrerequisites().contains(c))
                .collect(Collectors.toList());
            if (!dependents.isEmpty()) {
                TreeItem<String> deps = new TreeItem<>("Required for:");
                for (Course d : dependents) {
                    deps.getChildren().add(new TreeItem<>(d.toString()));
                }
                parent.getChildren().add(deps);
            }
        }
    }
     else {
        electRoot.getChildren().add(new TreeItem<>("No electives found."));
    }

    electiveTree.setPrefHeight(450);
    electiveTree.setStyle("""
    -fx-font-size: 14px;
    -fx-background-color: white;
    """);

// ===== Cell factory: same status UI as Major Courses =====
    electiveTree.setCellFactory(tv -> new TreeCell<String>() {

    // Row: [text] ---spacer---> [statusSquare]
    private final HBox row = new HBox();
    private final Label textLbl = new Label();
    private final Region spacer = new Region();
    private final Region statusSquare = new Region(); 

    {
        HBox.setHgrow(spacer, Priority.ALWAYS);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setSpacing(8);
        statusSquare.setMinSize(12, 12);
        statusSquare.setPrefSize(12, 12);
        statusSquare.setMaxSize(12, 12);
        row.getChildren().addAll(textLbl, spacer, statusSquare);
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        setText(null);
        setGraphic(null);
        setStyle("");
        row.setStyle("");
        textLbl.setText("");
        textLbl.setStyle("");
        statusSquare.setStyle("-fx-background-color: transparent;");

        if (empty || item == null) return;

        textLbl.setText(item);
        TreeItem<String> ti = getTreeItem();
        boolean isTopLevel = (ti != null && ti.getParent() == electRoot);

        if (isTopLevel) {
            Course course = electNodeToCourse.get(ti);
            boolean completed = (course != null && completedCourseIds.contains(course.getCourseId()));

            textLbl.setStyle("-fx-font-weight: bold;");

            if (completed) {
                row.setStyle("-fx-background-color: #e6ffe6; -fx-background-radius: 6;");
                statusSquare.setStyle("-fx-background-color: transparent;");
            } else {
                statusSquare.setStyle("-fx-background-color: #d32f2f; -fx-background-radius: 2;");
            }
            setGraphic(row);
        } else {
            if (item.endsWith(":")) setStyle("-fx-font-weight: bold;");
            setText(item);
        }
    }
});

majorElectivesPage.setSpacing(16);
majorElectivesPage.getChildren().addAll(majorElectivesHeader, electiveTree);
pages.getChildren().addAll(infoPage, majorCoursesPage,majorElectivesPage);



////////////////////////////////////////////////////////
 

        // ----------- | ROOT | -----------
        // instantiate root node and layout
        BorderPane root = new BorderPane();
        root.setTop(header);
        root.setCenter(pages);
        
        // ----------- | Tab Switching Logic | -------------

        // default page
        infoPage.setVisible(true); 

        majorCoursesPage.setVisible(false);
        majorElectivesPage.setVisible(false);

        infoLabel.setOnMouseClicked(e -> {
            infoPage.setVisible(true);
            majorCoursesPage.setVisible(false);
            majorElectivesPage.setVisible(false);
        });
        genEdLabel.setOnMouseClicked(e -> {
            infoPage.setVisible(false);
            majorCoursesPage.setVisible(false);
            majorElectivesPage.setVisible(false);
        });
        majorCoursesLabel.setOnMouseClicked(e -> {
            infoPage.setVisible(false);
            majorCoursesPage.setVisible(true);
            majorElectivesPage.setVisible(false);
        });
        majorElecLabel.setOnMouseClicked(e -> {
            infoPage.setVisible(false);
            majorCoursesPage.setVisible(false);
            majorElectivesPage.setVisible(true);
        });
        catalogLabel.setOnMouseClicked(e -> {
            infoPage.setVisible(false); 
            majorCoursesPage.setVisible(false);
            majorElectivesPage.setVisible(false);
        });
            
        // Instantiate the Scene
        Scene scene = new Scene(root, 1250, 600);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    // TO-DO
    private void initializeCourses() {
        // CS Core Courses
        Course CS1300 = new Course(
            "CS",
            CourseCategory.MAJOR,
            1300,
            "Discrete Structures",
            3
        );

        Course CS1400 = new Course(
            "CS",
            CourseCategory.MAJOR,
            1400,
            "Introduction to Programming and Problem Solving",
            4
        );

        Course CS2400 = new Course(
            "CS",
            CourseCategory.MAJOR,
            2400,
            "Data Structures and Advanced Programming",
            4
        );
        CS2400.addPrerequisite(CS1400);
        CS2400.addPrerequisite(CS1300);

        Course CS2600 = new Course(
            "CS",
            CourseCategory.MAJOR,
            2600,
            "Systems Programming",
            3
        );
        CS2600.addPrerequisite(CS1400);

        Course CS2610 = new Course(
            "CS",
            CourseCategory.MAJOR,
            2610,
            "Introduction to Cyber Security and Network Communications",
            3
        );
        CS2610.addPrerequisite(CS2600);

        Course CS2640 = new Course(
            "CS",
            CourseCategory.MAJOR,
            2640,
            "Computer Organization and Assembly Programming",
            3
        );
        CS2640.addPrerequisite(CS1300);
        CS2640.addPrerequisite(CS1400);

        Course CS3010 = new Course(
            "CS",
            CourseCategory.MAJOR,
            3010,
            "Numerical Methods and Computing",
            3
        );
        CS3010.addPrerequisite(CS2400);

        Course CS3110 = new Course(
            "CS",
            CourseCategory.MAJOR,
            3110,
            "Formal Languages and Automata",
            3
        );
        CS3110.addPrerequisite(CS2400);


        Course CS3310 = new Course(
            "CS",
            CourseCategory.MAJOR,
            3310,
            "Design and Analysis of Algorithms",
            3
        );
        CS3310.addPrerequisite(CS2400);

        Course CS3560 = new Course(
            "CS",
            CourseCategory.MAJOR,
            3560,
            "Object-Oriented Design and Programming",
            3
        );
        CS3560.addPrerequisite(CS2400);

        Course CS3650 = new Course(
            "CS",
            CourseCategory.MAJOR,
            3650,
            "Computer Architecture",
            4
        );
        CS3650.addPrerequisite(CS2640);

        Course CS3750W = new Course(
            "CS",
            CourseCategory.MAJOR,
            3750,
            "Computers and Society",
            3
        );
        
        Course CS4080 = new Course(
            "CS",
            CourseCategory.MAJOR,
            4080,
            "Concepts of Programming Languages",
            3
        );
        CS4080.addPrerequisite(CS3110);
        CS4080.addPrerequisite(CS2640);

        Course CS4310 = new Course(
            "CS",
            CourseCategory.MAJOR,
            4310,
            "Operating Systems",
            3
        );
        CS4310.addPrerequisite(CS3650);

        Course CS4630 = new Course(
            "CS",
            CourseCategory.MAJOR,
            4630,
            "Undergraduate Seminar",
            1
        );

        Course CS4800 = new Course(
            "CS",
            CourseCategory.MAJOR,
            4800,
            "Software Engineering",
            3
        );
        CS4800.addPrerequisite(CS2400);

        // Math Core Courses
        Course MAT1140 = new Course(
            "MAT",
            CourseCategory.MAJOR,
            1140,
            "Calculus I",
            4
        );

        Course MAT1150 = new Course(
            "MAT",
            CourseCategory.MAJOR,
            1150,
            "Calculus II",
            4
        );
        MAT1150.addPrerequisite(MAT1140);

        Course STA2260 = new Course(
            "STA",
            CourseCategory.MAJOR,
            2260,
            "Probability and Statistics for Computer Scientists and Engineers",
            3
        );
        STA2260.addPrerequisite(MAT1140);

        //Elective Courses
        Course CS3520 = new Course(
            "CS",
            CourseCategory.MAJOR_ELECTIVE,
            3520,
            "Database Systems",
            3
        );
        CS3520.addPrerequisite(CS2400);

        Course CS3700 = new Course(
            "CS",
            CourseCategory.MAJOR_ELECTIVE,
            3700,
            "Parallel Processing",
            3
        );
        CS3700.addPrerequisite(CS3110);

        Course CS3800 = new Course(
            "CS",
            CourseCategory.MAJOR_ELECTIVE,
            3800,
            "Computer Networks",
            3
        );
        CS3800.addPrerequisite(CS2400);
        CS3800.addPrerequisite(CS2640);

        Course CS4110 = new Course(
            "CS",
            CourseCategory.MAJOR_ELECTIVE,
            4110,
            "Compilers and Interpreters",
            3
        );
        CS4110.addPrerequisite(CS3110);

        Course CS4200 = new Course(
            "CS",
            CourseCategory.MAJOR_ELECTIVE,
            4200,
            "Artificial Intelligence",
            3
        );
        CS4200.addPrerequisite(CS2400);

        Course CS4210 = new Course(
            "CS",
            CourseCategory.MAJOR_ELECTIVE,
            4210,
            "Machine Learning and Its Applications",
            3
        );
        CS4210.addPrerequisite(CS3010);

        Course CS4220 = new Course(
            "CS",
            CourseCategory.MAJOR_ELECTIVE,
            4220,
            "Computer Processing Unit Computing",
            3
        );
        CS4220.addPrerequisite(CS2600);
        CS4220.addPrerequisite(CS2640);

        Course CS4230 = new Course(
            "CS",
            CourseCategory.MAJOR_ELECTIVE,
            4230,
            "Social Computing",
            3
        );
        CS4230.addPrerequisite(CS2400);

        Course CS4250 = new Course(
            "CS",
            CourseCategory.MAJOR_ELECTIVE,
            4250,
            "Web Search and Recommender Systems",
            3
        );
        CS4250.addPrerequisite(CS2400);
        

        Course CS4350 = new Course(
            "CS",
            CourseCategory.MAJOR_ELECTIVE,
            4350,
            "Database Systems",
            3
        );
        CS4350.addPrerequisite(CS2400);

        Course CS4440 = new Course(
            "CS",
            CourseCategory.MAJOR_ELECTIVE,
            4440,
            "Data Mining",
            3
        );
        CS4440.addPrerequisite(CS2400);

        Course CS4450 = new Course(
            "CS",
            CourseCategory.MAJOR_ELECTIVE,
            4450,
            "Computer Graphics",
            3
        );
        CS4450.addPrerequisite(CS2400);
        

        Course CS4500 = new Course(
            "CS",
            CourseCategory.MAJOR_ELECTIVE,
            4500,
            "Computability",
            3
        );
        CS4500.addPrerequisite(CS3110);

        Course CS4600 = new Course(
            "CS",
            CourseCategory.MAJOR_ELECTIVE,
            4600,
            "Cryptography and Information Security",
            3
        );
        CS4600.addPrerequisite(CS2610);

        Course CS4650 = new Course(
            "CS",
            CourseCategory.MAJOR_ELECTIVE,
            4650,
            "Big Data Analytics and Cloud Computing",
            3
        );
        CS4650.addPrerequisite(CS2400);

        Course CS4651 = new Course(
            "CS",
            CourseCategory.MAJOR_ELECTIVE,
            4651,
            "Cloud Computing",
            3
        );
        CS4651.addPrerequisite(CS2400);

        Course CS4680 = new Course(
            "CS",
            CourseCategory.MAJOR_ELECTIVE,
            4680,
            "Prompt Engineering",
            3
        );
        CS4680.addPrerequisite(CS2400);

        Course CS4700 = new Course(
            "CS",
            CourseCategory.MAJOR_ELECTIVE,
            4700,
            "Game Development",
            3
        );
        CS4700.addPrerequisite(CS2400);

        Course CS4750 = new Course(
            "CS",
            CourseCategory.MAJOR_ELECTIVE,
            4750,
            "Mobile Application Development",
            3
        );
        CS4750.addPrerequisite(CS2400);

        Course CS4810 = new Course(
            "CS",
            CourseCategory.MAJOR_ELECTIVE,
            4810,
            "Software Engineering Practice",
            3
        );
        CS4810.addPrerequisite(CS4350);
        CS4810.addPrerequisite(CS4800);

        Course CS4990 = new Course(
            "CS",
            CourseCategory.MAJOR_ELECTIVE,
            4990,
            "Special Topics for Upper Division Students",
            1
        );
        // All courses
        // major courses
        allCourses.add(CS1300);
        allCourses.add(CS1400);
        allCourses.add(CS2400);
        allCourses.add(CS2600);
        allCourses.add(CS2610);
        allCourses.add(CS2640);
        allCourses.add(CS3010);
        allCourses.add(CS3110);
        allCourses.add(CS3310);
        allCourses.add(CS3560);
        allCourses.add(CS3650);
        allCourses.add(CS3750W);
        allCourses.add(CS4080);
        allCourses.add(CS4310);
        allCourses.add(CS4630);
        allCourses.add(CS4800);

        // Math Courses
        allCourses.add(MAT1140);
        allCourses.add(MAT1150);
        allCourses.add(STA2260);

        // elective courses
        allCourses.add(CS3520);
        allCourses.add(CS3700);
        allCourses.add(CS3800);
        allCourses.add(CS4110);
        allCourses.add(CS4200);
        allCourses.add(CS4210);
        allCourses.add(CS4220);
        allCourses.add(CS4230);
        allCourses.add(CS4250);
        allCourses.add(CS4350);
        allCourses.add(CS4440);
        allCourses.add(CS4450);
        allCourses.add(CS4500);
        allCourses.add(CS4600);
        allCourses.add(CS4650);
        allCourses.add(CS4651);
        allCourses.add(CS4680);
        allCourses.add(CS4700);
        allCourses.add(CS4750);
        allCourses.add(CS4810);
        allCourses.add(CS4990);
    }

    // TO-DO
    private void initializeMajors() {
        Major CS = new Major("Computer Science");
        Major ME = new Major("Mechanical Engineering");

        for (Course c : allCourses) {
            String m = c.getCourseMajor();
            if ("CS".equalsIgnoreCase(c.getCourseMajor()) ||
                "MAT".equalsIgnoreCase(c.getCourseMajor()) ||
                "STA".equalsIgnoreCase(c.getCourseMajor())){
                CS.addCourse(c); 
                }
            }

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
            true,
            2.5
        );

        Student Bryce = new Student(
            "Bryce Canyon",
            888000888,
            "Fall Semester 2023",
            "Spring Semester 2027",
            true,
            false,
            3.9
        );

        Student Yelena = new Student(
            "Yelena Belova",
            607000607,
            "Fall Semester 2024",
            "Spring Semester 2026",
            false,
            false,
            1.8
        );

        allStudents.add(Alice);
        allStudents.add(Bryce);
        allStudents.add(Yelena);
    }

    public static void main(String[] args) {
        launch(args);
    }
}