package shop.shopBE;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@TestConfiguration
@EnableConfigurationProperties(value = DataSourceConfiguration.class)
public class TestConfig {

    private final DataSourceConfiguration dataSourceConfiguration;

    public TestConfig(DataSourceConfiguration dataSourceConfiguration) {
        this.dataSourceConfiguration = dataSourceConfiguration;
    }

    @Bean(name = "dataDBSource")
    @Primary
    public DataSource testDataSource() {
        HikariDataSource dataSource = (HikariDataSource) DataSourceBuilder.create()
                .driverClassName(dataSourceConfiguration.driverClassName())
                .url(dataSourceConfiguration.url())
                .username(dataSourceConfiguration.username())
                .password(dataSourceConfiguration.password())
                .type(HikariDataSource.class)
                .build();

        // 테스트 데이터베이스 관련 설정
        dataSource.setMaximumPoolSize(10);
        return dataSource;
    }

    @Bean(name = "entityManagerFactory")  // entityManagerFactory 빈 정의
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(testDataSource());
        em.setPackagesToScan("shop.shopBE.domain");  // 엔티티 패키지 경로
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", "true");
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean(name = "transactionManager")  // transactionManager 빈 정의
    @Primary
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
}
