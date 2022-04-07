package com.gmail.vanyasudnishnikov.authorization.repository.impl;

import com.gmail.vanyasudnishnikov.authorization.repository.TokenBlacklistRepository;
import com.gmail.vanyasudnishnikov.authorization.repository.model.Employee;
import com.gmail.vanyasudnishnikov.authorization.repository.model.InvalidToken;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.PersistentObjectException;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Optional;

@Repository
@Slf4j
public class TokenBlacklistRepositoryImpl extends GenericRepositoryImpl<String, InvalidToken> implements TokenBlacklistRepository {
    @Override
    public Optional<InvalidToken> add(InvalidToken tokenBlacklist) {
        try {
            em.persist(tokenBlacklist);
            return Optional.of(tokenBlacklist);
        } catch (PersistentObjectException e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<InvalidToken> findByName(String token) {
        String hql = "select i from InvalidToken as i where i.jwtToken like :token";
        Query query = em.createQuery(hql);
        query.setParameter("token", "%" + token);
        try {
            InvalidToken invalidToken = (InvalidToken) query.getSingleResult();
            return Optional.ofNullable(invalidToken);
        } catch (NoResultException e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }
}
