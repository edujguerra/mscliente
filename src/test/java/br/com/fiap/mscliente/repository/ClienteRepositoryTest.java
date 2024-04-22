package br.com.fiap.mscliente.repository;

import br.com.fiap.mscliente.model.Cliente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ClienteRepositoryTest {

    @Mock
    private ClienteRepository clienteRepository;

    AutoCloseable openMocks;


    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void RegistrarCliente(){

        // Arrange
        Cliente cliente = gerarCliente();
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // Act
        var clienteArmazenado = clienteRepository.save(cliente);

        // Assert
        verify(clienteRepository, times(1)).save(cliente);
        assertThat(clienteArmazenado)
                .isInstanceOf(Cliente.class)
                .isNotNull()
                .isEqualTo(cliente);
        assertThat(clienteArmazenado)
                .extracting(Cliente::getId)
                .isEqualTo(cliente.getId());
//        assertThat(avaliacaoArmazenada)
//                .extracting(Avaliacao::getCliente)
//                .isEqualTo(avaliacao.getCliente());

    }

    @Test
    void AlterarCliente(){
        fail("teste não implementado");
    }

    @Test
    void DeletarCliente(){
        fail("teste não implementado");
    }

    @Test
    void ListarCliente(){
        fail("teste não implementado");
    }

    @Test
    void ListarUmCliente(){
        fail("teste não implementado");
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

        return cliente;
    }
}
