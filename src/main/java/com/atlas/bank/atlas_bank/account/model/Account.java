package com.atlas.bank.atlas_bank.account.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//@Data puede tener problematica en una @Entity si tiene relaciones (One to One, One to many...ect)
    // ya que genera el equal() and hashCode - Puede ocasionar problemas indeseados,
    // como lo que se denomina el Lazy loading involuntario. Si tenemos una relación puede acceder
    // a esos campos y eso puede disparar consultas a la BD sin que nosotros lo esperemos
@Entity
//@Data
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include //Solo incluimos este campo en el equals and hashCode
    private Long id;
    private String accountNumber;
    private String ownerName;
    private String email;
    private String type; // Saving, Cheking (cuenta de ahorro, cuenta corriente)
    private BigDecimal balance;
    private String status; // Active, Closed, Frozen
    private LocalDateTime createAt;

        // Callback para asignar valores por Defecto al momento de crear un objeto, solo para @Entity, no se puede implementar en DTOs/VO
    @PrePersist //Notación de JPA
    public void prePersist(){
        this.createAt = LocalDateTime.now();
        if(status == null) status = "ACTIVE";
        if(balance == null) balance = BigDecimal.ZERO;
    }

}
