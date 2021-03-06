package sample.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    public static void update(String expression) throws SQLException {
        st.executeUpdate(expression);
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
                else if (sVal.equalsIgnoreCase("CURDATE()"))
                    s.append(sVal);
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

    public static void exec(String path) {
        try {
            scriptRunner.runScript(DBUtil.readAsset(path));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void executeAsset(String path, Map<String, String> replaces) throws SQLException, IOException {
        String asset = DBUtil.readAsset(path).lines().collect(Collectors.joining("\n"));

        for(Map.Entry<String, String> entry : replaces.entrySet()) {
            asset = asset.replace("{" + entry.getKey() + "}", entry.getValue());
        }

        InputStream stream = new ByteArrayInputStream(asset.getBytes(StandardCharsets.UTF_8));

        scriptRunner.runScript(new BufferedReader(new InputStreamReader(stream)));
    }

    static private BufferedReader readAsset(String path) throws IOException {
        ClassLoader classLoader = DBUtil.class.getClassLoader();
//            path = URLDecoder.decode(classLoader.getResource(path).getPath(), "UTF-8");
        return new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream(path)));
    }

}
