package com.kstarrain.dao.impl;

import com.kstarrain.dao.StudentDao;
import com.kstarrain.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author: DongYu
 * @create: 2019-01-05 16:41
 * @description:
 */

@Repository
public class StudentDaoImpl implements StudentDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    private RowMapper<Student> getStudentRowMapper(){

        RowMapper<Student> rowMapper = new RowMapper<Student>() {
            @Override
            public Student mapRow(ResultSet resultSet, int index) throws SQLException {
                Student student = new Student();
                student.setId(resultSet.getString("ID"));
                student.setName(resultSet.getString("NAME"));
                student.setMoney(resultSet.getBigDecimal("MONEY"));
                student.setBirthday(resultSet.getDate("BIRTHDAY"));
                student.setCreateDate(resultSet.getTimestamp("CREATE_DATE"));
                student.setUpdateDate(resultSet.getTimestamp("UPDATE_DATE"));
                student.setAliveFlag(resultSet.getString("ALIVE_FLAG"));
                return student;
            }
        };
        return rowMapper;
    }

    /** sql 注入*/
    @Override
    public List<Student> findStudentByName(String name) {

        String sql = "select * from t_student where ALIVE_FLAG = '1' and NAME = '" + name + "'";
        List<Student> students = jdbcTemplate.query(sql, this.getStudentRowMapper());
        return students;

    }


    /** 避免sql 注入*/
    @Override
    public List<Student> findStudentByName2(String name) {

        String sql = "select * from t_student where ALIVE_FLAG = '1' and NAME = ?";
        Object[] params = new Object[1];
        params[0] = name;

        List<Student> students = jdbcTemplate.query(sql, params,this.getStudentRowMapper());
        return students;

    }


    @Override
    public List<Student> findAllStudent() {

        String sql = "select * from t_student where ALIVE_FLAG = '1'" ;
        List<Student> students = jdbcTemplate.query(sql, this.getStudentRowMapper());
        return students;
    }

    @Override
    public int insertStudent(Student student) {

        String sql = "insert into t_student(ID, NAME, BIRTHDAY, MONEY, CREATE_DATE, UPDATE_DATE, ALIVE_FLAG)values(?,?,?,?,?,?,?)";

        int update = jdbcTemplate.update(sql, student.getId(),
                student.getName(),
                student.getBirthday(),
                student.getMoney(),
                student.getCreateDate(),
                student.getUpdateDate(),
                student.getAliveFlag());
        return update;
    }
}
