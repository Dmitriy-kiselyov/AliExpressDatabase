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

    public static ObservableList<Customer> searchCustomers() throws SQLException {
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

    public static ObservableList<Customer> searchCustomers(String nickname) throws SQLException {
        if (nickname.isEmpty())
            return null;

        String query = "SELECT * FROM ali_express.покупатели WHERE ник LIKE '%" + nickname + "%'";

        try {
            ResultSet rs = DBUtil.execute(query);
            return getCustomersList(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void insertCustomer(String nickname, String name, String surname, int sex) throws SQLException {
        try {
            String sexStr = null;
            if (sex == 1)
                sexStr = "М";
            if (sex == 2)
                sexStr = "Ж";
            DBUtil.insert("ali_express.покупатели", null, nickname, name, surname, sexStr, 0);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
