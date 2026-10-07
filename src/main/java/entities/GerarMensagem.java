package entities;

import school.sptech.JiraService;

public class GerarMensagem {

    //Varáveis chaves
    private static final double LIMITE_ALERTA = 80.0;

    //Configurações pro Jira
    private static final String JIRA_BASE_URL = "https://rafaela-mayumi.atlassian.net";
    private static final String JIRA_EMAIL = "mayumirafaela0@gmail.com";
    private static final String JIRA_API_TOKEN = "ATATT3xFfGF0orAhGcIDwXGQZC5NQxqIaaL_e0KASwg6qFmZMUjlxZ5Ck22xXW4Yi1IDVw66IrOwIe34d08zQPdTtk7REU9fv4Ni5uJpYfw1PHZQsggk3tJth-a4WFwnX9xDuSTqD9GjKavNwQpI54oJi9XTEt7QHw42EWccgMULRFhBgoUnzKo=5285BDCA";
    private static final String JIRA_PROJECT_KEY = "SUP";
    private static final String JIRA_ISSUE_TYPE = "Task";

    public void geradorMensagem(String mensagem, int velocidadeMs, int delayFinalMs) {
        if (velocidadeMs <= 0) {
            System.out.println(mensagem);
        } else {
            for (char c : mensagem.toCharArray()) {
                System.out.print(c);
                try {
                    Thread.sleep(velocidadeMs);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println();
        }

        if (delayFinalMs > 0) {
            try {
                Thread.sleep(delayFinalMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void geradorAlerta(String tipoRecurso, Double valor, String idRadar) {
        if (valor == null || valor < LIMITE_ALERTA) {
            return;
        }

        geradorMensagem(String.format(
                "ALERTA - Uso de %s no RADAR-%s está em %.2f%%, acima do limite de %.2f%% \n",
                tipoRecurso, idRadar, valor, LIMITE_ALERTA), 0, 0);

        try {
            JiraService jira = new JiraService(
                    JIRA_BASE_URL,
                    JIRA_EMAIL,
                    JIRA_API_TOKEN,
                    JIRA_PROJECT_KEY,
                    JIRA_ISSUE_TYPE
            );

            String resumo = String.format("Alerta RADAR-%s - %s acima do limite (%.2f%%)",
                    idRadar, tipoRecurso, valor);
            String descricao = String.format(
                    "O recurso %s do RADAR-%s atingiu %.2f%%, ultrapassando o limite de %.2f%%.",
                    tipoRecurso, idRadar, valor, LIMITE_ALERTA);

            jira.criarSolicitacao(resumo, descricao);
        } catch (Exception e) {
            geradorMensagem("ERRO - Falha ao criar solicitação no Jira: " + e.getMessage(), 0, 0);
        }
    }
}
