package com.microservicearchitecture.config.flyway;

import com.microservicearchitecture.config.DbConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@Configuration
public class DatabaseMigrationImpl implements DatabaseMigration {

    private static final String DATABASE_MIGRATION_PATH = "classpath:/db/migration";
    private static final int MAX_MIGRATION_CONNECTIONS = 1;

    private final HikariConfig hikariConfig;
    private final DbConfig dbConfig;

    @Autowired
    public DatabaseMigrationImpl(HikariConfig hikariConfig, DbConfig dbConfig) {
        this.hikariConfig = hikariConfig;
        this.dbConfig = dbConfig;
    }

    @Override
    public void migrate() {
        if (!dbConfig.isMigrationEnabled()) {
            return;
        }

        var dataSource = getDataSource();
        var connection = dataSource.getConnection();

        try {
            log.info("Starting migration...");
            connection.prepareStatement("BEGIN;").executeUpdate();
            connection.prepareStatement("CREATE SCHEMA IF NOT EXISTS \"public\";").executeUpdate();
            getFlyway(dataSource).migrate();
            connection.prepareStatement("COMMIT;").executeUpdate();
            log.info("Migration finished, commit...");
            dataSource.close();
        } catch (SQLException exception) {
            log.error("Could not prepare statement during schema creation: {}", exception.getLocalizedMessage());
        }
    }

    private Flyway getFlyway(FlywayMigrationDataSource dataSource) {
        var configuration = Flyway.configure()
                .dataSource(dataSource)
                .schemas("public")
                .locations(DATABASE_MIGRATION_PATH);
        return new Flyway(configuration);
    }

    private FlywayMigrationDataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.copyStateTo(hikariConfig);
        config.setMetricsTrackerFactory(null);
        config.setMetricRegistry(null);
        config.setMaximumPoolSize(MAX_MIGRATION_CONNECTIONS);
        config.setPoolName("flyway-connection-pool");
        var dataSource = new HikariDataSource(config);
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException exception) {
            log.error("Connection exception: {}", exception.getLocalizedMessage());
        }
        return new FlywayMigrationDataSource(dataSource, connection);
    }
}
