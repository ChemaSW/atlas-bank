package com.atlas.bank.atlas_bank.transaction.repository;

import com.atlas.bank.atlas_bank.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

        // busque por ID de cuenta de origen o ID de cuenta de destino
    List<Transaction> findBySourceAccountIdOrTargetAccountId(Long sourceId, Long targetId);

}
