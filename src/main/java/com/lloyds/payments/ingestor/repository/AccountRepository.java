package com.lloyds.payments.ingestor.repository;

import com.lloyds.payments.ingestor.entity.AccountEntity;
import com.lloyds.payments.ingestor.util.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, String>, JpaSpecificationExecutor<AccountEntity> {

    Optional<AccountEntity> findByAccountId(String accountId);

    Optional<AccountEntity> findByAccountIdAndStatus(
            String accountId,
            AccountStatus status);
}
