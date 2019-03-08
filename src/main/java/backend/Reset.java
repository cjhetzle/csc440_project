package main.java.backend;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class Reset {

    public static void main(String[] args) throws SQLException, IOException {
        resetDB();
        setupDB();
    }

    private static void resetDB() throws SQLException {
        List<Map<String,String>> commands = Utils.select(
                "SELECT concat('DROP TABLE IF EXISTS `', table_name, '`;') AS command " +
                        "FROM information_schema.tables " +
                        "WHERE table_schema = 'cars';", new String[0]);
        Connection conn = ConnectionWrapper.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        stmt.execute("SET FOREIGN_KEY_CHECKS = 0;");
        for (Map<String,String> command : commands) {
            System.out.println(command.get("command"));
            stmt.execute(command.get("command"));
        }
        stmt.execute("SET FOREIGN_KEY_CHECKS = 1;");
        stmt.close();
    }

    private static void setupDB() throws IOException, SQLException {
        String[] fileNames = {"schema.sql", "load_sample_data.sql"};
        for (String fileName : fileNames) {
            String script = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
            // JDBC can only run one statement at a time
            String[] splitScript = script.split("\n\n");
            Connection conn = ConnectionWrapper.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            for (String command : splitScript) {
                System.out.println(command);
                stmt.execute(command);
            }
            stmt.close();
        }
    }
}
