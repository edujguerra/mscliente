package br.com.fiap.mscliente.controller;

import br.com.fiap.mscliente.model.Cliente;
import br.com.fiap.mscliente.service.ClienteService;
import br.com.fiap.mscliente.utils.ClienteHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Fail.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClienteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClienteService clienteService;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        ClienteController clienteController = new ClienteController(clienteService);

        mockMvc = MockMvcBuilders.standaloneSetup(clienteController)
                .build();

    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }


    @Test
    void RegistrarCliente() throws Exception {
        //arrange
        var cliente = ClienteHelper.gerarCliente();
        when(clienteService.salvar(any(Cliente.class)))
                .thenAnswer( i -> i.getArgument(0));

        //act & assert
        mockMvc.perform(
                    post("/api/clientes")
                    .content(asJsonString(cliente))
                )
                .andExpect(status().isCreated());
        verify(clienteService, times(1)).salvar(any(Cliente.class));

    }

    public static String asJsonString(final Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
    @Test
    void ListarCliente(){
        fail("teste não implenentado");
    }

    @Test
    void DeletarCliente(){
        fail("teste não implenentado");
    }

    @Test
    void ListarUmCliente(){
        fail("teste não implenentado");
    }
}
