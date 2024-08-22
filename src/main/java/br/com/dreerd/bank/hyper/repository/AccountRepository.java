package br.com.dreerd.bank.hyper.repository;

import br.com.dreerd.bank.hyper.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByNumber(Long numberAccount);
}
