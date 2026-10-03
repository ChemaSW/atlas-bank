package com.atlas.bank.atlas_bank.account.repository;

import com.atlas.bank.atlas_bank.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

//Entidad y ID de dicha entidad
public interface AccountRepository extends JpaRepository<Account, Long> {

}
