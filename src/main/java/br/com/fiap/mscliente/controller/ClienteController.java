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

    @CrossOrigin
    @GetMapping
    public List<Cliente> buscarTodos() {

        return service.buscarTodos();
    }

    @CrossOrigin
    @PostMapping
    public Cliente salvar(@RequestBody Cliente cliente){

        return service.salvar(cliente);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUm(@PathVariable Integer id) {

        return service.buscarUm(id);
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public Cliente atualizar(@PathVariable Integer id, @RequestBody Cliente novo) {

        return service.atualizar(id,novo);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {

        service.excluir(id);
    }
}
