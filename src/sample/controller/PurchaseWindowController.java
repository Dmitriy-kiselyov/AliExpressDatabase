package sample.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.model.*;

import java.sql.Date;
import java.sql.SQLException;

public class PurchaseWindowController {

    @FXML
    private Label                          mCustomerLabel;
    @FXML
    private Label                          mBalanceLabel;
    @FXML
    private ComboBox<String>               mCategoryCombo;
    @FXML
    private ComboBox<Good>                 mGoodsCombo;
    @FXML
    private Label                          mPriceLabel;
    @FXML
    private Spinner<Integer>               mCountSpinner;
    @FXML
    private Label                          mTotalLabel;
    @FXML
    private TextArea                       mConsoleArea;
    @FXML
    private TableView<Purchase>            mPurchaseTable;
    @FXML
    private TableColumn<Purchase, Integer> mIdColumn;
    @FXML
    private TableColumn<Purchase, String>  mNameColumn;
    @FXML
    private TableColumn<Purchase, Double>  mPriceColumn;
    @FXML
    private TableColumn<Purchase, Integer> mCountColumn;
    @FXML
    private TableColumn<Purchase, Date>    mPurchaseDateColumn;
    @FXML
    private TableColumn<Purchase, Date>    mDeliveryDateColumn;
    @FXML
    private TableColumn<Purchase, String>  mStatusColumn;

    private Customer mCustomer;

    @FXML
    private void initialize() {
        mIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        mNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        mPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        mCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        mPurchaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));
        mDeliveryDateColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
        mStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        SpinnerValueFactory<Integer> spinnerFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 999, 1);
        mCountSpinner.setValueFactory(spinnerFactory);

        mCustomerLabel.setText("нет");
        mBalanceLabel.setText("нет");
        recount();
    }

    public void setCustomer(Customer customer) {
        mCustomer = customer;

        mCustomerLabel.setText(String.format("%s, %s %s", customer.getNickname(), customer.getFirstName(),
                                             customer.getLastName()));
        mBalanceLabel.setText(customer.getBalance() + "");

        handleShowAll();

        try {
            ObservableList<String> list = GoodDBO.getCategories();
            mCategoryCombo.setItems(list);
        }
        catch (SQLException e) {
            log("Произошла фатальная ошибка! Данные о категориях не получены. " +
                "Вы не сможете покупать товары.");
            log(e.toString());
        }

        setupListeners();
    }

    private void setupListeners() {
        mCategoryCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String category = newValue;

            try {
                if (category == null) {
                    mGoodsCombo.setItems(null);
                } else {
                    ObservableList<Good> list = GoodDBO.searchGoodsFromCategory(category);
                    mGoodsCombo.setItems(list);
                }
            }
            catch (SQLException e) {
                clearLog();
                log("Данные о товарах в данной категории не получены");
                log(e.toString());
            }
        });

        mGoodsCombo.getSelectionModel().selectedItemProperty()
                   .addListener((observable, oldValue, newValue) -> recount());
        mCountSpinner.valueProperty().addListener((observable, oldValue, newValue) -> recount());
    }

    private void recount() {
        Good good = mGoodsCombo.getValue();
        if (good == null) {
            mPriceLabel.setText("нет");
            mTotalLabel.setText("нет");
            return;
        }

        double price = good.getPrice();
        int count = mCountSpinner.getValue();
        double total = price * count;

        mPriceLabel.setText(price + " руб.");
        mTotalLabel.setText(round(total, 2) + " руб.");
    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    @FXML
    private void handleStatusChange() {

    }

    @FXML
    private void handleAddPurchase() {
        clearLog();
        try {
            Good good = mGoodsCombo.getValue();

            //take money
            double cost = mCountSpinner.getValue() * good.getPrice();
            double moneyLeft = mCustomer.getBalance() - cost;
            if (moneyLeft < 0)
                throw new NumberFormatException();

            PurchaseDBO.purchase(mCustomer.getId(), good.getId(), mCountSpinner.getValue());

            handleShowAll();

            mCustomer.setBalance(round(moneyLeft, 2));
            mBalanceLabel.setText(round(moneyLeft, 2) + " ");
        }
        catch (NumberFormatException e) {
            log("Недостаточно денег на счету.");
        }
        catch (NullPointerException e) {
            log("Введите название товара.");
        }
        catch (SQLException e) {
            log("Ошибка транзакции.");
            log("Заполните все обязательные поля.");
            log(e.toString());
        }
        catch (Exception e) {
            log("Произошла неизвестная ошибка.");
            log(e.toString());
        }
    }

    @FXML
    private void handleShowAll() {
        clearLog();
        try {
            ObservableList<Purchase> list = PurchaseDBO.searchPurchases(mCustomer.getId());
            mPurchaseTable.setItems(list);
            log("Данные успешно получены.");
            log("У пользователя " + list.size() + " покупок.");
        }
        catch (SQLException e) {
            log("Произошла ошибка получения данных с сервера.");
            log(e.toString());
        }
        catch (Exception e) {
            log("Произошла неизвестная ошибка.");
            log(e.toString());
        }
    }

    private void log(String log) {
        String text = mConsoleArea.getText();

        if (text.isEmpty())
            mConsoleArea.setText(log);
        else
            mConsoleArea.setText(text + "\n" + log);
    }

    private void clearLog() {
        mConsoleArea.setText("");
    }

}
