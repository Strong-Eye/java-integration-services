package school.sptech;

import entities.GerarMensagem;
import entities.Valores;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        Valores logs = Valores.gerarValores();
        String usuario = logs.usuario();
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        DateTimeFormatter formatadorHorario = DateTimeFormatter.ofPattern("HH:mm:ss");
        Double cpu = logs.cpu();
        Double ram = logs.ram();
        Double disco = logs.disco();
        Long bytesEnviados = logs.bytesEnviados();
        Long bytesPerdidos = logs.bytesPerdidos();

        System.out.printf("%s digite o ID do Radar: ", usuario);
        String idRadar = sc.nextLine();
        System.out.println();

        GerarMensagem mensagem = new GerarMensagem();
        mensagem.geradorMensagem(String.format("INFO - Iniciando processo de gerenciamento do RADAR-%s", idRadar), 4, 500);
        mensagem.geradorMensagem("INFO - Iniciando leitura dos dados do CSV", 4, 500);
        mensagem.geradorMensagem("------------------------------------------- \n", 0, 0);

        mensagem.geradorMensagem("INFO - Apresentando dados sobre CPU: \n", 0, 0);
        mensagem.geradorMensagem(String.format("INFO - Percentual de uso da CPU: %.2f%% \n", cpu), 0, 0);
        mensagem.geradorAlerta("CPU", cpu, idRadar);
        mensagem.geradorMensagem("INFO - Leitura da CPU executada ás: " + LocalDateTime.now().format(formatadorHorario), 0, 0);
        System.out.println();
        mensagem.geradorMensagem("------------------------------------------- \n", 0, 0);
        Thread.sleep(1500L);

        mensagem.geradorMensagem("INFO - Apresentando dados sobre RAM: \n", 0, 0);
        mensagem.geradorMensagem(String.format("INFO - Percentual de uso da RAM: %.2f%% \n", ram), 0, 0);
        mensagem.geradorAlerta("RAM", ram, idRadar);
        mensagem.geradorMensagem("INFO - Leitura da RAM executada ás: " + LocalDateTime.now().format(formatadorHorario), 0, 0);
        System.out.println();
        mensagem.geradorMensagem("------------------------------------------- \n", 0, 0);
        Thread.sleep(1500L);

        mensagem.geradorMensagem("INFO - Apresentando dados sobre Disco: \n", 0, 0);
        mensagem.geradorMensagem(String.format("INFO - Percentual de uso do Disco: %.2f%% \n", disco), 0, 0);
        mensagem.geradorAlerta("Disco", disco, idRadar);
        mensagem.geradorMensagem("INFO - Leitura do Disco executada ás: " + LocalDateTime.now().format(formatadorHorario), 0, 0);
        System.out.println();
        mensagem.geradorMensagem("------------------------------------------- \n", 0, 0);
        Thread.sleep(1500L);

        mensagem.geradorMensagem("INFO - Apresentando dados sobre os Bytes: \n", 0, 0);
        mensagem.geradorMensagem(String.format("INFO - Quantidade de Bytes enviados: %.2fMb \n", (double) bytesEnviados / Math.pow(1024.0, 2.0)), 0, 0);
        mensagem.geradorMensagem(String.format("INFO - Quantidade de Bytes perdidos: %.2fMb \n", (double) bytesPerdidos / Math.pow(1024.0, 2.0)), 0, 0);
        mensagem.geradorMensagem("INFO - Leitura dos Bytes executada ás: " + LocalDateTime.now().format(formatadorHorario), 0, 0);
        System.out.println();
        mensagem.geradorMensagem("------------------------------------------- \n", 0, 0);
        Thread.sleep(1500L);

        mensagem.geradorMensagem("Processo finalizado em: " + LocalDateTime.now().format(formatador), 0, 0);
        System.out.println();
        mensagem.geradorMensagem("Muito Obrigado por usar o Sistema Strong Eye", 0, 0);
    }
}
