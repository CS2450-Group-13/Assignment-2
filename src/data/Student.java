package src.data;

public class Student {
    private String name;
    private int id;
    private String semStarted; // format Fall/Spring Semester [Year]
    private String expectedGradSem; // format Fall/Spring Semester [Year]
    private boolean isUndergrad;
    private Major major;
    private boolean gradStatus;
    private double gpa;

    public Student(
        String name, 
        int id, 
        String semStarted,
        String expectedGradSem,
        boolean isUndergrad,
        boolean gradStatus,
        double gpa
        ) {

        this.name = name;
        this.id = id;
        this.isUndergrad = isUndergrad;
        this.semStarted = semStarted;
        this.expectedGradSem = expectedGradSem;
        this.isUndergrad = isUndergrad;
        this.gradStatus = gradStatus;
        this.gpa = gpa;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getUndergradStatus() {
        return (isUndergrad) ? "Undergraduate" : "Graduate";
    }

    public String getStartingSemester() {
        return semStarted;
    }

    public String getExpectedGradSem() {
        return expectedGradSem;
    }

    public Major getMajor() {
        return major;
    }

    public double getGPA() {
        return gpa;
    }

    public String getStanding() {
        if (gpa >= 2.3) {
            return "Good Standing";
        } else if (gpa >= 2.0 && gpa <= 2.2) {
            return "At-risk";
        } else {
            return "Academic Probation";
        }
    }

    public String getGradStatus() {
        return (gradStatus) ? "Applied" : "Not Applied";
    }

    public String getCourseCatalog() {
        String[] parts = semStarted.split(" ");
        int startYear = Integer.parseInt(parts[parts.length - 1]);
        int endYear = startYear + 1;
        return startYear + " to " + endYear + " {Semester}";
    }

    public void setMajor(Major major) {
        this.major = major;
    }
}
