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
    private ComboBox<Good>                 mNameCombo;
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
                    mNameCombo.setItems(null);
                } else {
                    ObservableList<Good> list = GoodDBO.searchGoodsFromCategory(category);
                    mNameCombo.setItems(list);
                }
            }
            catch (SQLException e) {
                clearLog();
                log("Данные о товарах в данной категории не получены");
                log(e.toString());
            }
        });
    }

    @FXML
    private void initialize() {
        mIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        mNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        mPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        mCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        mPurchaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));
        mDeliveryDateColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
        mStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        mCustomerLabel.setText("нет");
        mBalanceLabel.setText("нет");
    }

    @FXML
    private void handleStatusChange() {

    }

    @FXML
    private void handleAddPurchase() {
        
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
