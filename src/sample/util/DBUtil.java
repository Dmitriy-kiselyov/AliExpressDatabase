package sample.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBUtil {

    private static final Pattern ENUM_PATTERN = Pattern.compile("'(.*?)'");

    private static Connection con;
    private static Statement  st;

    private static ScriptRunner scriptRunner;

    public static void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306?characterEncoding=UTF-8", "root", "");
            st = con.createStatement();
            scriptRunner = new ScriptRunner(con, false, true);
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public static void deleteSchema() {
        exec("delete.sql");
    }

    public static void createSchema() {
        exec("create.sql");
    }

    public static void insertDefault() {
        exec("insert_data.sql");
    }

    public static ResultSet execute(String query) throws SQLException {
        return st.executeQuery(query);
    }

    public static void insert(String table, Object... values) throws SQLException {
        String update = "INSERT INTO " + table + " VALUES " + toValues(values);
        st.executeUpdate(update);
    }

    private static String toValues(Object... values) {
        StringBuilder s = new StringBuilder("(");
        boolean first = true;

        for (Object val : values) {
            if (!first)
                s.append(", ");
            first = false;

            if (val == null) {
                s.append("NULL");
            } else if (val.getClass() == String.class) {
                String sVal = (String) val;
                sVal = sVal.trim();

                if (sVal.isEmpty())
                    s.append("NULL");
                else
                    s.append("'").append(sVal).append("'");
            } else if (val.getClass() == Date.class) {
                s.append("'").append(val).append("'");
            } else {
                s.append(val);
            }
        }

        s.append(")");
        return s.toString();
    }

    public static ObservableList<String> listFromEnum(String enumString) {
        ObservableList<String> list = FXCollections.observableArrayList();

        Matcher m = ENUM_PATTERN.matcher(enumString);
        while (m.find())
            list.add(m.group(1));

        return list;
    }

    private static void exec(String path) {
        try {
            ClassLoader classLoader = DBUtil.class.getClassLoader();
//            path = URLDecoder.decode(classLoader.getResource(path).getPath(), "UTF-8");
            InputStreamReader reader = new InputStreamReader(classLoader.getResourceAsStream(path));

            scriptRunner.runScript(new BufferedReader(reader));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
