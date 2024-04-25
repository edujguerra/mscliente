package br.com.fiap.mscliente.service;

import br.com.fiap.mscliente.model.Cliente;
import br.com.fiap.mscliente.repository.ClienteRepository;
import br.com.fiap.mscliente.utils.ClienteHelper;
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
        Cliente cliente = ClienteHelper.gerarCliente();

        // Act
        Cliente clienteArmazenado = clienteService.salvar(cliente);

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
        int id = 200;

        // Act
        ResponseEntity<Object> resposta = clienteService.buscarUm(id);
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
        Cliente cliente = ClienteHelper.gerarCliente();
        cliente.setId(id);

        boolean resultado = clienteService.excluir(id);

        Assertions.assertThat(resultado).isTrue();
    }
}
