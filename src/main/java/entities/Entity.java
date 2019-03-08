package main.java.entities;

import java.sql.SQLException;

/**
 * Base class for all database entities
 */
interface Entity {
    void load() throws SQLException;
    void save() throws SQLException;
    void insert() throws SQLException;
}
