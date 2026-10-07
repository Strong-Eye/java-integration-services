package school.sptech;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class ConexaoBanco {

    // Variáveis
    private final JdbcTemplate jdbcTemplate;
    private final BasicDataSource basicDataSource;

    // Construtor que prepara as configurações do banco
    public ConexaoBanco() {

        //Criando uma instância local para configurar as credenciais do banco
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/strong_eye"); //Caminho
        dataSource.setUsername("aluno"); //Usuário
        dataSource.setPassword("sptech"); //Senha

        this.basicDataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public BasicDataSource getBasicDataSource() {
        return basicDataSource;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}

