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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Fail.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        Cliente cliente = ClienteHelper.gerarCliente();
        when(clienteService.salvar(any(Cliente.class)))
                .thenAnswer( i -> i.getArgument(0));

        //act & assert
        mockMvc.perform(
                    post("/api/clientes")
                            .contentType(MediaType.APPLICATION_JSON)
                    .content(ClienteHelper.asJsonString(cliente))
                )
                .andExpect(status().isCreated());
        verify(clienteService, times(1)).salvar(any(Cliente.class));

    }

    @Test
    void ListarCliente() throws Exception {

        mockMvc.perform(get("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(clienteService, times(1))
                .buscarTodos();
    }

    @Test
    void DeletarCliente() throws Exception {

        Integer id = 301;
        when(clienteService.excluir(any(Integer.class)))
                .thenReturn(true);

        mockMvc.perform(delete("/api/clientes/{id}", id))
                .andExpect(status().isOk());
        verify(clienteService, times(1))
                .excluir(any(Integer.class));
    }

    @Test
    void ListarUmCliente() throws Exception {
        Integer id = 301;
        Cliente cliente = ClienteHelper.gerarCliente();
        cliente.setId(id);

        mockMvc.perform(get("/api/clientes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(clienteService, times(1)).buscarUm(any(Integer.class));
    }
}
