package model;

import exceptions.DaysTimesNotEqual;
import observer.CourseObserver;

import java.util.*;

public class Course extends Observable{

    private String courseName;
    private List<String> courseDays;
    private Map<String, String> daysToTimes = new HashMap<>();
    private List<Assignment> courseAssignments = new ArrayList<>();
    private List<Test> courseTests = new ArrayList<>();
    private Schedule schedule = Schedule.getInstance();

    // MODIFIES: this
    // EFFECTS: constructs new Course with given inputs
    public Course(String courseName, List<String> courseDays, List<String> courseTimes) throws DaysTimesNotEqual{
        if(courseDays.size() != courseTimes.size())
            throw new DaysTimesNotEqual("Course must have same number of days and times");
        this.courseName = courseName;
        this.courseDays = courseDays;
        for (int i=0; i<courseDays.size(); i++) daysToTimes.put(courseDays.get(i), courseTimes.get(i));
        addObserver(new CourseObserver());
    }

    // EFFECTS: returns name of course
    public String getCourseName() {return courseName;}
    // EFFECTS: returns what days course is on
    public List<String> getCourseDays() {return courseDays;}
    // EFFECTS: returns what times course is at
    public List<String> getCourseTimes() {
        List<String> courseTimes = new ArrayList<>();
        String time;
        for (String day: courseDays) {
            time = daysToTimes.get(day);
            courseTimes.add(time);
        }
        return courseTimes;
    }
    public List<Assignment> getCourseAssignments() {return courseAssignments;}
    public List<Test> getCourseTests() {return courseTests;}

    public List<String> getCourseDaysAndTimes() {
        List<String> daysAndTimes = new ArrayList<>();
        for (String day: courseDays) {
            daysAndTimes.add(day +" "+ daysToTimes.get(day));
        }
        return daysAndTimes;
    }

    public void printCourseAssignments() {
        for (Assignment assignment: courseAssignments) {
            assignment.printDate();
        }
    }

    public void printCourseTests() {
        for (Test test: courseTests){
            test.printDate();
        }
    }

    // MODIFIES: this
    // EFFECTS: sets new course name
    public void setCourseName(String courseName) {this.courseName = courseName;}
    // MODIFIES: this
    // EFFECTS: sets new days course is on
    public void setCourseDaysAndTimes(List<String> courseDays, List<String> courseTimes) throws DaysTimesNotEqual {
        if(courseDays.size() != courseTimes.size()){
            throw new DaysTimesNotEqual("Course must have same number of days and times");
        }
        this.courseDays = courseDays;
        daysToTimes = new HashMap<>();
        for (int i=0; i<courseDays.size(); i++) {
            daysToTimes.put(courseDays.get(i), courseTimes.get(i));
        }
    }
    public void setCourseAssignments(List<Assignment> courseAssignments) {this.courseAssignments = courseAssignments;}
    public void setCourseTests(List<Test> courseTests) {this.courseTests = courseTests;}

    public void setSchedule(Schedule schedule) {
        if(!this.schedule.equals(schedule)) {
            this.schedule = schedule;
            schedule.addCourse(this);
        }
    }

    public void removeSchedule(Schedule schedule) {
        if(this.schedule.equals(schedule)) {
            this.schedule = null;
            schedule.removeCourse(this);
        }
    }

    public void addAssignment(Assignment assignment) {
        this.courseAssignments.add(assignment);
        setChanged();
        notifyObservers(assignment);
    }

    public void addTest(Test test) {
        this.courseTests.add(test);
        setChanged();
        notifyObservers(test);
    }
}