package br.com.fiap.mscliente.service;

import br.com.fiap.mscliente.model.Cliente;
import br.com.fiap.mscliente.repository.ClienteRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {

    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {

        openMocks = MockitoAnnotations.openMocks(this);
        clienteService= new ClienteService(clienteRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void RegistrarCliente(){

        // Arrange
        Cliente cliente = gerarCliente();
        cliente.setId(400);
        Mockito.when(clienteRepository.save(ArgumentMatchers.any(Cliente.class)))
                .thenAnswer(i -> i.getArgument(0));

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

        // Arrange
        int id = 100;
        Cliente cliente = gerarCliente();
        cliente.setId(id);
        Mockito.when(clienteRepository.findById(ArgumentMatchers.any(Integer.class)))
                .thenReturn(Optional.of(cliente));

        // Act
        ResponseEntity<?> resposta = clienteService.buscarUm(id);
        Cliente clienteArmazenado = (Cliente) resposta.getBody();

        // Assert
        Assertions.assertThat(clienteArmazenado)
                .isInstanceOf(Cliente.class)
                .isNotNull()
                .isEqualTo(cliente);
        Assertions.assertThat(clienteArmazenado)
                .extracting(Cliente::getId)
                .isEqualTo(cliente.getId());
    }

    @Test
    void DeletarCliente(){
        Integer id = 100;
        Cliente cliente = gerarCliente();
        cliente.setId(id);
        when(clienteRepository.findById(id))
                .thenReturn(Optional.of(cliente));
        doNothing()
                .when(clienteRepository).deleteById(id);

        boolean resultado = clienteService.excluir(id);

        Assertions.assertThat(resultado).isTrue();
        verify(clienteRepository, times(1)).findById(any(Integer.class));
        verify(clienteRepository, times(1)).delete(any(Cliente.class));
    }

    @Test
    void ListarCliente(){

        // Arrange
        Cliente cliente1 = gerarCliente();
        Cliente cliente2 = gerarCliente();
        List<Cliente> clienteList = Arrays.asList(cliente1, cliente2);

        Mockito.when(clienteRepository.findAll()).thenReturn(clienteList);

        // Act
        List<Cliente> resultado = clienteService.buscarTodos();

        // Assert
        Assertions.assertThat(resultado)
                .hasSize(2)
                .containsExactlyInAnyOrder(cliente1, cliente2);
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
