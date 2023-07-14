package org.example.components;

import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

@Component
public class IgnitionSQLDataSource implements DataSource {

    private final GatewayContext gatewayContext;

    @Autowired
    public IgnitionSQLDataSource(GatewayContext gatewayContext) {
        this.gatewayContext = gatewayContext;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return gatewayContext.getDatasourceManager().getDatasource("MES").getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return gatewayContext.getDatasourceManager().getDatasource("MES").getConnection();
    }

    private PrintWriter pw = new PrintWriter(System.out);

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return pw;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        this.pw = out;
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        // nop
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    private final Logger logger = Logger.getLogger("Test Logger");

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return logger;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
