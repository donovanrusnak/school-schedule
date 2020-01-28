package model;

public class Assignment extends CourseEvent {

    public Assignment(String name, String date){
        super(name, date);
    }

    public void printDate(){
        System.out.println(getName()+ " is due on " +getDate());
    }
}
