package com.example.demo.service;

import com.example.demo.model.Result;
import com.example.demo.model.Student;
import com.example.demo.repository.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InDataBaseResultService implements ResultService {
    @Override
    public void addScore(Student student, int score) {
        String sql = "insert into RESULTS(id_student,id_course,score) values(?,(select id_course from courses where name=?),?)";
        try (PreparedStatement pstmt = DbConnection.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, student.getId());
            pstmt.setString(2, student.getCourseName());
            pstmt.setInt(3, score);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteScore(Student student) {
        String sql = "delete from RESULTS where id_student=?";
        try (PreparedStatement pstmt = DbConnection.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, student.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateScore(Student student, int score) {
        String sql = "update RESULTS set score=? where id_student=?";
        try (PreparedStatement pstmt = DbConnection.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, score);
            pstmt.setInt(2, student.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Result> getResults() {
        String sql = "select S.id_student,S.name,S.lastname,C.name,score from PUBLIC.STUDENTS S\n" +
                "JOIN COURSES C ON C.id_course=S.ID_COURSE\n" +
                "JOIN RESULTS R ON R.id_student=S.id_student ";
        ResultSet rs;
        List<Result> results = new ArrayList<>();
        try (PreparedStatement pstmt = DbConnection.getConnection().prepareStatement(sql)) {
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Student student = new Student(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));
                Result result = new Result(student.getCourseName(), student, rs.getInt(5));
                results.add(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return results;
    }

    @Override
    public Result getResultByStudent(Student student) {
        return null;
    }
}
