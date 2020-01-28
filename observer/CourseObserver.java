package observer;

import model.Assignment;
import model.CourseEvent;
import model.Test;

import java.util.Observable;
import java.util.Observer;

public class CourseObserver implements Observer{

    @Override
    public void update(Observable o, Object arg) {
        String type = null;
        String name = ((CourseEvent) arg).getName();
        if(arg instanceof Assignment){
            type = "assignment";
        }
        if(arg instanceof Test){
            type = "test";
        }
        System.out.println("New " +type+ " " +name+ " successfully added to course");
    }
}
