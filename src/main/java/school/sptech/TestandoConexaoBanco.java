package school.sptech;

import org.springframework.jdbc.core.JdbcTemplate;

public class TestandoConexaoBanco {

    public static void main(String[] args) {

        // Para testes com o banco fazer aqui
        // Sempre ter essas duas variáveis para conseguir dar update e query
        ConexaoBanco conexao = new ConexaoBanco();

        JdbcTemplate jdbcTemplate = conexao.getJdbcTemplate();
    }
}
