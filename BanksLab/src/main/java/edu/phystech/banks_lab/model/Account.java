package edu.phystech.banks_lab.model;

import edu.phystech.banks_lab.repository.AccountDao;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "accounts")
public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Nonnull
    int clientId;
    @Nonnull
    int bankId;

    @Nonnull
    int accountValue;

    @Nonnull
    int bufferedValue;
    @Nonnull
    long bufferedCommissionStartDate;
    @Nonnull
    long depositCanTransferDate;

    @Nonnull
    String type;
    @Nonnull
    long updateTime;
}
