🚀 PicPay Simplificado - Backend Challenge
Este projeto é uma implementação do desafio técnico para o PicPay, focado em criar uma plataforma de pagamentos simplificada. Desenvolvido com Java e Spring Boot, o sistema permite transferências entre usuários comuns e lojistas, com foco em segurança e integridade de dados.

🛠️ Decisões Técnicas e Tecnologias
Para este projeto, foram tomadas decisões visando as melhores práticas do mercado de desenvolvimento backend:

Java 17 & Spring Boot 3: Utilização das versões mais recentes para aproveitar recursos modernos de performance e segurança.

Segurança com UUID: Diferente do modelo sequencial básico, optei por utilizar UUID (Universally Unique Identifier) como chave primária nas entidades. Isso evita ataques de enumeração e torna a exposição de IDs na URL muito mais segura.

Banco de Dados H2: Utilizado para persistência em memória, facilitando o ambiente de desenvolvimento e testes rápidos.

Arquitetura em Camadas: O projeto segue o padrão Controller -> Service -> Repository -> Entity, garantindo uma clara separação de responsabilidades.

Tratamento de Exceções: Implementação de um ExceptionHandler global para garantir respostas HTTP claras e padronizadas em caso de erros.

⚙️ Funcionalidades
Cadastro de Usuários: Permite cadastrar usuários do tipo "Common" e "Merchant".

Validação de Transações: Sistema que impede lojistas de enviar dinheiro, permitindo apenas o recebimento.

Verificação de Saldo: Validação automática de saldo antes de qualquer transação ser efetuada.

Sincronização de Dados: Atualização atômica dos saldos do pagador e recebedor.

Integração com Mock de Autorização: O sistema consulta um serviço externo fictício para autorizar transferências.

🚀 Como Executar o Projeto
Clone o repositório:

- Instale as dependências:

- Execute a aplicação:

- A API estará disponível em http://localhost:8080.

Nota de Estudo: Este projeto faz parte do meu aprendizado em Análise e Desenvolvimento de Sistemas, aplicando conceitos de Java e Spring Boot.
