import java.util.Random;

// Class for each course
public class Course {
    private String name;
    private String courseMajor;
    private int courseNum;
    private int courseId;
    private int units;
    Random random = new Random();

    public Course(String courseMajor, int courseNum, String name, int units) {
        this.name = name;
        this.units = units;
        this.courseNum = courseNum;
        this.courseMajor = courseMajor;
        this.courseId = random.nextInt(1000000) + random.nextInt(100000);
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

    public void setName(String name) {
        this.name = name;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    @Override
    public String toString() {
        return courseMajor + courseNum + " " + name;
    }

}
