# language: pt

Funcionalidade: Cliente

  Cenario: Registrar Cliente
    Quando registrar cliente sem id
    Entao a mensagem é registrada
    E deve ser apresentado

  Cenario: Buscar Cliente
    Dado que um cliente já foi cadastrado
    Quando efetuar a busca
    Entao o cliente é exibido
