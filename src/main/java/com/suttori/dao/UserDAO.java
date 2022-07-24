package com.suttori.dao;

import com.suttori.models.Buy;
import com.suttori.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class UserDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> index() {
        return jdbcTemplate.query("SELECT * FROM Users", new BeanPropertyRowMapper<>(User.class));
    }

    public User show(int id) {
        return jdbcTemplate.query("SELECT * FROM Users WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(User.class)).stream().findAny().orElse(null);
    }

    public Optional<User> showId(int id) {
        return jdbcTemplate.query("SELECT * FROM Users WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(User.class)).stream().findAny();
    }

    public void save(User user) {
        jdbcTemplate.update("INSERT INTO Users (firstName, lastName, amountOfMoney) VALUES (?, ?, ?)",
                user.getFirstName(), user.getLastName(), user.getAmountOfMoney());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Users WHERE id=?", id);
    }

    public Integer takeAmountOfMoney(int id) {
        return jdbcTemplate.queryForObject("SELECT amountOfMoney FROM Users WHERE id=?", new Object[]{id},
                (Integer.class));
    }

    public void update(int id, int result) {
        jdbcTemplate.update("UPDATE Users SET amountOfMoney=? WHERE id=?", result, id);
    }

    public ArrayList<String> display(int id) {
        return (ArrayList<String>) jdbcTemplate.queryForList("SELECT name FROM buy JOIN product p ON p.id = buy.productid WHERE buy.userid=?",
                new Object[]{id}, (String.class));
    }
}
