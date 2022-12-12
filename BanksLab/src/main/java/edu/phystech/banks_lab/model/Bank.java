package edu.phystech.banks_lab.model;

import java.util.List;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "banks")
public class Bank {

    @Id
    @GeneratedValue
    Integer id;

    @Nonnull
    int debitCommission;
    @Nonnull
    int creditCommission;
    @Nonnull
    int creditLimit;
    @Nonnull
    String depositValue;

    @Nonnull
    int precent;

    @Nonnull
    int unauthorizedLimit;
}