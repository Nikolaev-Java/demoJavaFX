package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.repository.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InDataBaseCourseService implements CourseService {
    @Override
    public int addCourse(Course course) {
        String sql = "insert into courses (name,description)" +
                "values(?,?)";
        ResultSet rs;
        try (PreparedStatement pstmt = DbConnection.getConnection().prepareStatement(sql,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, course.getName());
            pstmt.setString(2, course.getDescription());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public List<Course> getCourses() {
        String sql = "select * from courses";
        ResultSet rs;
        try (PreparedStatement pstmt = DbConnection.getConnection().prepareStatement(sql)) {
            rs = pstmt.executeQuery();
            List<Course> courses = new ArrayList<>();
            while (rs.next()) {
                Course course = new Course(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3));
                courses.add(course);
            }
            return courses;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Course getCourse(int id) {
        String sql = "select * from courses where id_course=?";
        ResultSet rs;
        try (PreparedStatement pstmt = DbConnection.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Course(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void updateCourse(Course course) {
        String sql = "update courses set name=?,description=? where id_course=?";
        ResultSet rs;
        try (PreparedStatement pstmt = DbConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, course.getName());
            pstmt.setString(2, course.getDescription());
            pstmt.setInt(3, course.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteCourse(int id) {
        String sql = "delete from courses where id_course=?";
        try (PreparedStatement pstmt = DbConnection.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
