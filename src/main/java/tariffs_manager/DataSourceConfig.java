package tariffs_manager;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "tariffsEntityManagerFactory",
        transactionManagerRef = "tariffstransactionmanager",
        basePackages = {"tariffs_manager"}
)
public class DataSourceConfig {

    @Primary
    @Bean(name = "tariffsdsproperties")
    @ConfigurationProperties("tariffs.datasource")
    public DataSourceProperties tariffsDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean(name="tariffsdatasource")
    public DataSource tariffsDataSource
            (@Qualifier("tariffsdsproperties") DataSourceProperties tariffsDataSourceProperties){
        return tariffsDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean(name="tariffsEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean tariffsEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("tariffsdatasource") DataSource tariffsDataSource
    ){
        Map<String,String> tariffsJpaProperties = new HashMap<>();
        tariffsJpaProperties.put("hibernate.hbm2ddl.auto", "update");

        return builder
                .dataSource(tariffsDataSource)
                .packages("tariffs_manager")
                .persistenceUnit("tariffsdatasource")
                .properties(tariffsJpaProperties)
                .build();

    }

    @Bean(name="tariffstransactionmanager")
    public PlatformTransactionManager tariffsTransactionManager(
            @Qualifier("tariffsEntityManagerFactory")EntityManagerFactory factory
            ){
        return new JpaTransactionManager(factory);
    }
}
