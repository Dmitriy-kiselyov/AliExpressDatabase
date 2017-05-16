package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDBO {

    private static Customer getCustomer(ResultSet resultSet) {
        try {
            Customer customer = new Customer();
            customer.setId(resultSet.getInt("ид"));
            customer.setNickname(resultSet.getString("ник"));
            customer.setFirstName(resultSet.getString("имя"));
            customer.setLastName(resultSet.getString("фамилия"));
            customer.setSex(resultSet.getString("пол"));
            customer.setBalance(resultSet.getDouble("баланс"));
            return customer;
        }
        catch (Exception e) {
            return null;
        }
    }

    private static ObservableList<Customer> getCustomersList(ResultSet resultSet) throws SQLException {
        ObservableList<Customer> list = FXCollections.observableArrayList();
        while (resultSet.next())
            list.add(getCustomer(resultSet));
        return list;
    }

    public static ObservableList<Customer> searchGoods() throws SQLException {
        String query = "SELECT * FROM ali_express.покупатели";

        try {
            ResultSet rs = DBUtil.execute(query);
            return getCustomersList(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
