package com.suttori.dao;

import com.suttori.models.Buy;
import com.suttori.models.Product;
import com.suttori.models.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProductDAO {

    private final JdbcTemplate jdbcTemplate;

    public ProductDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> indexProduct() {
        return jdbcTemplate.query("SELECT * FROM Product", new BeanPropertyRowMapper<>(Product.class));
    }

    public Product showProduct(int id) {
        return jdbcTemplate.query("SELECT * FROM Product WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Product.class)).stream().findAny().orElse(null);
    }

    public Optional<Product> showId(int id) {
        return jdbcTemplate.query("SELECT * FROM Product WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Product.class)).stream().findAny();
    }

    public void saveProduct(Product product) {
        jdbcTemplate.update("INSERT INTO Product (name, price) VALUES (?, ?)",
                product.getName(), product.getPrice());
    }

    public void deleteProduct(int id) {
        jdbcTemplate.update("DELETE FROM Product WHERE id=?", id);
    }

    public void buy(Buy buy) {
        jdbcTemplate.update("INSERT INTO Buy (userId, productId) VALUES (?, ?)",
                buy.getUserId(), buy.getProductId());
    }

    public Integer takePrice(int id) {
        return jdbcTemplate.queryForObject("SELECT price FROM Product WHERE id=?", new Object[]{id},
                (Integer.class));
    }

    public ArrayList<String> display(int id) {
        return (ArrayList<String>) jdbcTemplate.queryForList("SELECT firstname FROM buy JOIN users ON users.id = buy.userid WHERE buy.productid=?",
                new Object[]{id}, (String.class));
    }
}