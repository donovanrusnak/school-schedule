package model;

import exceptions.DaysTimesNotEqual;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Schedule implements Loadable, Saveable {

    private static Schedule schedule = null;

    private String scheduleName;
    private List<Course> courses;

    // MODIFIES: this
    // EFFECTS: constructs new Schedule
    private Schedule(){
        courses = new ArrayList<>();
    }

    public static Schedule getInstance()
    {
        if (schedule == null)
            schedule = new Schedule();

        return schedule;
    }

    // EFFECTS: returns list of Courses in Schedule
    public List<Course> getCourses() {return courses;}

    // EFFECTS: returns list of names of Courses in Schedule
    public List<String> getCourseNames() {
        List<String> courseNames = new ArrayList<>();
        for (Course course : courses){
            String courseName = course.getCourseName();
            courseNames.add(courseName);
        }
        return courseNames;
    }

    public String getScheduleName() {return scheduleName;}

    public void setScheduleName(String scheduleName) {this.scheduleName = scheduleName;}

    // MODIFIES: this
    // EFFECTS: adds new Course to Schedule
    public void addCourse(Course course) {
        if(!courses.contains(course)) {
            courses.add(course);
            course.setSchedule(this);
        }
    }

    public void removeCourse(Course course) {
        if(courses.contains(course)) {
            courses.remove(course);
            course.removeSchedule(this);
        }
    }

    public void load(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        for (String line : lines) {
            Course course;
            String courseName;
            List<String> courseDays = new ArrayList<>();
            List courseTimes = new ArrayList<>();

            ArrayList<String> partsOfLine = splitOnSpace(line);
            courseName = (partsOfLine.get(0));
            for (int i=1; i<partsOfLine.size(); i++) {
                if (i<=partsOfLine.size()/2) {
                    courseDays.add(partsOfLine.get(i));
                }else{
                    courseTimes.add(partsOfLine.get(i));
                }
            }
            try {
                course = new Course(courseName, courseDays, courseTimes);
                this.addCourse(course);
            } catch (DaysTimesNotEqual dtne) {
                System.out.println(dtne.getMessage());
            }
        }
    }

    public void save(String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(fileName,"UTF-8");
        for (Course course : courses){
            String line = course.getCourseName();
            for (String courseDay : course.getCourseDays()){
                line = line+ " " +courseDay;
            }
            for (String courseTime : course.getCourseTimes()){
                line = line+ " " +courseTime;
            }
            writer.println(line);
        }
        writer.close();
    }

    public ArrayList<String> splitOnSpace(String line){
        String[] splits = line.split(" ");
        return new ArrayList<>(Arrays.asList(splits));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(scheduleName, schedule.scheduleName) &&
                Objects.equals(courses, schedule.courses);
    }

    @Override
    public int hashCode() {

        return Objects.hash(scheduleName, courses);
    }
}