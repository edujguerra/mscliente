package br.com.fiap.mscliente.component;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageRabbitListener {

//    @RabbitListener(queues = "clientes.v1.cliente-solicitado")
//    public void onMessage(String message){
//        System.out.println("Mensagem recebida : " + message);
//    }
}
