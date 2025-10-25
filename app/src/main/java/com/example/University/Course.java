package com.example.University;

public class Course {

    private String name;
    private int semester;
    private int finalGrade;
    private boolean passed;

    public Course(String name, int semester, int finalGrade, boolean passed) {
        this.name = name;
        this.semester = semester;
        this.finalGrade = finalGrade;
        this.passed = passed;
    }

    public String getName() {
        return name;
    }

    public int getSemester() {
        return semester;
    }

    public int getFinalGrade() {
        return finalGrade;
    }

    public boolean isPassed() {
        return passed;
    }
}
