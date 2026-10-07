package entities;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;

public record Valores(
        String usuario,
        Double cpu,
        Double ram,
        Double disco,
        Long bytesEnviados,
        Long bytesPerdidos
) {

    private static final String CSV_PATH = "/dados.csv";

    public static Valores gerarValores() {
        try (InputStream is = Valores.class.getResourceAsStream(CSV_PATH)) {
            if (is != null) {
                String conteudo = new String(is.readAllBytes(), StandardCharsets.UTF_8).trim();
                List<String> linhas = conteudo.lines().toList();
                if (!linhas.isEmpty()) {
                    String[] campos = linhas.get(0).split(",");
                    if (campos.length == 6) {
                        return new Valores(
                                campos[0].trim(),
                                Double.parseDouble(campos[1].trim()),
                                Double.parseDouble(campos[2].trim()),
                                Double.parseDouble(campos[3].trim()),
                                Long.parseLong(campos[4].trim()),
                                Long.parseLong(campos[5].trim())
                        );
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("AVISO - Não foi possível ler dados.csv, gerando valores simulados.");
        }

        Random random = new Random();
        return new Valores(
                System.getProperty("user.name", "usuario"),
                arredondar(random.nextDouble() * 100),
                arredondar(random.nextDouble() * 100),
                arredondar(random.nextDouble() * 100),
                (long) (random.nextDouble() * 5_000_000),
                (long) (random.nextDouble() * 50_000)
        );
    }

    private static double arredondar(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}
