package edu.phystech.banks_lab.repository;

import java.util.List;

import edu.phystech.banks_lab.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDao extends JpaRepository<Client, Integer> {
    Client save(Client client);
    Client getById(int id);
    List<Client> findAllByIdIsNotNull();
}
