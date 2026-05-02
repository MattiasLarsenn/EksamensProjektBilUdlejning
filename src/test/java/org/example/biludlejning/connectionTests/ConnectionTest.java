package org.example.biludlejning.connectionTests;

import org.example.biludlejning.utility.ConnectionManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
public class ConnectionTest
{
    @Autowired
    private ConnectionManager connectionManager;

    //Testing if we can establish a connection to the database server
    @Test
    void shouldConnectToDatabase()
    {
        //If the method doesn't throw an exception we established a connection
        assertDoesNotThrow(() ->
        {
            try (Connection conn = connectionManager.getConnection())
            {
            }
        });
    }

}
