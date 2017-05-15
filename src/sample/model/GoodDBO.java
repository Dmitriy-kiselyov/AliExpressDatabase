package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GoodDBO {

    private static Good getGood(ResultSet resultSet) {
        try {
            Good good = new Good();
            good.setId(resultSet.getInt("ид"));
            good.setCategory(resultSet.getString("категория"));
            good.setName(resultSet.getString("название"));
            good.setPrice(resultSet.getDouble("цена"));
            good.setDescription(resultSet.getString("описание"));
            return good;
        }
        catch (Exception e) {
            return null;
        }
    }

    private static ObservableList<Good> getGoodsList(ResultSet resultSet) throws SQLException {
        ObservableList<Good> list = FXCollections.observableArrayList();
        while (resultSet.next())
            list.add(getGood(resultSet));
        return list;
    }

    public static ObservableList<Good> searchGoods() throws SQLException {
        String query = "SELECT товары.ид, категории.название категория, товары.название, цена, описание\n" +
                       "FROM ali_express.товары, ali_express.категории\n" +
                       "WHERE ид_категории = категории.ид\n" +
                       "ORDER BY товары.ид;";

        try {
            ResultSet rs = DBUtil.execute(query);
            return getGoodsList(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static ObservableList<Good> searchGoods(String name) throws SQLException {
        if (name.isEmpty())
            return null;

        String query = "SELECT товары.ид, категории.название категория, товары.название, цена, описание\n" +
                       "FROM ali_express.товары, ali_express.категории\n" +
                       "WHERE ид_категории = категории.ид AND товары.название LIKE '%" + name + "%'\n" +
                       "ORDER BY товары.ид;";

        try {
            ResultSet rs = DBUtil.execute(query);
            return getGoodsList(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static int getCategoryId(String category) throws SQLException {
        String query = "SELECT ид FROM ali_express.категории WHERE название = '" + category + "'";
        try {
            ResultSet rs = DBUtil.execute(query);
            rs.next();
            return rs.getInt("ид");
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void insertCategory(String category) throws SQLException {
        try {
            DBUtil.insert("ali_express.категории", null, category);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void insertGood(String category, String name, Double price, String desc) throws SQLException {
        try {
            DBUtil.insert("ali_express.товары", null, getCategoryId(category), name, price, desc);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

    }

    public static ObservableList<String> getCategories() throws SQLException {
        String query = "SELECT название FROM ali_express.категории";

        try {
            ResultSet rs = DBUtil.execute(query);

            ObservableList<String> list = FXCollections.observableArrayList();
            while (rs.next())
                list.add(rs.getString("название"));
            return list;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
