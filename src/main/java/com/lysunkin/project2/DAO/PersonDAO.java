package com.lysunkin.project2.DAO;

import com.lysunkin.project2.models.Person;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PersonDAO {
    private final SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public List<Person> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Person", Person.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Person findById(int person_id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, person_id);
    }

    @Transactional
    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(person);
    }

    @Transactional
    public void update(Person person) {
        Session session = sessionFactory.getCurrentSession();
        Person personToBeUpdated = session.get(Person.class, person.getPerson_id());
        personToBeUpdated.setPerson_name(person.getPerson_name());
        personToBeUpdated.setBirthday(person.getBirthday());
    }

    @Transactional
    public void deleteById(int person_id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, person_id);
        person.getBooks().forEach(x -> x.setOwner(null));
        session.remove(person);
    }

    public Optional<Person> findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query<Person> query = session.createQuery("FROM Person WHERE person_name=:name", Person.class).setParameter("name", name);
        return Optional.ofNullable(query.getSingleResultOrNull());
    }
}
