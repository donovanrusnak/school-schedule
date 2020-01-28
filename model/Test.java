package model;

public class Test extends CourseEvent {

    public Test(String name, String date){
        super(name, date);
    }

    public void printDate(){
        System.out.println(getName()+ " is on " +getDate());
    }
}
