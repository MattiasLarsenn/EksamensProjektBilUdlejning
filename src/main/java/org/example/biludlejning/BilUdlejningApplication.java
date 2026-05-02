package org.example.biludlejning;

import org.example.biludlejning.utility.ConnectionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.datasource.AbstractDataSource;

import javax.sql.DataSource;


@SpringBootApplication
public class BilUdlejningApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(BilUdlejningApplication.class, args);

	}
}
