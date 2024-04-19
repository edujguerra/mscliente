package br.com.fiap.mscliente.service;

import java.util.List;
import java.util.NoSuchElementException;

import br.com.fiap.mscliente.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.fiap.mscliente.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;
    public List<Cliente> buscarTodos() {
        return repository.findAll();
    }

    public Cliente salvar(Cliente cliente) {

        return repository.save(cliente);
    }

    public ResponseEntity<?> buscarUm(Integer id ) {

        Cliente cliente = repository.findById(id).orElse(null);

        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
        }
    }

    public Cliente atualizar(Integer id, Cliente novo) {
        Cliente existente = repository.findById(id).orElse(null);

        if (existente != null) {
            existente.setNome(novo.getNome());

            return repository.save(existente);
        } else {
            throw new NoSuchElementException("Cliente não Encontrado.");
        }
    }

    public void excluir(Integer id) {
        Cliente existente = repository.findById(id).orElse(null);

        if (existente != null) {
            repository.delete(existente);
        } else {
            throw new NoSuchElementException("Cliente não encontrado");
        }
    }
}
