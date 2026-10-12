package com.atlas.bank.atlas_bank.transaction.dto;

import com.atlas.bank.atlas_bank.transaction.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

//Indicamos que es un componente de Spring para realizar una inyección
@Mapper(componentModel = "spring")
public interface TransactionMapper {

        //No se realiza este metodo ya que no tiene info relevante/confidencial del usuario
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "type", ignore = true)
//    @Mapping(target = "fee", ignore = true)
//    @Mapping(target = "status", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    Transaction toEntity(TransferRequest request);

        //Como el transaction es igual al response y no hay nada de info confidencial, se puede pasar igual
    TransactionResponse toResponse(Transaction transaction);

}
