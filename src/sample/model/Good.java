package sample.model;

import javafx.beans.property.*;

public class Good {

    private IntegerProperty mId;
    private StringProperty  mCategory;
    private StringProperty  mName;
    private DoubleProperty  mPrice;
    private StringProperty  mDescription;

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

    public void setId(int id) {
        this.mId.set(id);
    }

    public IntegerProperty idProperty() {
        return mId;
    }

    public String getCategory() {
        return mCategory.get();
    }

    public void setCategory(String category) {
        this.mCategory.set(category);
    }

    public StringProperty categoryProperty() {
        return mCategory;
    }

    public String getName() {
        return mName.get();
    }

    public void setName(String name) {
        this.mName.set(name);
    }

    public StringProperty nameProperty() {
        return mName;
    }

    public double getPrice() {
        return mPrice.get();
    }

    public void setPrice(double price) {
        this.mPrice.set(price);
    }

    public DoubleProperty priceProperty() {
        return mPrice;
    }

    public String getDescription() {
        return mDescription.get();
    }

    public void setDescription(String description) {
        this.mDescription.set(description);
    }

    public StringProperty descriptionProperty() {
        return mDescription;
    }

    @Override
    public String toString() {
        return mName.get();
    }

}
