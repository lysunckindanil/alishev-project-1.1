package com.lysunkin.project2.DAO;

import com.lysunkin.project2.models.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setBook_id(rs.getInt("book_id"));
        book.setBook_name(rs.getString("book_name"));
        book.setAuthor(rs.getString("author"));
        book.setYear(rs.getInt("year"));
        return book;
    }
}
