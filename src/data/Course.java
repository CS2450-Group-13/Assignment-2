package src.data;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Class for each course
public class Course {
    private String name;
    private String courseMajor;
    private int courseNum;
    private int courseId;
    private int units;
    private CourseCategory category;
    private List<Course> prerequisites;
    Random random = new Random();

    public Course(String courseMajor, CourseCategory category, int courseNum, String name, int units) {
        this.courseMajor = courseMajor;
        this.category = category;
        this.courseNum = courseNum;
        this.name = name;
        this.units = units;
        this.courseId = random.nextInt(1000000) + random.nextInt(1000000);
        this.prerequisites = new ArrayList<>();
    }

    public Course(String courseMajor, CourseCategory category, int courseNum, String name, int units, List<Course> prerequisites) {
        this.courseMajor = courseMajor;
        this.category = category;
        this.courseNum = courseNum;
        this.name = name;
        this.units = units;
        this.courseId = random.nextInt(1000000) + random.nextInt(1000000);
        this.prerequisites = (prerequisites != null) ? prerequisites : new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getUnits() {
        return units;
    }

    public CourseCategory getCourseCategory() {
        return category;
    }

    public List<Course> getPrerequisites() {
        return prerequisites;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public void addPrerequisite(Course course) {
        prerequisites.add(course);
    }

    public boolean hasPrerequisites() {
        return !prerequisites.isEmpty();
    }

    @Override
    public String toString() {
        return courseMajor + courseNum + " " + name;
    }

}
