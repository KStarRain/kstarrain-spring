package com.kstarrain.dao.impl;

import com.kstarrain.dao.StudentDao;
import com.kstarrain.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author: DongYu
 * @create: 2019-01-05 16:41
 * @description:
 */

@Repository
public class StudentDaoImpl implements StudentDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public int insertStudent(Student student) {

        String sql = "insert into t_student(ID, NAME, BIRTHDAY, MONEY, CREATE_DATE, UPDATE_DATE, ALIVE_FLAG)values(?,?,?,?,?,?,?)";

        int update = jdbcTemplate.update(sql,   student.getId(),
                                                student.getName(),
                                                student.getBirthday(),
                                                student.getMoney(),
                                                student.getCreateDate(),
                                                student.getUpdateDate(),
                                                student.getAliveFlag());
        return update;
    }



}
