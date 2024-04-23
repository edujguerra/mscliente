package br.com.fiap.mscliente.repository;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.fiap.mscliente.model.Cliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClienteRepositoryIT {
    // o Teste de integração é verificado com o banco real, não mockado

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void devePermitirCriarTabela() {
        long totalRegistros = clienteRepository.count();
        assertThat(totalRegistros).isNotNegative();
    }

    @Test
    void RegistrarCliente(){

        // Arrange
        Cliente cliente = gerarCliente();

        //act
        Cliente clienteRecebido = clienteRepository.save(cliente);

        //assert
        assertThat(clienteRecebido)
                .isInstanceOf(Cliente.class)
                .isNotNull();
        assertThat(clienteRecebido.getNome()).isEqualTo(cliente.getNome());
    }

    @Test
    void ListarUmCliente(){
        // Arrange
        int id = 200;

        //act
        Optional<Cliente> clienteRecebido = clienteRepository.findById(id);

        //Assert
        assertThat(clienteRecebido)
                .isPresent()
                .isNotNull();
        clienteRecebido.ifPresent(cli -> {
            assertThat(cli.getId()).isEqualTo(id);
        });
    }

    @Test
    void DeletarCliente(){

        // Arrange
        Integer id = 100;

        // Act
        clienteRepository.deleteById(id);
        Optional<Cliente> clienteRecebido = clienteRepository.findById(id);

        // Assert
        assertThat(clienteRecebido).isEmpty();
    }

    @Test
    void ListarCliente(){

        // Act
        List<Cliente> resultado = clienteRepository.findAll();

        // Assert
        assertThat(resultado).hasSize(3);

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
