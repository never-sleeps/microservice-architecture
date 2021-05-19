package com.dev;

import com.microservicearchitecture.config.DbConfig;
import com.microservicearchitecture.config.flyway.DatabaseMigrationImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.sql.Connection;

@Primary
@Profile("fixtures")
@Configuration
public class FlywayMigration extends DatabaseMigrationImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlywayMigration.class);
    private final HikariDataSource hikariDataSource;

    public FlywayMigration(HikariConfig hikariConfig,
                           DbConfig dbConfig,
                           HikariDataSource hikariDataSource) {
        super(hikariConfig, dbConfig);
        this.hikariDataSource = hikariDataSource;
    }

    @Override
    public void migrate() {
        killPostgresPool();

    }

    private Connection getConnection() {
        return null;
    }

    private HikariDataSource maintenanceDataSource() {
        return null;
    }

    private void killPostgresPool() {
        try {
            ((HikariPool) hikariDataSource.getHikariPoolMXBean()).shutdown();
        } catch (InterruptedException e) {
            LOGGER.error("Error during hikari pool shutdown: {}", e.getLocalizedMessage());
        }
    }

    private void killConnections() {
        sql("SELECT pg_terminate_backend(pg_stat_activity.pid)\n" +
                "     FROM pg_stat_activity\n" +
                "     WHERE pg_stat_activity.datname = 'order'\n" +
                "     AND pid <> pg_backend_pid()", false, null);
    }

    private void sql(String sql, boolean isUpdate, Connection connection) {
        LOGGER.debug(sql);
        try {
            if (isUpdate) {
                connection.prepareStatement(sql).executeUpdate();
            } else {
                connection.prepareStatement(sql).execute();
            }
        } catch (Throwable e) {
            LOGGER.error("Failed to run: {}", e.getLocalizedMessage());
            throw new IllegalStateException(e);
        }
    }
}
