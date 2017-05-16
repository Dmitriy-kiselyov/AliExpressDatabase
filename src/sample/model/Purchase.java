package sample.model;

import javafx.beans.property.*;

import java.sql.Date;

public class Purchase {

    private IntegerProperty      mId;
    private StringProperty       mName;
    private DoubleProperty       mPrice;
    private IntegerProperty      mCount;
    private ObjectProperty<Date> mPurchaseDate;
    private ObjectProperty<Date> mDeliveryDate;
    private StringProperty       mStatus;

    public Purchase() {
        mId = new SimpleIntegerProperty();
        mName = new SimpleStringProperty();
        mPrice = new SimpleDoubleProperty();
        mCount = new SimpleIntegerProperty();
        mPurchaseDate = new SimpleObjectProperty<>();
        mDeliveryDate = new SimpleObjectProperty<>();
        mStatus = new SimpleStringProperty();
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

    public int getCount() {
        return mCount.get();
    }

    public void setCount(int count) {
        this.mCount.set(count);
    }

    public IntegerProperty countProperty() {
        return mCount;
    }

    public Date getPurchaseDate() {
        return mPurchaseDate.get();
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.mPurchaseDate.set(purchaseDate);
    }

    public ObjectProperty<Date> purchaseDateProperty() {
        return mPurchaseDate;
    }

    public Date getDeliveryDate() {
        return mDeliveryDate.get();
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.mDeliveryDate.set(deliveryDate);
    }

    public ObjectProperty<Date> deliveryDateProperty() {
        return mDeliveryDate;
    }

    public String getStatus() {
        return mStatus.get();
    }

    public void setStatus(String status) {
        this.mStatus.set(status);
    }

    public StringProperty statusProperty() {
        return mStatus;
    }
}
