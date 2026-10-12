package com.atlas.bank.atlas_bank.account.dto;

import com.atlas.bank.atlas_bank.account.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

//Indicamos que esta interfaz sera un componente de Spring
    //Esto para que podamos inyectar AccountMapper con Required Argument Constrcutor
@Mapper(componentModel = "spring")
public interface AccountMapper {

        //Indicamos que ignore estos campos, ya que el DTO de request no los solicita
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    Account toEntity(CreateAccountRequest request); //Solicitud

    AccountResponse toResponse(Account account); //DTO Respuesta del sistema


}
