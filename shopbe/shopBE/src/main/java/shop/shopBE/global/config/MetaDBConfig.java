package shop.shopBE.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import shop.shopBE.global.utils.database.MetaDBEnv;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class MetaDBConfig {

    private final MetaDBEnv metaDBEnv;

    @Bean
    @Qualifier(value = "metaDBSource")
    public DataSource metaDBSource() {
        return DataSourceBuilder.create()
                .driverClassName(metaDBEnv.driverClassName())
                .url(metaDBEnv.url())
                .username(metaDBEnv.username())
                .password(metaDBEnv.password())
                .build();
    }

    @Bean
    @Qualifier(value = "metaTransactionManager")
    public PlatformTransactionManager metaTransactionManager() {
        return new DataSourceTransactionManager(metaDBSource());
    }

}
