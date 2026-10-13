package com.atlas.bank.atlas_bank.account.controller;

import com.atlas.bank.atlas_bank.account.dto.AccountMapper;
import com.atlas.bank.atlas_bank.account.dto.AccountResponse;
import com.atlas.bank.atlas_bank.account.dto.CreateAccountRequest;
import com.atlas.bank.atlas_bank.account.model.Account;
import com.atlas.bank.atlas_bank.account.service.IAccountService;
import com.atlas.bank.atlas_bank.transaction.service.ITransactionQueryService;
import com.atlas.bank.atlas_bank.transaction.service.ITransferService;
import jakarta.validation.Valid;
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
    private final AccountMapper accountMapper;

    //ResponseEntity - devuelve una respuesta HTTP completa, desde el contenido hasta el status code y body

    @PostMapping            // @RequestBody -> Recibe un cuerpo JSON - ¿Que informacion estoy enviando?
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody CreateAccountRequest request) {
        //Creacion de entity                    //@Valid se pone antes del RequestBody
        Account account = accountMapper.toEntity(request);

            //Realizamos el create del servicio
        Account saved = accountService.create(account);
            //Exposicion del objeto
        return ResponseEntity.status(HttpStatus.CREATED).body(accountMapper.toResponse(saved));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> findAll() {
        //return ResponseEntity.ok(accountService.findAll());
        List<AccountResponse> responses = accountService.findAll()
                .stream()
                .map(accountMapper::toResponse) //transformamos los elementos en base a toResponse
                .toList(); //retornamos una lista
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")            //@PathVariable que recurso quiero, por lo regular se usan en busquedas de id
    public ResponseEntity<AccountResponse> findById(@PathVariable Long id) {
        //return ResponseEntity.ok(accountService.findById(id));
        return ResponseEntity.ok(accountMapper.toResponse(accountService.findById(id))); //recibe el metodo ya que devuelve un Account
    }

}



















