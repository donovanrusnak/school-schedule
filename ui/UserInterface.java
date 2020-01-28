package ui;

import exceptions.DaysTimesNotEqual;
import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInterface extends JFrame implements ActionListener {

    private static final String LOAD_SCHEDULE_COMMAND = "load";
    private static final String NEW_SCHEDULE_COMMAND = "new";
    private static final String NEW_COURSE_COMMAND = "new course";
    private static final String VIEW_COURSES_COMMAND = "view courses";
    private static final String VIEW_COURSE_COMMAND = "view course";
    private static final String EDIT_COURSE_COMMAND = "edit course";
    private static final String EDIT_COURSE_NAME_COMMAND = "name";
    private static final String EDIT_COURSE_SCHEDULE_COMMAND = "schedule";
    private static final String EDIT_ASSIGNMENT_COMMAND = "assignments";
    private static final String EDIT_TEST_COMMAND = "tests";
    private static final String QUIT_COMMAND = "quit";
    private static final String GO_BACK_COMMAND = "back";
    private static final String INPUT_ERROR_MESSAGE = "Sorry, did not understand input";
    private static final String ADD_COMMAND = "add";
    private static final String EDIT_COMMAND = "edit";
    private static final String RENAME_COMMAND = "rename";
    private static final String NEW_DATE_COMMAND = "new date";

    private Schedule schedule;
    private int classPerWeek;
    private String courseName;
    private List<String> courseDays = new ArrayList<>();
    private List<String> courseTimes = new ArrayList<>();
    private Course activeCourse;

    private JLabel outputLabel;
    private JPanel topPanel = new JPanel();
    private JPanel bottomPanel = new JPanel();
    private JTextField scheduleNameField = new JTextField();
    private JTextField courseNameField = new JTextField();
    private JTextField classPerWeekField = new JTextField();
    private JTextField courseDaysField1 = new JTextField();
    private JTextField courseDaysField2 = new JTextField();
    private JTextField courseDaysField3 = new JTextField();
    private JTextField courseDaysField4 = new JTextField();
    private JTextField courseDaysField5 = new JTextField();
    private JTextField courseTimesField1 = new JTextField();
    private JTextField courseTimesField2 = new JTextField();
    private JTextField courseTimesField3 = new JTextField();
    private JTextField courseTimesField4 = new JTextField();
    private JTextField courseTimesField5 = new JTextField();

    public UserInterface(Schedule schedule) {
        super("My Schedule");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.schedule = schedule;

        setPreferredSize(new Dimension(400, 350));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        WebPageReader webPageReader = new WebPageReader();
        outputLabel = new JLabel();
        try {
            outputLabel.setText(webPageReader.read());
        } catch (IOException e) {
            e.printStackTrace();
        }

        outputLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton loadSchedule = new JButton("Load Schedule");
        loadSchedule.setActionCommand("loadSchedule");
        loadSchedule.setAlignmentY(TOP_ALIGNMENT);
        loadSchedule.setAlignmentX(CENTER_ALIGNMENT);
        loadSchedule.addActionListener(this);

        JButton newSchedule = new JButton("New Schedule");
        newSchedule.setActionCommand("newSchedule");
        newSchedule.setAlignmentY(BOTTOM_ALIGNMENT);
        newSchedule.setAlignmentX(CENTER_ALIGNMENT);
        newSchedule.addActionListener(this);

//        inputField = new JTextArea(5, 10);

//        JButton loadScheduleName = new JButton("Enter");
//        loadScheduleName.setActionCommand("loadScheduleName");
//        loadScheduleName.setAlignmentX(Component.CENTER_ALIGNMENT);
//        loadScheduleName.addActionListener(this); //sets "this" class as an action listener for btn.
        //that means that when the btn is clicked,
        //this.actionPerformed(ActionEvent e) will be called.
        //You could also set a different class, if you wanted
        //to capture the response behaviour elsewhere

        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.PAGE_AXIS));
        topPanel.setPreferredSize(new Dimension(100, 150));
        topPanel.add(outputLabel);

        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.PAGE_AXIS));
        bottomPanel.add(loadSchedule);
        bottomPanel.add(newSchedule);
//        bottomPanel.add(inputField);
//        bottomPanel.add(loadScheduleName);

        add(topPanel);
        add(bottomPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    //this is the method that runs when Swing registers an action on an element
    //for which this class is an ActionListener
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("loadSchedule")) {
            loadScheduleInterface();
        }
        if (e.getActionCommand().equals("newSchedule")) {
            newScheduleInterface();
        }
        if (e.getActionCommand().equals("loadScheduleName")) {
            String scheduleName;
            scheduleName = scheduleNameField.getText();
            schedule.setScheduleName(scheduleName);
            try {
                schedule.load(scheduleName + ".txt");
                setTitle(scheduleName);
            } catch (IOException ioe) {
                outputLabel.setText("Error loading schedule, please try again");
                loadScheduleInterface();
            }
            runInterface();
        }
        if (e.getActionCommand().equals("newScheduleName")) {
            String scheduleName;
            scheduleName = scheduleNameField.getText();
            scheduleNameField.setText("");
            schedule.setScheduleName(scheduleName);
            setTitle(scheduleName);
            runInterface();
        }
        if (e.getActionCommand().equals("newCourse")) {
            newCourseInterfaceOne();
        }
        if (e.getActionCommand().equals("viewCourses")) {
            viewCoursesInterface();
        }
        if (e.getActionCommand().equals("save")) {
            saveInterface();
        }
        if (e.getActionCommand().equals("finishCourseOne")) {
            courseName = courseNameField.getText();
            courseNameField.setText("");
            classPerWeek = Integer.parseInt(classPerWeekField.getText());
            classPerWeekField.setText("");
            newCourseInterfaceTwo();
        }
        if (e.getActionCommand().equals("finishCourseTwo")) {
            Course course = null;
            courseDays.add(courseDaysField1.getText());
            courseDaysField1.setText("");
            if (classPerWeek > 1) {
                courseDays.add(courseDaysField2.getText());
                courseDaysField2.setText("");
                if (classPerWeek > 2) {
                    courseDays.add(courseDaysField3.getText());
                    courseDaysField3.setText("");
                    if (classPerWeek > 3) {
                        courseDays.add(courseDaysField4.getText());
                        courseDaysField4.setText("");
                        if (classPerWeek > 4) {
                            courseDays.add(courseDaysField5.getText());
                            courseDaysField5.setText("");
                        }
                    }
                }
            }
            courseTimes.add(courseTimesField1.getText());
            courseTimesField1.setText("");
            if (classPerWeek > 1) {
                courseTimes.add(courseTimesField2.getText());
                courseTimesField2.setText("");
                if (classPerWeek > 2) {
                    courseTimes.add(courseTimesField3.getText());
                    courseTimesField3.setText("");
                    if (classPerWeek > 3) {
                        courseTimes.add(courseTimesField4.getText());
                        courseTimesField4.setText("");
                        if (classPerWeek > 4) {
                            courseTimes.add(courseTimesField5.getText());
                            courseTimesField5.setText("");
                        }
                    }
                }
            }
            try {
                course = new Course(courseName, courseDays, courseTimes);
                schedule.addCourse(course);
            } catch (DaysTimesNotEqual dtne) {
                System.out.println(dtne.getMessage());
            }
//            setAssignmentInterface(course);
//            setTestInterface(course);
            courseName = null;
            courseDays = new ArrayList<>();
            courseTimes = new ArrayList<>();
            bottomPanel.removeAll();
            bottomPanel.updateUI();
            outputLabel.setText("New course '" + course.getCourseName() + "' added to schedule");
            runInterface();
        }
        if (e.getActionCommand().equals("course1")) {
            activeCourse = schedule.getCourses().get(0);
            viewCourseInterface();
        }
        if (e.getActionCommand().equals("course2")) {
            activeCourse = schedule.getCourses().get(1);
            viewCourseInterface();
        }
        if (e.getActionCommand().equals("course3")) {
            activeCourse = schedule.getCourses().get(2);
            viewCourseInterface();
        }
        if (e.getActionCommand().equals("course4")) {
            activeCourse = schedule.getCourses().get(3);
            viewCourseInterface();
        }
        if (e.getActionCommand().equals("course5")) {
            activeCourse = schedule.getCourses().get(4);
            viewCourseInterface();
        }
        if (e.getActionCommand().equals("course6")) {
            activeCourse = schedule.getCourses().get(5);
            viewCourseInterface();
        }
        if (e.getActionCommand().equals("course7")) {
            activeCourse = schedule.getCourses().get(6);
            viewCourseInterface();
        }
        if (e.getActionCommand().equals("course8")) {
            activeCourse = schedule.getCourses().get(7);
            viewCourseInterface();
        }
        if (e.getActionCommand().equals("editCourseName")) {
            editCourseNameInterface();
        }
        if (e.getActionCommand().equals("editCourseSchedule")) {
            editCourseScheduleInterfaceOne();
        }
        if (e.getActionCommand().equals("removeCourse")) {
            schedule.removeCourse(activeCourse);
            activeCourse = null;
            runInterface();
        }
//        if(e.getActionCommand().equals("editCourseTests")){
//            editCourseTestsInterface();
//        }
//        if(e.getActionCommand().equals("editCourseAssignments")){
//            editCourseAssignmentsInterface();
//        }
        if (e.getActionCommand().equals("newCourseName")) {
            activeCourse.setCourseName(courseNameField.getText());
            courseNameField.setText("");
            activeCourse = null;
            runInterface();
        }
        if (e.getActionCommand().equals("finishEditCourseOne")) {
            classPerWeek = Integer.parseInt(classPerWeekField.getText());
            classPerWeekField.setText("");
            editCourseScheduleInterfaceTwo();
        }
        if (e.getActionCommand().equals("finishEditCourseTwo")) {
            courseDays.add(courseDaysField1.getText());
            courseDaysField1.setText("");
            if (classPerWeek > 1) {
                courseDays.add(courseDaysField2.getText());
                courseDaysField2.setText("");
                if (classPerWeek > 2) {
                    courseDays.add(courseDaysField3.getText());
                    courseDaysField3.setText("");
                    if (classPerWeek > 3) {
                        courseDays.add(courseDaysField4.getText());
                        courseDaysField4.setText("");
                        if (classPerWeek > 4) {
                            courseDays.add(courseDaysField5.getText());
                            courseDaysField5.setText("");
                        }
                    }
                }
            }
            courseTimes.add(courseTimesField1.getText());
            courseTimesField1.setText("");
            if (classPerWeek > 1) {
                courseTimes.add(courseTimesField2.getText());
                courseTimesField2.setText("");
                if (classPerWeek > 2) {
                    courseTimes.add(courseTimesField3.getText());
                    courseTimesField3.setText("");
                    if (classPerWeek > 3) {
                        courseTimes.add(courseTimesField4.getText());
                        courseTimesField4.setText("");
                        if (classPerWeek > 4) {
                            courseTimes.add(courseTimesField5.getText());
                            courseTimesField5.setText("");
                        }
                    }
                }
            }
            try {
                activeCourse.setCourseDaysAndTimes(courseDays, courseTimes);
            } catch (DaysTimesNotEqual dtne) {
                System.out.println(dtne.getMessage());
            }
//            setAssignmentInterface(course);
//            setTestInterface(course);
            courseDays = new ArrayList<>();
            courseTimes = new ArrayList<>();
            activeCourse = null;
            runInterface();
        }
        if (e.getActionCommand().equals("back")) {
            activeCourse = null;
            runInterface();
        }
        if (e.getActionCommand().equals("save")) {
            try {
                schedule.save(schedule.getScheduleName() + ".txt");
                outputLabel.setText(schedule.getScheduleName() + " saved successfully");
            } catch (IOException ioe) {
                outputLabel.setText("Sorry, unable to save schedule");
            }
            JButton backButton = new JButton("Back");
            backButton.setActionCommand("back");
            backButton.addActionListener(this);
            bottomPanel.add(backButton);
        }
//        if(e.getActionCommand().equals("loadScheduleName"))
//        {
//            input = inputField.getText();
//            inputField.setText("");
//            if (whichStep.equals("start")){
//
//            }
//        }
    }

    private void loadScheduleInterface() {
        outputLabel.setText("Please enter the name of your schedule");
        bottomPanel.removeAll();
        bottomPanel.updateUI();

        JButton loadScheduleName = new JButton("Enter");
        bottomPanel.add(scheduleNameField);
        loadScheduleName.setActionCommand("loadScheduleName");
        loadScheduleName.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        loadScheduleName.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadScheduleName.addActionListener(this);
        bottomPanel.add(loadScheduleName);
    }

    private void newScheduleInterface() {
        outputLabel.setText("Please enter the name of your schedule");
        bottomPanel.removeAll();
        bottomPanel.updateUI();

        JButton newScheduleName = new JButton("Enter");
        newScheduleName.setActionCommand("newScheduleName");
        newScheduleName.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        newScheduleName.setAlignmentX(Component.CENTER_ALIGNMENT);
        newScheduleName.addActionListener(this);

        bottomPanel.add(scheduleNameField);
        bottomPanel.add(newScheduleName);
    }

    private void runInterface() {
        topPanel.removeAll();
        topPanel.updateUI();
        topPanel.add(outputLabel);
        outputLabel.setText("");
        bottomPanel.removeAll();
        bottomPanel.updateUI();

        JButton newCourse = new JButton("New Course");
        newCourse.setActionCommand("newCourse");
        newCourse.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        newCourse.setAlignmentX(Component.CENTER_ALIGNMENT);
        newCourse.addActionListener(this);

        JButton viewCourses = new JButton("View Courses");
        viewCourses.setActionCommand("viewCourses");
        viewCourses.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        viewCourses.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewCourses.addActionListener(this);

        JButton save = new JButton("Save");
        save.setActionCommand("save");
        save.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        save.setAlignmentX(Component.CENTER_ALIGNMENT);
        save.addActionListener(this);

        bottomPanel.add(newCourse);
        bottomPanel.add(viewCourses);
        bottomPanel.add(save);
    }

    private void newCourseInterfaceOne() {
        bottomPanel.removeAll();
        bottomPanel.updateUI();

        JLabel courseNameLabel = new JLabel("Please enter the name of your course:");

        JLabel classPerWeekLabel = new JLabel("Please enter how many days a week you have your course:");

        JButton finishCourseButtonOne = new JButton("Next");
        finishCourseButtonOne.setActionCommand("finishCourseOne");
        finishCourseButtonOne.addActionListener(this);

        bottomPanel.add(courseNameLabel);
        bottomPanel.add(courseNameField);
        bottomPanel.add(classPerWeekLabel);
        bottomPanel.add(classPerWeekField);
        bottomPanel.add(finishCourseButtonOne);
    }

    private void newCourseInterfaceTwo() {
        bottomPanel.removeAll();
        bottomPanel.updateUI();

        JLabel courseDaysLabel = new JLabel("Please enter which days of the week you have " + courseName + ":");

        JLabel courseTimesLabel = new JLabel("Please enter at what times you have " + courseName + ":");

        JButton finishCourseButtonTwo = new JButton("Finish");
        finishCourseButtonTwo.setActionCommand("finishCourseTwo");
        finishCourseButtonTwo.setAlignmentX(LEFT_ALIGNMENT);
        finishCourseButtonTwo.addActionListener(this);

        bottomPanel.add(courseDaysLabel);
        bottomPanel.add(courseDaysField1);
        if (classPerWeek > 1) {
            bottomPanel.add(courseDaysField2);
            if (classPerWeek > 2) {
                bottomPanel.add(courseDaysField3);
                if (classPerWeek > 3) {
                    bottomPanel.add(courseDaysField4);
                    if (classPerWeek > 4) {
                        bottomPanel.add(courseDaysField5);
                    }
                }
            }
        }
        bottomPanel.add(courseTimesLabel);
        bottomPanel.add(courseTimesField1);
        if (classPerWeek > 1) {
            bottomPanel.add(courseTimesField2);
            if (classPerWeek > 2) {
                bottomPanel.add(courseTimesField3);
                if (classPerWeek > 3) {
                    bottomPanel.add(courseTimesField4);
                    if (classPerWeek > 4) {
                        bottomPanel.add(courseTimesField5);
                    }
                }
            }
        }
        bottomPanel.add(finishCourseButtonTwo);
    }

    private void viewCoursesInterface() {
        bottomPanel.removeAll();
        bottomPanel.updateUI();

        if (schedule.getCourses().size() == 0) {
            outputLabel.setText("Sorry, there are no courses in " + schedule.getScheduleName());
            JButton backButton = new JButton("Back");
            backButton.setActionCommand("back");
            backButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
            backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            backButton.addActionListener(this);
            bottomPanel.add(backButton);
        } else if (schedule.getCourses().size() > 0) {
            outputLabel.setText("Please select a course:");
            outputLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            String course1Name = schedule.getCourses().get(0).getCourseName();
            JButton course1Button = new JButton(course1Name);
            course1Button.setActionCommand("course1");
            course1Button.setAlignmentY(Component.BOTTOM_ALIGNMENT);
            course1Button.setAlignmentX(Component.CENTER_ALIGNMENT);
            course1Button.addActionListener(this);
            bottomPanel.add(course1Button);
            if (schedule.getCourses().size() > 1) {
                String course2Name = schedule.getCourses().get(1).getCourseName();
                JButton course2Button = new JButton(course2Name);
                course2Button.setActionCommand("course2");
                course2Button.setAlignmentY(Component.BOTTOM_ALIGNMENT);
                course2Button.setAlignmentX(Component.CENTER_ALIGNMENT);
                course2Button.addActionListener(this);
                bottomPanel.add(course2Button);
                if (schedule.getCourses().size() > 2) {
                    String course3Name = schedule.getCourses().get(2).getCourseName();
                    JButton course3Button = new JButton(course3Name);
                    course3Button.setActionCommand("course3");
                    course3Button.setAlignmentY(Component.BOTTOM_ALIGNMENT);
                    course3Button.setAlignmentX(Component.CENTER_ALIGNMENT);
                    course3Button.addActionListener(this);
                    bottomPanel.add(course3Button);
                    if (schedule.getCourses().size() > 3) {
                        String course4Name = schedule.getCourses().get(3).getCourseName();
                        JButton course4Button = new JButton(course4Name);
                        course4Button.setActionCommand("course4");
                        course4Button.setAlignmentY(Component.BOTTOM_ALIGNMENT);
                        course4Button.setAlignmentX(Component.CENTER_ALIGNMENT);
                        course4Button.addActionListener(this);
                        bottomPanel.add(course4Button);
                        if (schedule.getCourses().size() > 4) {
                            String course5Name = schedule.getCourses().get(4).getCourseName();
                            JButton course5Button = new JButton(course5Name);
                            course5Button.setActionCommand("course5");
                            course5Button.setAlignmentY(Component.BOTTOM_ALIGNMENT);
                            course5Button.setAlignmentX(Component.CENTER_ALIGNMENT);
                            course5Button.addActionListener(this);
                            bottomPanel.add(course5Button);
                            if (schedule.getCourses().size() > 5) {
                                String course6Name = schedule.getCourses().get(5).getCourseName();
                                JButton course6Button = new JButton(course6Name);
                                course6Button.setActionCommand("course6");
                                course6Button.setAlignmentY(Component.BOTTOM_ALIGNMENT);
                                course6Button.setAlignmentX(Component.CENTER_ALIGNMENT);
                                course6Button.addActionListener(this);
                                bottomPanel.add(course6Button);
                                if (schedule.getCourses().size() > 6) {
                                    String course7Name = schedule.getCourses().get(6).getCourseName();
                                    JButton course7Button = new JButton(course7Name);
                                    course7Button.setActionCommand("course7");
                                    course7Button.setAlignmentY(Component.BOTTOM_ALIGNMENT);
                                    course7Button.setAlignmentX(Component.CENTER_ALIGNMENT);
                                    course7Button.addActionListener(this);
                                    bottomPanel.add(course7Button);
                                    if (schedule.getCourses().size() > 7) {
                                        String course8Name = schedule.getCourses().get(7).getCourseName();
                                        JButton course8Button = new JButton(course8Name);
                                        course8Button.setActionCommand("course8");
                                        course8Button.setAlignmentY(Component.BOTTOM_ALIGNMENT);
                                        course8Button.setAlignmentX(Component.CENTER_ALIGNMENT);
                                        course8Button.addActionListener(this);
                                        bottomPanel.add(course8Button);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void viewCourseInterface() {
        topPanel.removeAll();
        topPanel.updateUI();
        bottomPanel.removeAll();
        bottomPanel.updateUI();

        outputLabel.setText(activeCourse.getCourseName());
        topPanel.add(outputLabel);

        for (String output : activeCourse.getCourseDaysAndTimes()) {
            JLabel outputLabel = new JLabel(output);
            outputLabel.setAlignmentY(Component.TOP_ALIGNMENT);
            outputLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            topPanel.add(outputLabel);
        }

        JButton editCourseNameButton = new JButton("Edit Name");
        editCourseNameButton.setActionCommand("editCourseName");
        editCourseNameButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        editCourseNameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        editCourseNameButton.addActionListener(this);

        JButton editCourseScheduleButton = new JButton("Edit Schedule");
        editCourseScheduleButton.setActionCommand("editCourseSchedule");
        editCourseScheduleButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        editCourseScheduleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        editCourseScheduleButton.addActionListener(this);

        JButton removeCourseButton = new JButton("Remove");
        removeCourseButton.setActionCommand("removeCourse");
        removeCourseButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        removeCourseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeCourseButton.addActionListener(this);

//        JButton editCourseTestsButton = new JButton("Edit Tests");
//        editCourseTestsButton.setActionCommand("editCourseTests");
//        editCourseTestsButton.addActionListener(this);
//
//        JButton editCourseAssignmentsButton = new JButton("Edit Assignments");
//        editCourseAssignmentsButton.setActionCommand("editCourseAssignments");
//        editCourseAssignmentsButton.addActionListener(this);

        JButton backButton = new JButton("Back");
        backButton.setActionCommand("back");
        backButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(this);

        bottomPanel.add(editCourseNameButton);
        bottomPanel.add(editCourseScheduleButton);
        bottomPanel.add(removeCourseButton);
//        bottomPanel.add(editCourseTestsButton);
//        bottomPanel.add(editCourseAssignmentsButton);
        bottomPanel.add(backButton);
    }

    private void editCourseNameInterface() {
        topPanel.removeAll();
        topPanel.updateUI();
        bottomPanel.removeAll();
        bottomPanel.updateUI();

        outputLabel.setText("Please enter the new name of " + activeCourse.getCourseName() + ":");
        JButton newCourseName = new JButton("Enter");
        newCourseName.setActionCommand("newCourseName");
        newCourseName.addActionListener(this);

        topPanel.add(outputLabel);
        bottomPanel.add(courseNameField);
        bottomPanel.add(newCourseName);
    }

    private void editCourseScheduleInterfaceOne() {
        topPanel.removeAll();
        topPanel.updateUI();
        bottomPanel.removeAll();
        bottomPanel.updateUI();

        JLabel classPerWeekLabel = new JLabel("Please enter how many days a week you have "
                + activeCourse.getCourseName() + ":");

        JButton editCourseButtonOne = new JButton("Next");
        editCourseButtonOne.setActionCommand("finishEditCourseOne");
        editCourseButtonOne.addActionListener(this);

        bottomPanel.add(classPerWeekLabel);
        bottomPanel.add(classPerWeekField);
        bottomPanel.add(editCourseButtonOne);
    }

    private void editCourseScheduleInterfaceTwo() {
        bottomPanel.removeAll();
        bottomPanel.updateUI();

        JLabel courseDaysLabel = new JLabel("Please enter which days of the week you have "
                + activeCourse.getCourseName() + ":");

        JLabel courseTimesLabel = new JLabel("Please enter at what times you have "
                + activeCourse.getCourseName() + ":");

        JButton editCourseButtonTwo = new JButton("Finish");
        editCourseButtonTwo.setActionCommand("finishEditCourseTwo");
        editCourseButtonTwo.addActionListener(this);

        bottomPanel.add(courseDaysLabel);
        bottomPanel.add(courseDaysField1);
        if (classPerWeek > 1) {
            bottomPanel.add(courseDaysField2);
            if (classPerWeek > 2) {
                bottomPanel.add(courseDaysField3);
                if (classPerWeek > 3) {
                    bottomPanel.add(courseDaysField4);
                    if (classPerWeek > 4) {
                        bottomPanel.add(courseDaysField5);
                    }
                }
            }
        }
        bottomPanel.add(courseTimesLabel);
        bottomPanel.add(courseTimesField1);
        if (classPerWeek > 1) {
            bottomPanel.add(courseTimesField2);
            if (classPerWeek > 2) {
                bottomPanel.add(courseTimesField3);
                if (classPerWeek > 3) {
                    bottomPanel.add(courseTimesField4);
                    if (classPerWeek > 4) {
                        bottomPanel.add(courseTimesField5);
                    }
                }
            }
        }
        bottomPanel.add(editCourseButtonTwo);
    }

    private void editCourseTestsInterface() {
        //stub
    }

    private void editCourseAssignmentsInterface() {
        //stub
    }

    private void saveInterface() {
        bottomPanel.removeAll();
        bottomPanel.updateUI();

        JLabel saveOutput = new JLabel();
        saveOutput.setAlignmentY(TOP_ALIGNMENT);
        saveOutput.setAlignmentX(CENTER_ALIGNMENT);
        JButton backButton = new JButton("Back");
        backButton.setActionCommand("back");
        backButton.setAlignmentY(BOTTOM_ALIGNMENT);
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.addActionListener(this);

        try {
            schedule.save(schedule.getScheduleName() + ".txt");
            saveOutput.setText(schedule.getScheduleName() + " saved successfully");
        } catch (IOException ioe) {
            saveOutput.setText("Sorry, unable to save schedule");
        }
    }

//    private void run(Schedule schedule) {
//        startScheduleInterface(schedule);
//        runInterface(schedule);
//    }
//
//    private void startScheduleInterface(Schedule schedule) {
//        WebPageReader webPageReader = new WebPageReader();
//        boolean validInput = false;
//        Scanner scanner = new Scanner(System.in);
//        String scheduleName = null;
//        String operation = null;
//        String input = null;
//
//        try {
//            outputLabel.setText(webPageReader.read());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        while(!validInput) {
//            startInstructions();
//            if (input.equals(LOAD_SCHEDULE_COMMAND)) {
//                boolean fileError = true;
//                while (fileError) {
//                    outputLabel.setText("Please enter the name of your schedule");
//                    scheduleName = scanner.nextLine();
//                    schedule.setScheduleName(scheduleName);
//                    try {
//                        schedule.load(scheduleName + ".txt");
//                        fileError = false;
//                        validInput = true;
//                    } catch (IOException ioe) {
//                        outputLabel.setText("Error loading schedule, please try again");
//                    }
//                }
//            } else if (input.equals(NEW_SCHEDULE_COMMAND)) {
//                outputLabel.setText("Please enter the name of your schedule");
//                scheduleName = scanner.nextLine();
//                schedule.setScheduleName(scheduleName);
//                validInput = true;
//            } else {
//                outputLabel.setText(INPUT_ERROR_MESSAGE);
//            }
//        }
//    }
//
//    private void startInstructions() {
//        outputLabel.setText("<html>To load a previous schedule type '" +LOAD_SCHEDULE_COMMAND+ "' <br>" +
//                "To start a new schedule type '" +NEW_SCHEDULE_COMMAND+ "'");
//    }
//
//    private void runInterface(Schedule schedule) {
//        boolean run = true;
//        Scanner scanner = new Scanner(System.in);
//        String operation;
//        String scheduleName = schedule.getScheduleName();
//
//        while (run) {
//            printInstructions();
//            operation = scanner.nextLine();
//            if (operation.equals(NEW_COURSE_COMMAND)) {
//                newCourseInterface(schedule);
//            }
//            else if (operation.equals(VIEW_COURSES_COMMAND)) {
//                viewCoursesInterface(schedule);
//            }
//            else if (operation.equals(QUIT_COMMAND)) {
//                outputLabel.setText("Do you want to save this schedule?");
//                operation = scanner.nextLine();
//                if (operation.equals("yes")){
//                    try {
//                        schedule.save(scheduleName+ ".txt");
//                        outputLabel.setText(scheduleName+ " saved successfully");
//                    } catch (IOException ioe) {
//                        outputLabel.setText("Sorry, unable to save schedule");
//                    }
//                }
//                run = false;
//            }
//            else {
//                outputLabel.setText(INPUT_ERROR_MESSAGE);
//            }
//        }
//    }
//
//
//
//    private void printInstructions() {
//        outputLabel.setText("<html>To add a new course type '" + NEW_COURSE_COMMAND + "'<br>" +
//                "To view your courses type '" + VIEW_COURSES_COMMAND + "'<br>" +
//                "To quit the program type '" + QUIT_COMMAND + "'");
//    }
//
//    private void newCourseInterface(Schedule schedule) {
//        Course course;
//
//        course = setCourseInterface(schedule);
//        setAssignmentInterface(course);
//        setTestInterface(course);
//
//        outputLabel.setText("New course '" + course.getCourseName() + "' added to schedule");
//    }
//
//    private Course setCourseInterface(Schedule schedule) {
//        boolean daysTimesMatch = false;
//        Scanner scanner = new Scanner(System.in);
//        int classPerWeek;
//        Course course = null;
//        String courseName;
//
//        outputLabel.setText("Please enter the name of your course:");
//        courseName = scanner.nextLine();
//
//        while(!daysTimesMatch) {
//            outputLabel.setText("Please enter how many days a week you have " +courseName+ ":");
//            classPerWeek = Integer.parseInt(scanner.nextLine());
//
//            List<String> courseDays = new ArrayList<>();
//            outputLabel.setText("Please enter which days of the week you have " + courseName + ":");
//            addInputToList(courseDays, classPerWeek);
//
//            List<String> courseTimes = new ArrayList<>();
//            outputLabel.setText("Please enter at what times you have " + courseName + ":");
//            addInputToList(courseTimes, classPerWeek);
//
//            try {
//                course = new Course(courseName, courseDays, courseTimes);
//                schedule.addCourse(course);
//                daysTimesMatch = true;
//            } catch (DaysTimesNotEqual dtne) {
//                System.out.println(dtne.getMessage());
//            }
//        }
//
//        return course;
//    }

    private void setAssignmentInterface(Course course) {
        String input;
        String courseAssignmentName;
        String courseAssignmentDate;
        Scanner scanner = new Scanner(System.in);

        outputLabel.setText("Does this course have any assignments?");
        input = scanner.nextLine();
        while (input.equals("yes")) {
            outputLabel.setText("Please enter the name of the assignment:");
            courseAssignmentName = scanner.nextLine();
            outputLabel.setText("Please enter the due date of the assignment:");
            courseAssignmentDate = scanner.nextLine();
            Assignment assignment = new Assignment(courseAssignmentName, courseAssignmentDate);
            course.addAssignment(assignment);
            outputLabel.setText("Does this course have any more assignments?");
            input = scanner.nextLine();
        }
    }

    private void setTestInterface(Course course) {
        String input;
        String courseTestName;
        String courseTestDate;
        Scanner scanner = new Scanner(System.in);

        outputLabel.setText("Does this course have any tests?");
        input = scanner.nextLine();
        while (input.equals("yes")) {
            outputLabel.setText("Please enter the name of the test:");
            courseTestName = scanner.nextLine();
            outputLabel.setText("Please enter the date of the test:");
            courseTestDate = scanner.nextLine();
            Test test = new Test(courseTestName, courseTestDate);
            course.addTest(test);
            outputLabel.setText("Does this course have any more tests?");
            input = scanner.nextLine();
        }
    }

//    private void viewCoursesInterface(Schedule schedule) {
//        Scanner scanner = new Scanner(System.in);
//        String operation;
//
//        outputLabel.setText("<html>Your courses are: " + schedule.getCourseNames() +"<br>"+
//                "To view a course type '" + VIEW_COURSE_COMMAND + "'" +
//                "To edit a course type '" +EDIT_COURSE_COMMAND+ "'" +
//                "To go back type '" +GO_BACK_COMMAND+ "'");
//        operation = scanner.nextLine();
//
//        if (operation.equals(VIEW_COURSE_COMMAND)){
//            viewCourseInterface(schedule);
//        }else if (operation.equals(EDIT_COURSE_COMMAND)){
//            editCourseInterface(schedule);
//        }else if (operation.equals(GO_BACK_COMMAND)){
//            return;
//        }else {
//            System.out.println(INPUT_ERROR_MESSAGE);
//        }
//    }

//    private void viewCourseInterface(Schedule schedule) {
//        Scanner scanner = new Scanner(System.in);
//        String courseName;
//
//        outputLabel.setText("Please type the name of your course:");
//        courseName = scanner.nextLine();
//
//        for (Course course: schedule.getCourses()) {
//            if (courseName.equals(course.getCourseName())){
//                course.printCourseDaysTimes();
//                course.printCourseAssignments();
//                course.printCourseTests();
//            }
//        }
//    }

//    private void editCourseInterface(Schedule schedule) {
//        Scanner scanner = new Scanner(System.in);
//        String courseName;
//        String operation;
//
//        outputLabel.setText("Please type the name of your course:");
//        courseName = scanner.nextLine();
//
//        for (Course course: schedule.getCourses()) {
//            if (courseName.equals(course.getCourseName())){
//                outputLabel.setText("To edit the name of " +courseName+ " type '" +EDIT_COURSE_NAME_COMMAND+ "'<br>" +
//                        "To edit the schedule of " +courseName+ " type '" +EDIT_COURSE_SCHEDULE_COMMAND+ "'<br>" +
//                        "To edit the assignments of " +courseName+ " type '" +EDIT_ASSIGNMENT_COMMAND+ "'<br>" +
//                        "To edit the tests of " +courseName+ " type '" +EDIT_TEST_COMMAND+ "'");
//                operation = scanner.nextLine();
//
//                if (operation.equals(EDIT_COURSE_NAME_COMMAND)){
//                    editCourseNameInterface(course);
//                }else if (operation.equals(EDIT_COURSE_SCHEDULE_COMMAND)){
//                    editCourseScheduleInterface(course);
//                }else if (operation.equals(EDIT_ASSIGNMENT_COMMAND)){
//                    editAssignmentsInterface(course);
//                }else if (operation.equals(EDIT_TEST_COMMAND)){
//                    editTestsInterface(course);
//                }else {
//                    System.out.println(INPUT_ERROR_MESSAGE);
//                }
//            }
//        }
//    }
//
//    private void editCourseNameInterface(Course course) {
//        Scanner scanner = new Scanner(System.in);
//        String courseName;
//
//        outputLabel.setText("Please enter the new name of " +course.getCourseName()+ ":");
//        courseName = scanner.nextLine();
//
//        course.setCourseName(courseName);
//    }

    private void editCourseScheduleInterface(Course course) {
        Scanner scanner = new Scanner(System.in);
        int classPerWeek;
        List<String> courseDays = new ArrayList<>();
        List<String> courseTimes = new ArrayList<>();
        boolean daysTimesMatch = false;

        while (!daysTimesMatch) {
            outputLabel.setText("Please enter how many days a week you have " + course.getCourseName() + ":");
            classPerWeek = Integer.parseInt(scanner.nextLine());
            outputLabel.setText("Please enter which days of the week you have " + course.getCourseName() + ":");
            addInputToList(courseDays, classPerWeek);
            outputLabel.setText("Please enter at what times you have " + course.getCourseName() + ":");
            addInputToList(courseTimes, classPerWeek);

            try {
                course.setCourseDaysAndTimes(courseDays, courseTimes);
                daysTimesMatch = true;
            } catch (DaysTimesNotEqual daysTimesNotEqual) {
                System.out.println(daysTimesNotEqual.getMessage());
            }
        }
    }

    private void editAssignmentsInterface(Course course) {
        Scanner scanner = new Scanner(System.in);
        String input;

        outputLabel.setText("To add an assignment type '" + ADD_COMMAND + "'");
        outputLabel.setText("To edit an assignment type '" + EDIT_COMMAND + "'");
        input = scanner.nextLine();

        if (input.equals(ADD_COMMAND)) {
            addAssignmentInterface(course);
        } else if (input.equals(EDIT_COMMAND)) {
            editAssignmentInterface(course);
        } else {
            System.out.println(INPUT_ERROR_MESSAGE);
        }
    }

    private void addAssignmentInterface(Course course) {
        Scanner scanner = new Scanner(System.in);
        String courseAssignmentName;
        String courseAssignmentDate;

        outputLabel.setText("Please enter the name of the assignment:");
        courseAssignmentName = scanner.nextLine();
        outputLabel.setText("Please enter the due date of the assignment:");
        courseAssignmentDate = scanner.nextLine();

        Assignment assignment = new Assignment(courseAssignmentName, courseAssignmentDate);
        course.addAssignment(assignment);
    }

    private void editAssignmentInterface(Course course) {
        Scanner scanner = new Scanner(System.in);
        String input;
        String assignmentName;

        outputLabel.setText("Please enter the name of the assignment you would like to edit:");
        assignmentName = scanner.nextLine();

        for (Assignment assignment : course.getCourseAssignments()) {
            if (assignment.getName().equals(assignmentName)) {
                outputLabel.setText("<html>To rename assignment type '" + RENAME_COMMAND + "'<br>" +
                        "To give assignment a new due date type '" + NEW_DATE_COMMAND + "'");
                input = scanner.nextLine();
                if (input.equals(RENAME_COMMAND)) {
                    outputLabel.setText("Please enter the new name of your assignment:");
                    String newAssignmentName = scanner.nextLine();
                    assignment.setName(newAssignmentName);
                    return;
                } else if (input.equals(NEW_DATE_COMMAND)) {
                    outputLabel.setText("Please enter the new date of your test:");
                    String newAssignmentDate = scanner.nextLine();
                    assignment.setDate(newAssignmentDate);
                    return;
                } else {
                    outputLabel.setText(INPUT_ERROR_MESSAGE);
                    return;
                }
            }
        }
        outputLabel.setText("Sorry, no such assignment found");
    }

    private void editTestsInterface(Course course) {
        Scanner scanner = new Scanner(System.in);
        String input;

        outputLabel.setText("To add a test type '" + ADD_COMMAND + "'");
        outputLabel.setText("To edit a test type '" + EDIT_COMMAND + "'");
        input = scanner.nextLine();

        if (input.equals(ADD_COMMAND)) {
            addTestInterface(course);
        } else if (input.equals(EDIT_COMMAND)) {
            editTestInterface(course);
        } else {
            System.out.println(INPUT_ERROR_MESSAGE);
        }
    }

    private void addTestInterface(Course course) {
        Scanner scanner = new Scanner(System.in);
        String courseTestName;
        String courseTestDate;

        outputLabel.setText("Please enter the name of the test:");
        courseTestName = scanner.nextLine();
        outputLabel.setText("Please enter the date of the test:");
        courseTestDate = scanner.nextLine();

        Test test = new Test(courseTestName, courseTestDate);
        course.addTest(test);
    }

    private void editTestInterface(Course course) {
        Scanner scanner = new Scanner(System.in);
        String input;

        outputLabel.setText("Please enter the name of the test you would like to edit:");
        String testName = scanner.nextLine();

        for (Test test : course.getCourseTests()) {
            if (test.getName().equals(testName)) {
                outputLabel.setText("<html>To rename test type '" + RENAME_COMMAND + "'<br>" +
                        "To give test a new due date type '" + NEW_DATE_COMMAND + "'");
                input = scanner.nextLine();
                if (input.equals(RENAME_COMMAND)) {
                    outputLabel.setText("Please enter the new name of your test:");
                    String newTestName = scanner.nextLine();
                    test.setName(newTestName);
                    return;
                } else if (input.equals(NEW_DATE_COMMAND)) {
                    outputLabel.setText("Please enter the new date of your test:");
                    String newTestDate = scanner.nextLine();
                    test.setDate(newTestDate);
                    return;
                } else {
                    outputLabel.setText(INPUT_ERROR_MESSAGE);
                    return;
                }
            }
        }
        outputLabel.setText("Sorry, no such test found");
    }

    private void addInputToList(List<String> inputList, int classPerWeek) {
        Scanner scanner = new Scanner(System.in);
        String input;

        for (int i = 0; i < classPerWeek; i++) {
            input = scanner.nextLine();
            inputList.add(input);
        }
    }
}