package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.util.DBUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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

    public static void purchase(int customerId, int goodId, int count) throws SQLException, IOException {
        Map<String, String> replaces = new HashMap<>();

        replaces.put("customerId", String.valueOf(customerId));
        replaces.put("goodId", String.valueOf(goodId));
        replaces.put("count", String.valueOf(count));

        DBUtil.executeAsset("buy.sql", replaces);
    }

    public static ObservableList<String> getAvailableStatuses(int purchaseId) throws SQLException {
        String query = "SELECT переход FROM ali_express.статусы WHERE название = " +
                "(SELECT статус FROM ali_express.покупки WHERE ид = " + purchaseId + ")";

        try {
            ResultSet rs = DBUtil.execute(query);

            ObservableList<String> list = FXCollections.observableArrayList();
            while (rs.next())
                list.add(rs.getString("переход"));
            return list;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
