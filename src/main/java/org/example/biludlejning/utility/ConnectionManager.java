package org.example.biludlejning.utility;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class ConnectionManager
{
    private final DataSource dataSource;

    public ConnectionManager(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    public Connection getConnection() throws SQLException
    {
        return dataSource.getConnection();
    }
}
