package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.model.Customer;
import sample.model.Purchase;

import java.sql.Date;

public class PurchaseWindowController {

    @FXML
    private Label                          mCustomerLabel;
    @FXML
    private Label                          mBalanceLabel;
    @FXML
    private ComboBox<String>               mCategoryCombo;
    @FXML
    private ComboBox<String>               mNameCombo;
    @FXML
    private Label                          mPriceLabel;
    @FXML
    private Spinner<Integer>               mCountSpinner;
    @FXML
    private Label                          mTotalLabel;
    @FXML
    private TextArea                       mConsoleArea;
    @FXML
    private TableView<Purchase>            mPurchaseView;
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
    }

    @FXML
    private void handleStatusChange() {

    }

    @FXML
    private void handleAddPurchase() {

    }

    @FXML
    private void handleShowAll() {

    }

}
