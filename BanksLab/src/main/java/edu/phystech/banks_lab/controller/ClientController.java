package edu.phystech.banks_lab.controller;

import java.util.Collection;

import edu.phystech.banks_lab.controller.model.ClientAddAddressRequest;
import edu.phystech.banks_lab.controller.model.ClientAddPassportNumberRequest;
import edu.phystech.banks_lab.controller.model.CreateClientRequest;
import edu.phystech.banks_lab.exception.BusinessException;
import edu.phystech.banks_lab.model.Client;
import edu.phystech.banks_lab.repository.ClientDao;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static edu.phystech.banks_lab.exception.BusinessException.Reason.NO_SUCH_CLIENT_EXCEPTION;

@RestController
@RequestMapping("clients")
@AllArgsConstructor
public class ClientController {

    ClientDao clientDao;

    @PostMapping
    @ResponseBody
    public Client create(@RequestBody CreateClientRequest request) {
        return clientDao.save(Client.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .build());
    }

    @PostMapping("add-address")
    @ResponseBody
    public Client addAddress(@RequestBody ClientAddAddressRequest request) {
        Client client = clientDao.getById(request.getId());
        if (client == null) {
            throw new BusinessException(NO_SUCH_CLIENT_EXCEPTION);
        }

        client.setAddress(request.getAddress());
        return clientDao.save(client);
    }

    @PostMapping("add-passport-number")
    @ResponseBody
    public Client addPassportNumber(@RequestBody ClientAddPassportNumberRequest request) {
        Client client = clientDao.getById(request.getId());
        if (client == null) {
            throw new BusinessException(NO_SUCH_CLIENT_EXCEPTION);
        }
        client.setPassportNumber(request.getPassportNumber());
        return clientDao.save(client);
    }

    @GetMapping("{id}")
    @ResponseBody
    public Client get(@PathVariable("id") int id) {
        return clientDao.getById(id);
    }

    @GetMapping()
    @ResponseBody
    public Collection<Client> getAll() {
        return clientDao.findAllByIdIsNotNull();
    }
}
