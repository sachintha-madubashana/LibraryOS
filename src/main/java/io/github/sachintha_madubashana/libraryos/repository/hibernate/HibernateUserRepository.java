package io.github.sachintha_madubashana.libraryos.repository.hibernate;

import io.github.sachintha_madubashana.libraryos.database.HibernateSessionFactory;
import io.github.sachintha_madubashana.libraryos.database.entity.UserEntity;
import io.github.sachintha_madubashana.libraryos.model.User;
import io.github.sachintha_madubashana.libraryos.repository.UserRepository;
import org.hibernate.Session;

import java.util.Optional;

public class HibernateUserRepository implements UserRepository{
    @Override
    public Optional<User> findUser(User user) {
        try(Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            var query = session.createQuery("FROM UserEntity WHERE username = :username", UserEntity.class);
            query.setParameter("username", user.getUsername());
            var userEntity = query.uniqueResult();
            if (userEntity != null) {
                return Optional.of(toDto(userEntity));
            }
        }
        return Optional.empty();
    }

    private User toDto(UserEntity userEntity) {
        return new User(userEntity.getUsername(), userEntity.getPassword());
    }
}
