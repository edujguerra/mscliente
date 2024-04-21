package br.com.fiap.mscliente.service;

import java.util.List;
import java.util.NoSuchElementException;

import br.com.fiap.mscliente.model.CepResponse;
import br.com.fiap.mscliente.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.fiap.mscliente.repository.ClienteRepository;
import org.springframework.web.client.RestTemplate;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;
    public List<Cliente> buscarTodos() {
        return repository.findAll();
    }

    public ResponseEntity<?> salvar(Cliente cliente) {

        ResponseEntity response = validaCampos(cliente);
        if (!response.getStatusCode().equals(HttpStatus.OK)  ){
            return response;
        }

        cliente = repository.save(cliente);
        return ResponseEntity.ok(cliente);
    }

    private ResponseEntity validaCampos(Cliente cliente) {

        if (cliente.getNome() == null
                || cliente.getNome().equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nome não pode ser vazio.");
        }
        if (cliente.getEmail() == null ||
                cliente.getEmail().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email não pode ser vazio.");
        }
        if (cliente.getCep() == null ||
                cliente.getCep().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cep não pode ser vazio.");
        }
        if (cliente.getCpf() == null ||
                cliente.getCpf().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF não pode ser vazio.");
        }
        if (cliente.getComplemento() == null ||
                cliente.getComplemento().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Complemento não pode ser vazio.");
        }
        try {
            String URI = "https://viacep.com.br/ws/" + cliente.getCep() + "/json/";
            RestTemplate restTemplate = new RestTemplate();
            CepResponse cepResponse = restTemplate.getForEntity(URI, CepResponse.class).getBody();
            if (cliente.getEndereco() == null ||
                    cliente.getEndereco().isEmpty()) {
                cliente.setEndereco(cepResponse.getLogradouro());
            }
            if (cliente.getBairro() == null ||
                    cliente.getBairro().isEmpty()) {
                cliente.setBairro(cepResponse.getBairro());
            }
            if (cliente.getCidade() == null ||
                    cliente.getCidade().isEmpty()) {
                cliente.setCidade(cepResponse.getLocalidade());
            }
            if (cliente.getUf() == null ||
                    cliente.getUf().isEmpty()) {
                cliente.setUf(cepResponse.getUf());
            }
        } catch (Exception err) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cep não encontrado. ");
        }
        return ResponseEntity.ok(cliente);
    }

    public ResponseEntity<?> buscarUm(Integer id ) {

        Cliente cliente = repository.findById(id).orElse(null);

        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
        }
    }

    public ResponseEntity<?> atualizar(Integer id, Cliente novo) {
        Cliente existente = repository.findById(id).orElse(null);

        if (existente != null) {
            ResponseEntity response = validaCampos(novo);
            if (!response.getStatusCode().equals(HttpStatus.OK)  ){
                return response;
            }

            existente.setNome(novo.getNome());
            existente.setUf(novo.getUf());
            existente.setBairro(novo.getBairro());
            existente.setCidade(novo.getCidade());
            existente.setEndereco(novo.getEndereco());
            existente.setCep(novo.getCep());
            existente.setComplemento(novo.getComplemento());
            existente.setEmail(novo.getEmail());
            existente.setCpf(novo.getCpf());

            return ResponseEntity.ok(repository.save(existente));
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
