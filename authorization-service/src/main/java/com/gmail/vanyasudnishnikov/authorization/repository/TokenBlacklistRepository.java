package com.gmail.vanyasudnishnikov.authorization.repository;

import com.gmail.vanyasudnishnikov.authorization.repository.model.InvalidToken;

public interface TokenBlacklistRepository extends GenericRepository<String, InvalidToken> {
}
