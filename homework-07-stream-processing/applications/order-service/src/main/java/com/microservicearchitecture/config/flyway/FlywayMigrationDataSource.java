package com.microservicearchitecture.config.flyway;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class FlywayMigrationDataSource implements DataSource {

    private final HikariDataSource dataSource;
    private final Connection connection;

    public FlywayMigrationDataSource(HikariDataSource dataSource,
                                     Connection connection) {
        this.dataSource = dataSource;
        this.connection = connection;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public Connection getConnection(String username, String password) {
        return connection;
    }

    @Override
    public PrintWriter getLogWriter() {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) {

    }

    @Override
    public void setLoginTimeout(int seconds) {

    }

    @Override
    public int getLoginTimeout() {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException("getParentLogger not supported");
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new SQLException("cannot unwrap " + iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) {
        return false;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException exception) {
            System.err.println("Exception during closing connection: "+ exception.getLocalizedMessage());
        }
        dataSource.evictConnection(connection);
        dataSource.close();
    }
}
