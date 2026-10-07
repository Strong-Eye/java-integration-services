# Strong Eye Radar

Sistema de monitoramento de recursos (CPU, RAM, Disco e tráfego de rede) de radares,
com criação automática de solicitações no Jira quando algum recurso ultrapassa o limite.

## Estrutura do projeto

```
strong-eye-radar/
├── pom.xml
├── README.md
└── src/main/java/
    ├── school/sptech/
    │   ├── Main.java          -> ponto de entrada da aplicação
    │   └── JiraService.java   -> integração com a API REST do Jira
    └── entities/
        ├── Valores.java       -> leitura/geração dos dados monitorados
        └── GerarMensagem.java -> exibição de mensagens e disparo de alertas
```

## Pré-requisitos

- Java 17+
- Maven 3.8+
- Uma conta Jira Cloud com um projeto criado
- Um API Token do Jira: https://id.atlassian.com/manage-profile/security/api-tokens

## Configuração

Antes de rodar, defina as variáveis de ambiente com os dados do seu Jira:

```bash
export JIRA_BASE_URL="https://sua-empresa.atlassian.net"
export JIRA_EMAIL="seu-email@empresa.com"
export JIRA_API_TOKEN="seu-token-aqui"
export JIRA_PROJECT_KEY="STR"      # chave do seu projeto no Jira
export JIRA_ISSUE_TYPE="Task"      # tipo de issue: Task, Bug, Story, etc.
```

No Windows (PowerShell):

```powershell
$env:JIRA_BASE_URL="https://sua-empresa.atlassian.net"
$env:JIRA_EMAIL="seu-email@empresa.com"
$env:JIRA_API_TOKEN="seu-token-aqui"
$env:JIRA_PROJECT_KEY="STR"
$env:JIRA_ISSUE_TYPE="Task"
```

## Como rodar

```bash
mvn compile exec:java
```

Ou gerar um `.jar` executável:

```bash
mvn package
java -jar target/strong-eye-radar.jar
```

## Dados de entrada (opcional)

Por padrão, `Valores.gerarValores()` tenta ler o arquivo `src/main/resources/dados.csv`
no formato:

```
usuario,cpu,ram,disco,bytesEnviados,bytesPerdidos
joao.silva,72.5,64.3,88.1,1048576,2048
```

Se o arquivo não existir, valores simulados aleatórios são gerados automaticamente.

## Como funciona a integração com o Jira

Sempre que o uso de CPU, RAM ou Disco ultrapassa **80%**, o método `geradorAlerta`
(em `GerarMensagem`) cria automaticamente uma nova solicitação (issue) no Jira,
usando o `JiraService`, com um resumo e descrição do problema detectado.

Você pode ajustar o limite de alerta na constante `LIMITE_ALERTA` em `GerarMensagem.java`.

## Observações

- As classes `Valores` e `GerarMensagem` foram recriadas com base no uso observado
  no código original (decompilado). Se você já possuir a implementação real dessas
  classes, basta substituir os arquivos correspondentes mantendo a mesma assinatura
  de métodos usada em `Main.java`.
