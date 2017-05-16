package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.model.Customer;

public class CustomerTabController {

    @FXML
    private Label                          mCustomerLabel;
    @FXML
    private TextField                      mBalanceField;
    @FXML
    private Button                         mAddMoneyButton;
    @FXML
    private TextField                      mNicknameField;
    @FXML
    private TextField                      mFirstNameField;
    @FXML
    private TextField                      mLastNameField;
    @FXML
    private RadioButton                    mMaleRadio;
    @FXML
    private RadioButton                    mFemaleRadio;
    @FXML
    private TextArea                       mConsoleArea;
    @FXML
    private TextField                      mNicknameSearchField;
    @FXML
    private Button                         mSearchButton;
    @FXML
    private TableView<Customer>            mCustomerTable;
    @FXML
    private TableColumn<Customer, Integer> mIdColumn;
    @FXML
    private TableColumn<Customer, String>  mNicknameColumn;
    @FXML
    private TableColumn<Customer, String>  mFirstNameColumn;
    @FXML
    private TableColumn<Customer, String>  mLastNameColumn;
    @FXML
    private TableColumn<Customer, String>  mSexColumn;
    @FXML
    private TableColumn<Customer, Double>  mBalanceColumn;
    @FXML
    private Button                         mShowPurchaseButton;

    @FXML
    private void handleAddMoney() {

    }

    @FXML
    private void handleAddCustomer() {

    }

    @FXML
    private void handleSearch() {

    }

    @FXML
    private void handleShowAll() {

    }

    @FXML
    private void handleShowPurchase() {

    }

}
