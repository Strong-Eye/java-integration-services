package school.sptech;

import java.net.URI;//Formatar o link da API do Jira
import java.net.http.HttpClient;//Responsável por disparar e gerenciar a conexão de rede com a API
import java.net.http.HttpRequest;//Onde define o endereço,tipo de envio,autenticação, etc
import java.net.http.HttpResponse;//Resposta que o Jira devolve para o Java(HTTP)
import java.nio.charset.StandardCharsets;//Converte caracteres especiais
import java.util.Base64;//Cria o cabeçalho do Jira

public class JiraService {

    //variáveis
    private final String baseUrl;
    private final String email;
    private final String apiToken;
    private final String projectKey;
    private final String issueType;
    private final HttpClient httpClient;

    //Recebe as informações necessárias para o Java saber qual Jira/espaço usar e como se autenticar.
    public JiraService(String baseUrl, String email, String apiToken, String projectKey, String issueType) {
        this.baseUrl = baseUrl;//endereço do Jira
        this.email = email;//e-mail da conta do Jira
        this.apiToken = apiToken;//e-mail da conta do Jira
        this.projectKey = projectKey;//projeto onde a issue será criada
        this.issueType = issueType;//tipo da issue, como Task/Bug/Story
        this.httpClient = HttpClient.newHttpClient();//o objeto que o Java vai utilizar para enviar requisições HTTP

    }

    // A classe transforma isso em uma requisição para o Jira.
    public String criarSolicitacao(String summary, String description) throws Exception {
        String credenciais = Base64.getEncoder().encodeToString(
                (email + ":" + apiToken).getBytes(StandardCharsets.UTF_8)
        );

        String jsonBody = montarJsonIssue(summary, description);

        //Representa a requisição que será enviada ao Jira.
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/rest/api/3/issue"))
                .header("Authorization", "Basic " + credenciais)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody, StandardCharsets.UTF_8))
                .build();

        //Aqui o Java envia a requisição para o Jira.
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        //pega o código HTTP da resposta.
        if (response.statusCode() == 201) { //Se a requisição foi aceita
            String body = response.body();
            // Extração simples do "key" sem depender de biblioteca externa de JSON
            String key = extrairCampo(body, "\"key\"");
            System.out.println("INFO - Solicitação criada no Jira com sucesso: " + key);
            return key;
        } else {

            System.out.println("ERRO - Falha ao criar solicitação no Jira. Status: " + response.statusCode());
            System.out.println("Resposta: " + response.body());
            return null;
        }
    }

    private String montarJsonIssue(String summary, String description) {
        // Descrição no formato ADF (Atlassian Document Format)
        // Essa função é responsável por montar o JSON que o Jira espera receber.
        return """
                {
                  "fields": {
                    "project": {
                      "key": "%s"
                    },
                    "summary": "%s",
                    "description": {
                      "type": "doc",
                      "version": 1,
                      "content": [
                        {
                          "type": "paragraph",
                          "content": [
                            {
                              "type": "text",
                              "text": "%s"
                            }
                          ]
                        }
                      ]
                    },
                    "issuetype": {
                      "name": "%s"
                    }
                  }
                }
                """.formatted(
                escapeJson(projectKey),
                escapeJson(summary),
                escapeJson(description),
                escapeJson(issueType)
        );
    }

    private String escapeJson(String texto) {
        //Tratamento da descrição/texto para o JSON não ser inválido
        if (texto == null) return "";
        return texto
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "");
    }

    private String extrairCampo(String json, String chave) {
        //Essa função pega uma informação específica da resposta do Jira.
        int idx = json.indexOf(chave);
        if (idx == -1) return null;
        int inicioValor = json.indexOf(':', idx) + 1;
        int aspasInicio = json.indexOf('"', inicioValor) + 1;
        int aspasFim = json.indexOf('"', aspasInicio);
        return json.substring(aspasInicio, aspasFim);
    }
}
