package sample.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.model.Good;
import sample.model.GoodDBO;

import java.sql.SQLException;

public class GoodsTabController {

    @FXML
    private TextField                  mCategoryField;
    @FXML
    private ComboBox<String>           mCategoryCombo;
    @FXML
    private TextField                  mNameText;
    @FXML
    private TextField                  mPriceText;
    @FXML
    private TextArea                   mDescriptionArea;
    @FXML
    private TextArea                   mConsoleArea;
    @FXML
    private TextField                  mNameSearchText;
    @FXML
    private TableView<Good>            mGoodTable;
    @FXML
    private TableColumn<Good, Integer> mIdColumn;
    @FXML
    private TableColumn<Good, String>  mCategoryColumn;
    @FXML
    private TableColumn<Good, String>  mNameColumn;
    @FXML
    private TableColumn<Good, Double>  mPriceColumn;
    @FXML
    private TableColumn<Good, String>  mDescriptionColumn;

    @FXML
    private void initialize() {
        mIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        mCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        mNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        mPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        mDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        setupListeners();

        handleShowAll();

        try {
            ObservableList<String> list = GoodDBO.getCategories();
            mCategoryCombo.setItems(list);
        }
        catch (Exception e) {
            log("Произошла фатальная ошибка! Данные о категориях не получены. " +
                "Вы не сможете добавлять новые товары в базу данных.");
            log(e.toString());
        }
    }

    private void setupListeners() {
        mGoodTable.getSelectionModel()
                  .selectedIndexProperty()
                  .addListener((observable, oldValue, newValue) ->
                                       Platform.runLater(() -> mGoodTable.getSelectionModel().clearSelection()));
    }

    @FXML
    private void handleShowAll() {
        clearLog();
        mNameSearchText.setText("");

        try {
            ObservableList<Good> list = GoodDBO.searchGoods();
            mGoodTable.setItems(list);
            log("Данные успешно получены.");
            log("Всего " + list.size() + " товаров.");
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
    private void handleAddGood() {
        clearLog();
        try {
            String category = mCategoryCombo.getSelectionModel().getSelectedItem();
            String name = mNameText.getText();
            String desc = mDescriptionArea.getText();
            Double price = Double.parseDouble(mPriceText.getText());
            if (price < 0.01)
                throw new NumberFormatException();

            GoodDBO.insertGood(category, name, price, desc);
            handleShowAll();
        }
        catch (NumberFormatException e) {
            log("Цена должна быть положительной и больше 0.01 рубля.");
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
    private void handleAddCategory() {
        String category = mCategoryField.getText();

        clearLog();
        try {
            GoodDBO.insertCategory(category);
            mCategoryCombo.getItems().add(category);
            log("Новая категория успешно добавлена!");
            mCategoryField.setText("");
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
    private void handleSearch() {
        String name = mNameSearchText.getText().trim();

        clearLog();
        try {
            ObservableList<Good> list = GoodDBO.searchGoods(name);
            mGoodTable.setItems(list);
            log("Данные успешно получены.");

            if (list == null || list.isEmpty())
                log("По вашему запросу не найдено ни одного товара.");
            else
                log("По вашему запросу найдено " + list.size() + " товаров.");
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
