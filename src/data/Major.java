package src.data;
import java.util.List;
import java.util.ArrayList;

public class Major {
    private String name;
    private List<Course> courses;
    private List<Course> electives;

    public Major(String name) {
        this.name = name;
        this.courses = new ArrayList<>();
        this.electives = new ArrayList<>();
    }

    public Major(String name, List<Course> courses) {
        this.name = name;
        this.courses = (courses != null) ? courses : new ArrayList<>();
        this.electives = new ArrayList<>();
    }

    public Major(String name, List<Course> courses, List<Course> electives) {
        this.name = name;
        this.courses = (courses != null) ? courses : new ArrayList<>();
        this.electives = (electives != null) ? electives : new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getTotalUnits() {
        int totalUnits = 0;

        for (Course c : courses) {
            totalUnits += c.getUnits();
        }
        totalUnits += 17;

        return totalUnits;
    }

    public int getMajorRequiredUnits() {
        int totalUnits = 0;

        for (Course c : courses) {
            totalUnits += c.getUnits();
        }

        return totalUnits;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Course> getElectives() {
        return electives;
    }

    public List<Course> getCoursesByCategory(CourseCategory category) {
        return courses.stream()
            .filter(c -> c.getCourseCategory() == category)
            .toList();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void addElective(Course course) {
        electives.add(course);
    }

    @Override
    public String toString() {
        return name;
    }

}
