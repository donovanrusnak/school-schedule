package ui;

import model.Schedule;

public class Main {

    private static Schedule schedule = Schedule.getInstance();

    public static void main(String[] args)
    {
        new UserInterface(schedule);
    }
}