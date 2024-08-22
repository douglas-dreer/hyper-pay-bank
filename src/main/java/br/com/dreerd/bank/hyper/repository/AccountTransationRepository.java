package br.com.dreerd.bank.hyper.repository;

import br.com.dreerd.bank.hyper.entity.AccountTransation;
import br.com.dreerd.bank.hyper.enums.TransitionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AccountTransationRepository extends JpaRepository<AccountTransation, UUID> {
    List<AccountTransation> findAllByAccountId(UUID accountId);
    List<AccountTransation> findAllByAccountIdAndCreatedAtBetween(UUID accountId, LocalDateTime startedAt, LocalDateTime endedAt);
    List<AccountTransation> findAllByAccountIdAndTransitionType(UUID accountId, TransitionType transitionType);
}
