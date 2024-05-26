package com.example.demo.service;

import com.example.demo.model.Student;
import com.example.demo.repository.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InDataBaseStudentService implements StudentService {
    @Override
    public int addStudent(Student student) {
        String sql = "insert into students (NAME,LASTNAME,ID_COURSE)\n" +
                "values(?,?,(select ID_COURSE from COURSES where COURSES.NAME=?))";
        try (PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement(sql,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getLastname());
            preparedStatement.setString(3, student.getCourseName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public List<Student> getStudents() {
        String sql = "SELECT id_student, students.name, lastname, C.name  COURSE\n" +
                "FROM STUDENTS\n" +
                "JOIN PUBLIC.COURSES C on C.ID_COURSE = students.ID_COURSE";
        ResultSet rs;
        try (PreparedStatement pstmt = DbConnection.getConnection().prepareStatement(sql)) {
            rs = pstmt.executeQuery();
            ArrayList<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student student = new Student(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));
                students.add(student);
            }
            return students;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Student getStudent(int id) {
        String sql = "SELECT id_student, students.name, lastname, C.name  COURSE\n" +
                "FROM STUDENTS\n" +
                "JOIN PUBLIC.COURSES C on C.ID_COURSE = students.ID_COURSE\n" +
                "WHERE id_student = ?\n";
        ResultSet rs;
        try (PreparedStatement pstmt = DbConnection.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Student(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void updateStudent(Student student) {
        String sql = "update STUDENTS set NAME=?," +
                "LASTNAME=?," +
                "ID_COURSE=(select ID_COURSE from COURSES where COURSES.NAME=?)" +
                "where ID_STUDENT=?";
        try (PreparedStatement pstmt = DbConnection.getConnection().prepareStatement(sql)) {
            pstmt.setInt(4, student.getId());
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getLastname());
            pstmt.setString(3, student.getCourseName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteStudent(int id) {
        String sql = "DELETE FROM STUDENTS WHERE id_student = ?";
        try (PreparedStatement pstmt = DbConnection.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
