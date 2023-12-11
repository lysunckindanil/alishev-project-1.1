package com.lysunkin.project2.DAO;

import com.lysunkin.project2.models.Book;
import com.lysunkin.project2.models.Person;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookDAO {
    private final SessionFactory sessionFactory;

    public List<Book> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Book", Book.class).getResultList();
    }

    public Book findById(int book_id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class, book_id);
    }

    @Transactional
    public void save(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(book);
    }

    public List<Book> findAllByPersonId(int person_id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, person_id).getBooks();
    }

    @Transactional
    public void update(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(book);
    }

    @Transactional
    public void updateBookPerson(int book_id, int person_id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, book_id);
        if (book.getOwner() != null)
            book.getOwner().getBooks().removeIf(x -> x.getBook_id().equals(book_id));
        Person person = session.get(Person.class, person_id);
        person.addBook(book);
    }

    @Transactional
    public void deleteBookPerson(int book_id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, book_id);
        if (book.getOwner() != null)
            book.getOwner().getBooks().removeIf(x -> x.getBook_id().equals(book_id));
        book.setOwner(null);
    }

    @Transactional
    public void deleteById(int book_id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Book.class, book_id));
    }
}
