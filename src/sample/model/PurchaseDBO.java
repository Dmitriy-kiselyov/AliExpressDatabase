package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PurchaseDBO {

    private static Purchase getPurchase(ResultSet resultSet) {
        try {
            Purchase purchase = new Purchase();
            purchase.setId(resultSet.getInt("ид"));
            purchase.setName(resultSet.getString("название"));
            purchase.setPrice(resultSet.getDouble("цена"));
            purchase.setCount(resultSet.getInt("количество"));
            purchase.setPurchaseDate(resultSet.getDate("дата_покупки"));
            purchase.setDeliveryDate(resultSet.getDate("дата_доставки"));
            purchase.setStatus(resultSet.getString("статус"));

            return purchase;
        }
        catch (Exception e) {
            return null;
        }
    }

    private static ObservableList<Purchase> getPurchasesList(ResultSet resultSet) throws SQLException {
        ObservableList<Purchase> list = FXCollections.observableArrayList();
        while (resultSet.next())
            list.add(getPurchase(resultSet));
        return list;
    }

    public static ObservableList<Purchase> searchPurchases(int customerId) throws SQLException {
        String query = "SELECT покупки.ид, название, цена, количество, дата_покупки, дата_доставки, статус\n" +
                       "FROM ali_express.покупки, ali_express.товары\n" +
                       "WHERE ид_товара = товары.ид AND ид_покупателя = " + customerId;
        try {
            ResultSet rs = DBUtil.execute(query);
            return getPurchasesList(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void insertPurchase(int customerId, int goodId, int count) throws SQLException {
        try {
            DBUtil.insert("ali_express.покупки", null, customerId, "CURDATE()", null,
                          goodId, count, "проверяется");
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
