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
    
    private final List<Major> allMajors = new ArrayList<>();
    private final List<Student> allStudents = new ArrayList<>();
    
    private final List<Course> areaA_GE = new ArrayList<>();
    private final List<Course> areaB_GE = new ArrayList<>();
    private final List<Course> areaC_GE = new ArrayList<>();
    private final List<Course> areaD_GE = new ArrayList<>();
    private final List<Course> areaE_GE = new ArrayList<>();
    
    private final List<Course> allCsCourses = new ArrayList<>();
    private final List<Course> allCsElectives = new ArrayList<>();

    @Override
    public void start(Stage stage) throws Exception {
        int studentChoice = 0; // Change student 0-2

        // initialize data
        initializeGECourses();
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

        java.util.List<Course> electiveCourses = currMajor.getElectives();
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
        Label catalogLabel = new Label("GE Course Catalog");
        
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
        int currMajorUnits = currMajor.getTotalUnits();
        int currStudentCompletedUnits = currStudent.getCompletedUnits();
        
        Label studentName = new Label(currStudentName);
        Label studentId = new Label("ID: " + currStudentId);
        Label completedUnits = new Label("Completed Units: " + currStudentCompletedUnits);
        Label requiredUnits = new Label("Required: " + currMajorUnits);
        Label remainingUnits = new Label("Left: " + (currMajorUnits - currStudentCompletedUnits));
        VBox studentInfo = new VBox(
            studentName, 
            studentId,
            completedUnits,
            requiredUnits,
            remainingUnits
        );
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
        currCourseCatalog.setCenter(new VBox(currCourseCatalogValue)); 
            
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

        // ## General Education Page
        Label genEdInfoLabel = new Label("General Education Requirements: 48 units");
        genEdInfoLabel.getStyleClass().add("requirements-label");
        genEdInfoLabel.setPadding(new Insets(10, 0, 15, 30));

        Label areaAInfoLabel = new Label("Area A. English Language Communication and Critical Thinking (9 units)");
        Label areaBInfoLabel = new Label("Area B. Scientific Inquiry and Quantitative Reasoning (12 units)");
        Label areaCInfoLabel = new Label("Area C. Arts and Humanities (12 units)");
        Label areaDInfoLabel = new Label("Area D. Social Sciences (9 units)");
        Label areaEInfoLabel = new Label("Area E. Lifelong Learning and Self-Development (3 units)");

        VBox genEdInfoPage = new VBox(
            genEdInfoLabel,
            areaAInfoLabel,
            new VBox(
                new Label("At least 3 units from each sub-area") {{
                    getStyleClass().add("italic-text");
                }},
                new Label("1. Oral Communication"),
                new Label("2. Written Communication"),
                new Label("3. Critical Thinking")
            ) {{
                setPadding(new Insets(10, 0, 15, 50));
            }},
            areaBInfoLabel,
            new VBox(
                new Label("At least 3 units from each sub-area") {{
                    getStyleClass().add("italic-text");
                }},
                new Label("1. Physical Sciences"),
                new Label("2. Life Sciences"),
                new Label("3. Mathematics/Quantitative Reasoning")
            ) {{
                setPadding(new Insets(10, 0, 15, 50));
            }},
            areaCInfoLabel,
            new VBox(
                new Label("At least 3 units from each sub-area and 3 additional units from sub-areas 1 and/or 2") {{
                    getStyleClass().add("italic-text");
                }},
                new Label("1. Visual and Performing Arts"),
                new Label("2. Literature, Modern Languages, Philosophy, and Civilization")
            ) {{
                setPadding(new Insets(10, 0, 15, 50));
            }},
            areaDInfoLabel,
            new VBox(
                new Label("At least 3 units from each sub-area") {{
                    getStyleClass().add("italic-text");
                }},
                new Label("1. U.S. History and American Ideals"),
                new Label("2. U.S. Constitution and California Government"),
                new Label("3. Social Sciences: Principles, Methodologies, Value Systems, and Ethics")
            ) {{
                setPadding(new Insets(10, 0, 15, 50));
            }},
            areaEInfoLabel, 
            new Label("See course catalog page") {{
                setPadding(new Insets(0, 0, 10, 50));
            }}
        );

        areaAInfoLabel.getStyleClass().add("gen-ed-label");
        areaBInfoLabel.getStyleClass().add("gen-ed-label");
        areaCInfoLabel.getStyleClass().add("gen-ed-label");
        areaDInfoLabel.getStyleClass().add("gen-ed-label");
        areaEInfoLabel.getStyleClass().add("gen-ed-label");

        areaAInfoLabel.setPadding(new Insets(10, 0, 15, 50));
        areaBInfoLabel.setPadding(new Insets(10, 0, 15, 50));
        areaCInfoLabel.setPadding(new Insets(10, 0, 15, 50));
        areaDInfoLabel.setPadding(new Insets(10, 0, 15, 50));
        areaEInfoLabel.setPadding(new Insets(10, 0, 15, 50));

        // ## Major Courses Page
        VBox majorCoursesPage = new VBox();
        majorCoursesPage.setPadding(new Insets(10, 20, 10, 20));
        majorCoursesPage.setSpacing(8);

        Label majorCoursesHeader = new Label("Major Required: " + currMajor.getMajorRequiredUnits() + " units");
        majorCoursesHeader.getStyleClass().add("requirements-label");
        
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
        
        courseTree.setPrefHeight(700);
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

        // ##Major Electives Page
        VBox majorElectivesPage = new VBox();
        majorElectivesPage.setPadding(new Insets(10, 20, 10, 20));
        majorElectivesPage.setSpacing(8);

        Label majorElectivesHeader = new Label("Major Electives: 17 Units");
        Label electivesGroup1 = new Label(" At least 11 units from:");
        Label electivesGroup2 = new Label(" No more than 6 units from:");
        majorElectivesHeader.getStyleClass().add("requirements-label");

        electivesGroup1.getStyleClass().add("elective-group");
        electivesGroup2.getStyleClass().add("elective-group");

        // TreeView for electives group 1
        TreeView<String> electiveTree = new TreeView<>();
        electiveTree.setShowRoot(false);
        TreeItem<String> electRoot = new TreeItem<>("Electives");
        electiveTree.setRoot(electRoot);


        Map<Integer, Course> electById = new HashMap<>();
        Map<Integer, TreeItem<String>> electNodes = new HashMap<>();
        Map<TreeItem<String>, Course> electNodeToCourse = new HashMap<>();

        if (!electiveCourses.isEmpty()) {
    
            for (Course c : electiveCourses) {
                if (c.getGroup() == 2) 
                    continue;

                String label = String.format("%s - %s (%d units)",
                    c.toString(), c.getCourseCategory().getDisplayName(), c.getUnits());
                TreeItem<String> item = new TreeItem<>(label);
                electRoot.getChildren().add(item);
                electById.put(c.getCourseId(), c);
                electNodes.put(c.getCourseId(), item);
                electNodeToCourse.put(item, c);
            }

            for (Course c : electiveCourses) {
                if (c.getGroup() == 2)
                    continue;

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
        } else {
            electRoot.getChildren().add(new TreeItem<>("No electives found."));
        }

        electiveTree.setPrefHeight(635);
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

        // TreeView for electives group 2
        TreeView<String> electiveTree2 = new TreeView<>();
        electiveTree2.setShowRoot(false);
        TreeItem<String> electRoot2 = new TreeItem<>("Electives");
        electiveTree2.setRoot(electRoot2);


        Map<Integer, Course> electById2 = new HashMap<>();
        Map<Integer, TreeItem<String>> electNodes2 = new HashMap<>();
        Map<TreeItem<String>, Course> electNodeToCourse2 = new HashMap<>();

        if (!electiveCourses.isEmpty()) {

            for (Course c : electiveCourses) {
                if (c.getGroup() != 2) 
                    continue;

                String label = String.format("%s - %s (%d units)",
                    c.toString(), c.getCourseCategory().getDisplayName(), c.getUnits());
                TreeItem<String> item = new TreeItem<>(label);
                electRoot2.getChildren().add(item);
                electById2.put(c.getCourseId(), c);
                electNodes2.put(c.getCourseId(), item);
                electNodeToCourse2.put(item, c);
            }

            for (Course c : electiveCourses) {
                if (c.getGroup() != 2)
                    continue;

                TreeItem<String> parent = electNodes2.get(c.getCourseId());

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
        } else {
            electRoot2.getChildren().add(new TreeItem<>("No electives found."));
        }

        electiveTree2.setPrefHeight(475);
        electiveTree2.setStyle("""
        -fx-font-size: 14px;
        -fx-background-color: white;
        """);

        // ===== Cell factory 2: same status UI as Major Courses =====
        electiveTree2.setCellFactory(tv -> new TreeCell<String>() {

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
            boolean isTopLevel = (ti != null && ti.getParent() == electRoot2);

                if (isTopLevel) {
                    Course course = electNodeToCourse2.get(ti);
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
        majorElectivesPage.getChildren().addAll(
            majorElectivesHeader, 
            electivesGroup1, 
            electiveTree,
            electivesGroup2, 
            electiveTree2
        );
    
        pages.getChildren().addAll(
            infoPage, 
            genEdInfoPage, 
            majorCoursesPage, 
            majorElectivesPage
        );


    ////////////////////////////////////////////////////////
 

        // ----------- | ROOT | -----------
        // instantiate root node and layout
        BorderPane root = new BorderPane();
        root.setTop(header);
        root.setCenter(pages);
        
        // ----------- | Tab Switching Logic | -------------

        // default page
        infoPage.setVisible(true);
        genEdInfoPage.setVisible(false);
        majorCoursesPage.setVisible(false);
        majorElectivesPage.setVisible(false);

        infoLabel.getStyleClass().add("nav-item-clicked");

        infoLabel.setOnMouseClicked(e -> {
            infoPage.setVisible(true);
            genEdInfoPage.setVisible(false);
            majorCoursesPage.setVisible(false);
            majorElectivesPage.setVisible(false);

            infoLabel.getStyleClass().add("nav-item-clicked");
            genEdLabel.getStyleClass().remove("nav-item-clicked");
            majorCoursesLabel.getStyleClass().remove("nav-item-clicked");
            majorElecLabel.getStyleClass().remove("nav-item-clicked");
            catalogLabel.getStyleClass().remove("nav-item-clicked");
        });
        genEdLabel.setOnMouseClicked(e -> {
            infoPage.setVisible(false);
            genEdInfoPage.setVisible(true);
            majorCoursesPage.setVisible(false);
            majorElectivesPage.setVisible(false);

            infoLabel.getStyleClass().remove("nav-item-clicked");
            genEdLabel.getStyleClass().add("nav-item-clicked");
            majorCoursesLabel.getStyleClass().remove("nav-item-clicked");
            majorElecLabel.getStyleClass().remove("nav-item-clicked");
            catalogLabel.getStyleClass().remove("nav-item-clicked");
        });
        majorCoursesLabel.setOnMouseClicked(e -> {
            infoPage.setVisible(false);
            genEdInfoPage.setVisible(false);
            majorCoursesPage.setVisible(true);
            majorElectivesPage.setVisible(false);

            infoLabel.getStyleClass().remove("nav-item-clicked");
            genEdLabel.getStyleClass().remove("nav-item-clicked");
            majorCoursesLabel.getStyleClass().add("nav-item-clicked");
            majorElecLabel.getStyleClass().remove("nav-item-clicked");
            catalogLabel.getStyleClass().remove("nav-item-clicked");
        });
        majorElecLabel.setOnMouseClicked(e -> {
            infoPage.setVisible(false);
            genEdInfoPage.setVisible(false);
            majorCoursesPage.setVisible(false);
            majorElectivesPage.setVisible(true);

            infoLabel.getStyleClass().remove("nav-item-clicked");
            genEdLabel.getStyleClass().remove("nav-item-clicked");
            majorCoursesLabel.getStyleClass().remove("nav-item-clicked");
            majorElecLabel.getStyleClass().add("nav-item-clicked");
            catalogLabel.getStyleClass().remove("nav-item-clicked");
        });
        catalogLabel.setOnMouseClicked(e -> {
            infoPage.setVisible(false); 
            genEdInfoPage.setVisible(false);
            majorCoursesPage.setVisible(false);
            majorElectivesPage.setVisible(false);

            infoLabel.getStyleClass().remove("nav-item-clicked");
            genEdLabel.getStyleClass().remove("nav-item-clicked");
            majorCoursesLabel.getStyleClass().remove("nav-item-clicked");
            majorElecLabel.getStyleClass().remove("nav-item-clicked");
            catalogLabel.getStyleClass().add("nav-item-clicked");
        });
            
        // Instantiate the Scene
        Scene scene = new Scene(root, 1250, 840);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    // TO-DO
    private void initializeCourses() {
        // ------- CS CLASSES --------
        // CS Major Required Courses
        Course BIO1110 = new Course(
            "BIO",
            CourseCategory.MAJOR,
            1110,
            "Life Science",
            3
        );

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

        // CS Elective Courses (group 1)
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

        // CS Elective courses (group 2)
        Course CS2250 = new Course(
            "CS",
            CourseCategory.MAJOR_ELECTIVE,
            2250,
            "Introduction to Web Science and Technology",
            3
        );
        CS2250.setGroup(2);
        CS2250.addPrerequisite(CS1400);

        Course CS2410 = new Course(
            "CS",
            CourseCategory.MAJOR_ELECTIVE,
            2410,
            "Fundamentals of Data Science",
            3
        );
        CS2410.setGroup(2);
        CS2410.addPrerequisite(CS1400);
        
        Course CS2450 = new Course(
            "CS",
            CourseCategory.MAJOR_ELECTIVE,
            2450,
            "User Interace Design and Programming",
            3
        );
        CS2450.setGroup(2);
        CS2450.addPrerequisite(CS1400);

        Course CS2520 = new Course(
            "CS",
            CourseCategory.MAJOR_ELECTIVE,
            2520,
            "Python for Programmers",
            3
        );
        CS2520.setGroup(2);
        CS2520.addPrerequisite(CS1400);
        
        Course CS2560 = new Course(
            "CS",
            CourseCategory.MAJOR_ELECTIVE,
            2410,
            "C++ Programming",
            3
        );
        CS2560.setGroup(2);
        CS2560.addPrerequisite(CS1400);

        // CS Major courses
        allCsCourses.add(BIO1110);
        allCsCourses.add(CS1300);
        allCsCourses.add(CS1400);
        allCsCourses.add(CS2400);
        allCsCourses.add(CS2600);
        allCsCourses.add(CS2610);
        allCsCourses.add(CS2640);
        allCsCourses.add(CS3010);
        allCsCourses.add(CS3110);
        allCsCourses.add(CS3310);
        allCsCourses.add(CS3560);
        allCsCourses.add(CS3650);
        allCsCourses.add(CS3750W);
        allCsCourses.add(CS4080);
        allCsCourses.add(CS4310);
        allCsCourses.add(CS4630);
        allCsCourses.add(CS4800);
        allCsCourses.add(MAT1140);
        allCsCourses.add(MAT1150);
        allCsCourses.add(STA2260);

        // CS Elective courses (group 1)
        allCsElectives.add(CS3520);
        allCsElectives.add(CS3700);
        allCsElectives.add(CS3800);
        allCsElectives.add(CS4110);
        allCsElectives.add(CS4200);
        allCsElectives.add(CS4210);
        allCsElectives.add(CS4220);
        allCsElectives.add(CS4230);
        allCsElectives.add(CS4250);
        allCsElectives.add(CS4350);
        allCsElectives.add(CS4440);
        allCsElectives.add(CS4450);
        allCsElectives.add(CS4500);
        allCsElectives.add(CS4600);
        allCsElectives.add(CS4650);
        allCsElectives.add(CS4651);
        allCsElectives.add(CS4680);
        allCsElectives.add(CS4700);
        allCsElectives.add(CS4750);
        allCsElectives.add(CS4810);
        allCsElectives.add(CS4990);

        // CS Elective courses (group 2)
        allCsElectives.add(CS2250);
        allCsElectives.add(CS2410);
        allCsElectives.add(CS2450);
        allCsElectives.add(CS2520);
        allCsElectives.add(CS2560);

        // ------- ME CLASSES --------

        // ------- GE CLASSES --------

    }

    private void initializeGECourses() {
        // GE Area A
        // Group 1
        Course COM1110 = new Course(
            "COM",
            CourseCategory.GENERAL_EDUCATION,
            1110,
            "Public Speaking",
            3
        );
        COM1110.setGroup(1);

        Course COM2204 = new Course(
            "COM",
            CourseCategory.GENERAL_EDUCATION,
            2204,
            "Advocacy and Argument",
            3
        );
        COM2204.setGroup(1);

        // Group 2
        Course ENG1101 = new Course(
            "ENG",
            CourseCategory.GENERAL_EDUCATION,
            2204,
            "Stretch Composition II",
            3
        );
        ENG1101.setGroup(2);

        Course ENG1103 = new Course(
            "ENG",
            CourseCategory.GENERAL_EDUCATION,
            2204,
            "First Year Compositon",
            3
        );
        ENG1103.setGroup(2);

        // Group 3
        Course PHL2020 = new Course(
            "PHL",
            CourseCategory.GENERAL_EDUCATION,
            2020,
            "Critical Thinking",
            3
        );
        PHL2020.setGroup(3);

        Course ENG2105 = new Course(
            "ENG",
            CourseCategory.GENERAL_EDUCATION,
            2105,
            "Written Reasoning",
            3
        );
        ENG2105.setGroup(3);

        // GE Area B
        // Group 1
        Course AST1010 = new Course(
            "AST",
            CourseCategory.GENERAL_EDUCATION,
            1010,
            "Stars, Galxies, and the Universe",
            3
        );
        AST1010.setGroup(1);

        Course GEO1010 = new Course(
            "GEO",
            CourseCategory.GENERAL_EDUCATION,
            1010,
            "Physical Geography",
            3
        );
        GEO1010.setGroup(1);

        Course GSC1010 = new Course(
            "GSC",
            CourseCategory.GENERAL_EDUCATION,
            1100,
            "Water in a Changing World",
            3
        );
        GSC1010.setGroup(1);

        // Group 2
        Course ANT1010 = new Course(
            "ANT",
            CourseCategory.GENERAL_EDUCATION,
            1010,
            "Introduction to Biological Anthropology",
            3
        );
        ANT1010.setGroup(2);

        Course BIO1020 = new Course(
            "BIO",
            CourseCategory.GENERAL_EDUCATION,
            1020,
            "Plagues, Pandemics, and Bioterrorism",
            3
        );
        BIO1020.setGroup(2);

        Course BIO2700 = new Course(
            "GSC",
            CourseCategory.GENERAL_EDUCATION,
            2700,
            "Age of the Dinosaurs",
            3
        );
        BIO2700.setGroup(2);

        // Group 3
        Course BIO3010 = new Course(
            "BIO",
            CourseCategory.GENERAL_EDUCATION,
            3010,
            "Human Sexuality",
            3
        );
        BIO3010.setGroup(3);

        Course BIO3030 = new Course(
            "BIO",
            CourseCategory.GENERAL_EDUCATION,
            3030,
            "Sexually Transmitted Diseases and Safer Sex",
            3
        );
        BIO3030.setGroup(3);

        Course BIO3090 = new Course(
            "BIO",
            CourseCategory.GENERAL_EDUCATION,
            3090,
            "Biology of the Brain",
            3
        );
        BIO3090.setGroup(3);

        // GE Area C
        // Group 1
        Course COM2280 = new Course(
            "COM",
            CourseCategory.GENERAL_EDUCATION,
            2280,
            "Understanding and Appreciating the Photographic Image",
            3
        );
        COM2280.setGroup(1);

        Course DAN2020 = new Course(
            "DAN",
            CourseCategory.GENERAL_EDUCATION,
            2020,
            "World Dance and Culture",
            3
        );
        DAN2020.setGroup(1);

        // Group 2
        Course ANT1040 = new Course(
            "ANT",
            CourseCategory.GENERAL_EDUCATION,
            1040,
            "Introduction to Linguistic Anthropology",
            3
        );
        ANT1040.setGroup(2);

        Course ENG2500 = new Course(
            "ENG",
            CourseCategory.GENERAL_EDUCATION,
            2500,
            "Introduction to Shakespeare",
            3
        );
        ENG2500.setGroup(2);

        // GE Area D
        // Group 1
        Course HST2201 = new Course(
            "HST",
            CourseCategory.GENERAL_EDUCATION,
            2201,
            "United States History to 1877",
            3
        );
        HST2201.setGroup(1);

        Course HST2202 = new Course(
            "HST",
            CourseCategory.GENERAL_EDUCATION,
            2202,
            "United States History, 1877-Present",
            3
        );
        HST2202.setGroup(1);

        // Group 2
        Course PLS2010 = new Course(
            "PLS",
            CourseCategory.GENERAL_EDUCATION,
            2010,
            "Introducton to American Government",
            3
        );
        PLS2010.setGroup(2);

        // Group 3
        Course AG1010 = new Course(
            "AG",
            CourseCategory.GENERAL_EDUCATION,
            1010,
            "Agriculture & The Modern World",
            3
        );
        AG1010.setGroup(3);

        Course AMM1200 = new Course(
            "AMM",
            CourseCategory.GENERAL_EDUCATION,
            1200,
            "American Demographics and Lifestyles",
            3
        );
        AMM1200.setGroup(3);

        // GE Area E
        Course AVS2211 = new Course(
            "AVS",
            CourseCategory.GENERAL_EDUCATION,
            2211,
            "Drugs and Society",
            3
        );

        Course KIN2070 = new Course(
            "KIN",
            CourseCategory.GENERAL_EDUCATION,
            2070,
            "Stress Management for Healthy Living",
            3
        );

        // GE Area A
        areaA_GE.add(COM1110);
        areaA_GE.add(COM2204);
        areaA_GE.add(ENG1101);
        areaA_GE.add(ENG1103);
        areaA_GE.add(PHL2020);
        areaA_GE.add(ENG2105);

        // GE Area B
        areaB_GE.add(AST1010);
        areaB_GE.add(GEO1010);
        areaB_GE.add(GSC1010);
        areaB_GE.add(ANT1010);
        areaB_GE.add(BIO1020);
        areaB_GE.add(BIO2700);
        areaB_GE.add(BIO3010);
        areaB_GE.add(BIO3030);
        areaB_GE.add(BIO3090);

        // GE Area C
        areaC_GE.add(COM2280);
        areaC_GE.add(DAN2020);
        areaC_GE.add(ANT1040);
        areaC_GE.add(ENG2500);

        // GE Area D
        areaD_GE.add(HST2201);
        areaD_GE.add(HST2202);
        areaD_GE.add(PLS2010);
        areaD_GE.add(AG1010);
        areaD_GE.add(AMM1200);

        // GE Area E
        areaE_GE.add(AVS2211);
        areaE_GE.add(KIN2070);
    }

    // TO-DO
    private void initializeMajors() {
        Major CS = new Major("Computer Science");
        Major ME = new Major("Mechanical Engineering");

        for (Course c : allCsCourses) {
            String s = c.getCourseMajor();
            if ("CS".equalsIgnoreCase(s) ||
                "MAT".equalsIgnoreCase(s) ||
                "STA".equalsIgnoreCase(s)) {
                CS.addCourse(c); 
                }
            }

        for (Course c : allCsElectives) {
            String s = c.getCourseMajor();
            if ("CS".equalsIgnoreCase(s)) {
                CS.addElective(c);
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