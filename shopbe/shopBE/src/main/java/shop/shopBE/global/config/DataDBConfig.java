package shop.shopBE.global.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import shop.shopBE.global.utils.database.DataDBEnv;

import javax.sql.DataSource;
import java.util.HashMap;


@Configuration
@EnableJpaRepositories(
        basePackages = "shop.shopBE.domain",
        entityManagerFactoryRef = "dataEntityManager",
        transactionManagerRef = "dataTransactionManager"
)
@RequiredArgsConstructor
public class DataDBConfig {

    private final DataDBEnv dataDBEnv;

    @Bean
    public DataSource dataDBSource() {
        HikariDataSource dataSource = (HikariDataSource) DataSourceBuilder.create()
                .driverClassName(dataDBEnv.driverClassName())
                .url(dataDBEnv.url())
                .username(dataDBEnv.username())
                .password(dataDBEnv.password())
                .type(com.zaxxer.hikari.HikariDataSource.class)
                .build();

        dataSource.setMaximumPoolSize(20); // 최대 커넥션 수 (기본값 10)
        dataSource.setMinimumIdle(5); // 최소 유휴 커넥션 수
        dataSource.setIdleTimeout(30000); // 커넥션 풀에서 커넥션이 유휴 상태일 수 있는 시간 (밀리초)
        dataSource.setMaxLifetime(600000); // 커넥션의 최대 생명 주기 (밀리초)
        dataSource.setConnectionTimeout(30000); // 커넥션을 얻을 때 대기 시간 (밀리초)

        return dataSource;

    }

    @Bean
    public LocalContainerEntityManagerFactoryBean dataEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

        em.setDataSource(dataDBSource());
        em.setPackagesToScan(new String[]{"shop.shopBE.domain"});
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", "true");

        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    public PlatformTransactionManager dataTransactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(dataEntityManager().getObject());
        return jpaTransactionManager;
    }

}
