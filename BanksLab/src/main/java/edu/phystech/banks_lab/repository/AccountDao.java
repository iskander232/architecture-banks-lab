package edu.phystech.banks_lab.repository;

import java.util.List;

import edu.phystech.banks_lab.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDao extends JpaRepository<Account, Integer> {
    Account save(Account account);
    void deleteById(int id);
    Account getById(int id);
    List<Account> findAllByIdIsNotNull();
}
