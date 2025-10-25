package com.example.University;

import java.util.ArrayList;

public class Student {

    private String name;
    private ArrayList<Course> courses = new ArrayList<Course>();

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addCourse(Course c) {
        courses.add(c);
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public boolean hasAnyDebt() {
        for (int i = 0; i < courses.size(); i++) {
            if (!courses.get(i).isPassed()) {
                return true;
            }
        }
        return false;
    }

    public double averagePassed() {
        double sum = 0.0;
        int cnt = 0;
        for (int i = 0; i < courses.size(); i++) {
            Course c = courses.get(i);
            if (c.isPassed()) {
                sum += c.getFinalGrade();
                cnt++;
            }
        }
        if (cnt == 0) {
            return Double.NaN;
        }
        return sum / cnt;
    }

    public ArrayList<Course> coursesInSemester(int sem) {
        ArrayList<Course> res = new ArrayList<Course>();
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getSemester() == sem) {
                res.add(courses.get(i));
            }
        }
        return res;
    }
}
