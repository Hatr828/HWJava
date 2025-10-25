package com.example.University;

import java.util.ArrayList;

public class Group {

    private String name;
    private ArrayList<Student> students = new ArrayList<Student>();

    public Group(String name) {
        this.name = name;
    }

    public void addStudent(Student s) {
        students.add(s);
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<Student> studentsWithAnyDebt() {
        ArrayList<Student> res = new ArrayList<Student>();
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).hasAnyDebt()) {
                res.add(students.get(i));
            }
        }
        return res;
    }

    public ArrayList<Student> studentsWithoutDebts() {
        ArrayList<Student> res = new ArrayList<Student>();
        for (int i = 0; i < students.size(); i++) {
            if (!students.get(i).hasAnyDebt()) {
                res.add(students.get(i));
            }
        }
        return res;
    }

    public String[] coursesWithMaxFails() {
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Integer> counts = new ArrayList<Integer>();

        for (int i = 0; i < students.size(); i++) {
            ArrayList<Course> cs = students.get(i).getCourses();
            for (int j = 0; j < cs.size(); j++) {
                Course c = cs.get(j);
                if (!c.isPassed()) {
                    int idx = indexOfName(names, c.getName());
                    if (idx == -1) {
                        names.add(c.getName());
                        counts.add(1);
                    } else {
                        counts.set(idx, counts.get(idx) + 1);
                    }
                }
            }
        }

        if (names.size() == 0) {
            return new String[0];
        }

        int mx = counts.get(0);
        for (int i = 1; i < counts.size(); i++) {
            if (counts.get(i) > mx) {
                mx = counts.get(i);
            }
        }

        ArrayList<String> res = new ArrayList<String>();
        for (int i = 0; i < names.size(); i++) {
            if (counts.get(i) == mx) {
                res.add(names.get(i));
            }
        }

        String[] out = new String[res.size()];
        for (int i = 0; i < res.size(); i++) {
            out[i] = res.get(i);
        }
        return out;
    }

    private int indexOfName(ArrayList<String> arr, String name) {
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).equals(name)) {
                return i;
            }
        }
        return -1;
    }
}
