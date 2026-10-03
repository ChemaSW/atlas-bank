package com.atlas.bank.atlas_bank.transaction.controller;

import com.atlas.bank.atlas_bank.transaction.model.Transaction;
import com.atlas.bank.atlas_bank.transaction.service.ITransactionQueryService;
import com.atlas.bank.atlas_bank.transaction.service.ITransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final ITransferService transferService;
    private final ITransactionQueryService transactionQueryService;

    @PostMapping("/transfer")           //@RequestParam es comun en filtros, búsquedas, paginación, ordenamiento y opciones - ¿Como quiero obtener el objeto?
    public ResponseEntity<Transaction> transfer(@RequestParam Long fromId,
                                                @RequestParam Long toId,
                                                @RequestParam BigDecimal amount){
        return ResponseEntity.ok(transferService.execute(fromId, toId, amount));
    }

    @GetMapping("/{id}/transactions") //Retorna una lista de transacciones
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable Long accountId){
        return ResponseEntity.ok(transactionQueryService.getByAccountId(accountId));
    }
}
