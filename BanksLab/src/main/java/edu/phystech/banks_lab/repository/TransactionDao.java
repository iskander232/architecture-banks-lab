package edu.phystech.banks_lab.repository;

import java.util.List;

import edu.phystech.banks_lab.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDao extends JpaRepository<Transaction, Integer> {
    Transaction save(Transaction transaction);
    Transaction getById(int id);
    List<Transaction> findAllByIdIsNotNull();
}
