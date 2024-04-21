package br.com.fiap.mscliente.controller;

import br.com.fiap.mscliente.model.Cliente;
import br.com.fiap.mscliente.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    public List<Cliente> buscarTodos() {

        return service.buscarTodos();
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Cliente cliente){

        return service.salvar(cliente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUm(@PathVariable Integer id) {

        return service.buscarUm(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody Cliente novo) {

        return service.atualizar(id,novo);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {

        service.excluir(id);
    }
}
