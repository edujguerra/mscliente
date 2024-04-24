package br.com.fiap.mscliente.service;

import br.com.fiap.mscliente.model.Cliente;
import br.com.fiap.mscliente.repository.ClienteRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClienteServiceIT {

    @Autowired
    private ClienteService clienteService ;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void RegistrarCliente(){
        clienteService= new ClienteService(clienteRepository);

        // Arrange
        Cliente cliente = gerarCliente();

        // Act
        ResponseEntity<?> resposta = clienteService.salvar(cliente);
        Cliente clienteArmazenado = (Cliente) resposta.getBody();

        // Assert
        assertThat(clienteArmazenado)
                .isInstanceOf(Cliente.class)
                .isNotNull()
                .isEqualTo(cliente);
        assertThat(clienteArmazenado.getNome())
                .isEqualTo(cliente.getNome());
    }

    @Test
    void ListarUmCliente(){

        clienteService= new ClienteService(clienteRepository);
        // Arrange
        int id = 100;

        // Act
        ResponseEntity<?> resposta = clienteService.buscarUm(id);
        Cliente clienteArmazenado = (Cliente) resposta.getBody();

        // Assert
        assertThat(clienteArmazenado)
                .isNotNull();
    }

    @Test
    void ListarCliente(){
        clienteService= new ClienteService(clienteRepository);
        // Act
        List<Cliente> resultado = clienteService.buscarTodos();

        // Assert
        Assertions.assertThat(resultado)
                .hasSize(3);
    }

    @Test
    void DeletarCliente(){

        clienteService= new ClienteService(clienteRepository);
        Integer id = 100;
        Cliente cliente = gerarCliente();
        cliente.setId(id);

        boolean resultado = clienteService.excluir(id);

        Assertions.assertThat(resultado).isTrue();
    }


    private Cliente gerarCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("Eduardo");
        cliente.setUf("RS");
        cliente.setEndereco("rua");
        cliente.setCidade("Caxias");
        cliente.setBairro("bairro");
        cliente.setCpf("10212");
        cliente.setCep("95020-190");
        cliente.setEmail("email");
        cliente.setComplemento("ap 2");

        return cliente;
    }
}
