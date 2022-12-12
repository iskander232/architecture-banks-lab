package edu.phystech.banks_lab.repository;

import java.util.List;

import edu.phystech.banks_lab.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankDao extends JpaRepository<Bank, Integer> {
    Bank save(Bank bank);
    Bank getById(int id);
    List<Bank> findAllByIdIsNotNull();
}
