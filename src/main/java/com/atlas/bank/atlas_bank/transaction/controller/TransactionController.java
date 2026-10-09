package com.atlas.bank.atlas_bank.transaction.controller;

import com.atlas.bank.atlas_bank.transaction.dto.TransferRequest;
import com.atlas.bank.atlas_bank.transaction.dto.TransactionResponse;
import com.atlas.bank.atlas_bank.transaction.model.Transaction;
import com.atlas.bank.atlas_bank.transaction.service.ITransactionQueryService;
import com.atlas.bank.atlas_bank.transaction.service.ITransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final ITransferService transferService;
    private final ITransactionQueryService transactionQueryService;

//    @PostMapping("/transfer")           //@RequestParam es comun en filtros, búsquedas, paginación, ordenamiento y opciones - ¿Como quiero obtener el objeto?
//    public ResponseEntity<Transaction> transfer(@RequestParam Long fromId,
//                                                @RequestParam Long toId,
//                                                @RequestParam BigDecimal amount){
//        return ResponseEntity.ok(transferService.execute(fromId, toId, amount));
//    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(@RequestBody TransferRequest request){
        Transaction transaction = transferService.execute(//metodo del service
                request.getFromAccountId(),
                request.getToAccountId(),
                request.getAmount()
        );
        return ResponseEntity.ok(toResponse(transaction));
    }

    @GetMapping("/{id}/transactions") //Retorna una lista de transacciones
    public ResponseEntity<List<TransactionResponse>> getTransactions(@PathVariable Long accountId){
        //return ResponseEntity.ok(transactionQueryService.getByAccountId(accountId));
        List<TransactionResponse> response = transactionQueryService.getByAccountId(accountId)
                .stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

        //Mapeado de DTO en base al Enitity
    private TransactionResponse toResponse(Transaction transaction){
        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());
        response.setType(transaction.getType());
        response.setSourceAccountId(transaction.getSourceAccountId());
        response.setTargetAccountId(transaction.getTargetAccountId());
        response.setAmount(transaction.getAmount());
        response.setFee(transaction.getFee());
        response.setStatus(transaction.getStatus());
        response.setCreatedAt(transaction.getCreatedAt());
        return response;
    }
}
