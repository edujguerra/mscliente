package br.com.fiap.mscliente.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="tb_clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer id;

    @Column(name = "nm_cliente")
    private String nome;
}
