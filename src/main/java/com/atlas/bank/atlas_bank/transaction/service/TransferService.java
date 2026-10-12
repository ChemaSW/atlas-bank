package com.atlas.bank.atlas_bank.transaction.service;

import com.atlas.bank.atlas_bank.account.exception.AccountNotFoundException;
import com.atlas.bank.atlas_bank.account.model.Account;
import com.atlas.bank.atlas_bank.transaction.exception.AccountNotActiveException;
import com.atlas.bank.atlas_bank.transaction.exception.InsufficientFundsException;
import com.atlas.bank.atlas_bank.transaction.model.Transaction;
import com.atlas.bank.atlas_bank.account.repository.AccountRepository;
import com.atlas.bank.atlas_bank.transaction.repository.TransactionRepository;
import com.atlas.bank.atlas_bank.transaction.service.fee.FeeCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferService implements ITransferService{

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final List<FeeCalculator> feeCalculators; //Podemos recorrer todas las implementaciones de esta Interfaz
                                    //Programar contra una interfaz significa depender del "qué puede hacer" un objeto, no de "qué clase exacta es".

                                    //-- God-Services --
    @Override
    @Transactional
    public Transaction execute(Long fromId, Long toId, BigDecimal amount){ //cuenta origen a cuenta destino, monto, metodo de transferir dinero
        //buscar cuentas
        Account from = accountRepository.findById(fromId)
                .orElseThrow(() -> new AccountNotFoundException(fromId));

        Account to = accountRepository.findById(toId)
                .orElseThrow(() -> new AccountNotFoundException(toId));


        // validar que la cuenta este activa por mefio de string
        if(!"ACTIVE".equals(from.getStatus())){
            throw new AccountNotActiveException(fromId, from.getStatus());
        }
        if(!"ACTIVE".equals(to.getStatus())){
            throw new AccountNotActiveException(toId, to.getStatus());
        }

        // validar los fondos
        if(from.getBalance().compareTo(amount) < 0){ //si la cuentade origen comparado con el monto que quiero transferir me da un numero negativo
            throw new InsufficientFundsException(fromId, from.getBalance(), amount);
        }

        // calcular comisiones // este metodo respeta un principio SOLID, el de O (Abierto/Cerrado)
        BigDecimal fee = feeCalculators.stream()
                .filter( fc -> fc.supports(from.getType())) //support es una validación boleana, en este caso determinara a que tipo de cuenta pertenece
                .findFirst()
                .orElseThrow( () -> new RuntimeException("No hay calculador para el tipo "+ from.getType())) //Error de sistema, no es un error del usuario, se puede considerar como un bug
                .calculate(amount);//realiza el calculo correspondiente de acuerdo a la cuenta que se filtro

        // actualizacion de saldos
        from.setBalance(from.getBalance().subtract(amount).subtract(fee));
        to.setBalance(to.getBalance().add(amount));
        accountRepository.save(from);
        accountRepository.save(to);

        // crear transaccion
        Transaction transaction = new Transaction();
        transaction.setType("TRANSFER");
        transaction.setSourceAccountId(fromId);
        transaction.setTargetAccountId(toId);
        transaction.setAmount(amount);
        transaction.setFee(fee);
        transaction.setStatus("EXECUTED");

        return transactionRepository.save(transaction);
    }

}
