package sample.controller;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.model.Customer;
import sample.model.CustomerDBO;

import java.sql.SQLException;

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
    private void initialize() {
        mIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        mNicknameColumn.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        mFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        mLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        mSexColumn.setCellValueFactory(new PropertyValueFactory<>("sex"));
        mBalanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

        setupListeners();

        handleShowAll();
    }

    private void setupListeners() {
        mSearchButton.disableProperty().bind(Bindings.createBooleanBinding(
                () -> mNicknameSearchField.getText().trim().isEmpty(),
                mNicknameSearchField.textProperty()
        ));

        ToggleGroup toggleGroup = new ToggleGroup();
        mMaleRadio.setToggleGroup(toggleGroup);
        mFemaleRadio.setToggleGroup(toggleGroup);
        mMaleRadio.setSelected(true);

        mCustomerTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Customer customer = newValue;
            if (customer == null) {
                mCustomerLabel.setText("Выберите пользователя");
                mAddMoneyButton.setDisable(true);
            } else {
                mCustomerLabel.setText(customer.getNickname() + ", баланс: " + customer.getBalance() + " рублей");
                mAddMoneyButton.setDisable(false);
            }
        });
        mAddMoneyButton.setDisable(true);
    }

    @FXML
    private void handleAddMoney() {
        clearLog();
        try {
            double money = Double.parseDouble(mBalanceField.getText());
            if (money < 0.01)
                throw new NumberFormatException();

            Customer customer = mCustomerTable.getSelectionModel().getSelectedItem();
            CustomerDBO.addMoney(customer.getId(), money);

            mBalanceField.setText("");
            handleShowAll();
        }
        catch (NumberFormatException e) {
            log("Ошибка ввода данных.");
            log("Баланс - положительное действительное число.");
        }
        catch (SQLException e) {
            log("Произошла ошибка вставки данных.");
            log(e.toString());
        }
        catch (Exception e) {
            log("Произошла неизвестная ошибка.");
            log(e.toString());
        }
    }

    @FXML
    private void handleAddCustomer() {
        clearLog();
        try {
            String nick = mNicknameField.getText();
            String name = mFirstNameField.getText();
            String surname = mLastNameField.getText();
            int sex = mMaleRadio.isSelected() ? 1 : 2;

            CustomerDBO.insertCustomer(nick, name, surname, sex);
            handleShowAll();
        }
        catch (MySQLIntegrityConstraintViolationException e) {
            log("Произошла ошибка вставки данных.");
            log("Такой ник уже занят.");
        }
        catch (SQLException e) {
            log("Произошла ошибка вставки данных.");
            log("Заполните все обязательные поля.");
            log(e.toString());
        }
        catch (Exception e) {
            log("Произошла неизвестная ошибка.");
            log(e.toString());
        }

    }

    @FXML
    private void handleSearch() {
        String nick = mNicknameSearchField.getText().trim();

        clearLog();
        try {
            ObservableList<Customer> list = CustomerDBO.searchCustomers(nick);
            mCustomerTable.setItems(list);
            log("Данные успешно получены.");

            if (list == null || list.isEmpty())
                log("По вашему запросу не найдено ни одного пользователя.");
            else
                log("По вашему запросу найдено " + list.size() + " пользователей.");
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

    @FXML
    private void handleShowAll() {
        clearLog();
        mNicknameSearchField.setText("");

        try {
            ObservableList<Customer> list = CustomerDBO.searchCustomers();
            mCustomerTable.setItems(list);
            log("Данные успешно получены.");
            log("Всего " + list.size() + " пользователей.");
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

    @FXML
    private void handleShowPurchase() {

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
