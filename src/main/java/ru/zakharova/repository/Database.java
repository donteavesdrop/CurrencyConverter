package ru.zakharova.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
    private static Database instance;
    private Connection connection;
    private final static String DB_PATH = "D:\\mirea\\java\\Practice2\\src\\main\\resources\\currenciesDB.db";
    private final static String JDBC_URL = "jdbc:sqlite:" + DB_PATH;
    private final static String CREATION_COMMAND = """
            CREATE TABLE IF NOT EXISTS currencies (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            "value" REAL NOT NULL,
            nominal INTEGER NOT NULL,
            currency_name VARCHAR(100) NOT NULL,
            currency_code VARCHAR(3) NOT NULL,
            "date" DATE NOT NULL);
            """;

    private Database() {
        prepareDirectory();
        connection = getConnection();
        initDb();
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(JDBC_URL);
                connection.setAutoCommit(true);
            }
        } catch (SQLException exc) {
            System.out.println("Error in getConnection() -- Cannot get connection -- Exception message: " + exc.getMessage());
            return null;
        }
        return connection;
    }

    private void prepareDirectory() {
        if (Files.exists(Paths.get(DB_PATH))) {
            return;
        }
        File dbFile = new File(DB_PATH);
        try {
            if (dbFile.createNewFile()) {
                System.out.println("New database file successfully created");
            }
        } catch (IOException exc) {
            System.out.println("Error in prepareDirectory() -- Cannot create file: " + exc.getMessage());
        }
    }

    private void initDb() {
        try (PreparedStatement statement = connection.prepareStatement(CREATION_COMMAND)) {
            statement.execute();
        } catch (SQLException exc) {
            System.out.println("Error in initDb() -- Cannot prepare or execute statement -- Exception message: " + exc.getMessage());
        }
    }

    public void closeConnection() {
        try {
            if (connection == null) {
                return;
            }
            if (connection.isClosed()) {
                return;
            }
            connection.close();
        } catch (SQLException exc) {
            System.out.println("Error in closeConnection() -- Cannot close connection -- Exception message: " + exc.getMessage());
        }
    }
}
