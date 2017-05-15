package sample.model;

import javafx.beans.property.*;

public class Good {

    private IntegerProperty mId;
    private StringProperty mCategory;
    private StringProperty mName;
    private DoubleProperty mPrice;
    private StringProperty mDescription;

    public Good() {
        mId = new SimpleIntegerProperty();
        mCategory = new SimpleStringProperty();
        mName = new SimpleStringProperty();
        mPrice = new SimpleDoubleProperty();
        mDescription = new SimpleStringProperty();
    }

    public int getId() {
        return mId.get();
    }

    public IntegerProperty idProperty() {
        return mId;
    }

    public void setId(int id) {
        this.mId.set(id);
    }

    public String getCategory() {
        return mCategory.get();
    }

    public StringProperty categoryProperty() {
        return mCategory;
    }

    public void setCategory(String category) {
        this.mCategory.set(category);
    }

    public String getName() {
        return mName.get();
    }

    public StringProperty nameProperty() {
        return mName;
    }

    public void setName(String name) {
        this.mName.set(name);
    }

    public double getPrice() {
        return mPrice.get();
    }

    public DoubleProperty priceProperty() {
        return mPrice;
    }

    public void setPrice(double price) {
        this.mPrice.set(price);
    }

    public String getDescription() {
        return mDescription.get();
    }

    public StringProperty descriptionProperty() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription.set(description);
    }
}
