package com.lysunkin.project2.DAO;


import com.lysunkin.project2.models.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonRowMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();
        person.setPerson_id(rs.getInt("person_id"));
        person.setPerson_name(rs.getString("person_name"));
        person.setBirthday(rs.getDate("birthday"));
        return person;
    }
}
