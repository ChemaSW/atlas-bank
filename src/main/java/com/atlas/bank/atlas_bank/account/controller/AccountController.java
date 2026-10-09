package com.atlas.bank.atlas_bank.account.controller;

import com.atlas.bank.atlas_bank.account.dto.AccountResponse;
import com.atlas.bank.atlas_bank.account.dto.CreateAccountRequest;
import com.atlas.bank.atlas_bank.account.model.Account;
import com.atlas.bank.atlas_bank.account.service.IAccountService;
import com.atlas.bank.atlas_bank.transaction.service.ITransactionQueryService;
import com.atlas.bank.atlas_bank.transaction.service.ITransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")

@RequiredArgsConstructor
public class AccountController {

    private final IAccountService accountService;
    private final ITransferService transferService;
    private final ITransactionQueryService transactionQueryService;

    //ResponseEntity - devuelve una respuesta HTTP completa, desde el contenido hasta el status code y body

    @PostMapping            // @RequestBody -> Recibe un cuerpo JSON - ¿Que informacion estoy enviando?
    public ResponseEntity<AccountResponse> create(@RequestBody CreateAccountRequest request) {
        //Creacion de entity
        Account account = new Account();
            //Seteamos campos que vienen de la request
        account.setAccountNumber(request.getAccountNumber());
        account.setOwnerName(request.getOwnerName());
        account.setEmail(request.getEmail());
        account.setType(request.getType());
        account.setBalance(request.getBalance());
            //Realizamos el create del servicio
        Account saved = accountService.create(account);
            //Exposicion del objeto
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(saved));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> findAll() {
        //return ResponseEntity.ok(accountService.findAll());
        List<AccountResponse> responses = accountService.findAll()
                .stream()
                .map(this::toResponse) //transformamos los elementos en base a toResponse
                .toList(); //retornamos una lista
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")            //@PathVariable que recurso quiero, por lo regular se usan en busquedas de id
    public ResponseEntity<AccountResponse> findById(@PathVariable Long id) {
        //return ResponseEntity.ok(accountService.findById(id));
        return ResponseEntity.ok(toResponse(accountService.findById(id))); //recibe el metodo ya que devuelve un Account
    }

        //MApeado del dto con la entidad, para no exponer la entidad
    private AccountResponse toResponse(Account account){
        AccountResponse response = new AccountResponse();
        response.setId(account.getId());
        response.setAccountNumber(account.getAccountNumber());
        response.setOwnerName(account.getOwnerName());
        response.setEmail(account.getEmail());
        response.setType(account.getType());
        response.setBalance(account.getBalance());
        response.setStatus(account.getStatus());
        response.setCreateAt(account.getCreateAt());
        return response;
    }

}



















