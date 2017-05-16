package sample.model;

import javafx.beans.property.*;

public class Customer {

    private IntegerProperty mId;
    private StringProperty  mNickname;
    private StringProperty  mFirstName;
    private StringProperty  mLastName;
    private StringProperty  mSex;
    private DoubleProperty  mBalance;

    public Customer() {
        mId = new SimpleIntegerProperty();
        mNickname = new SimpleStringProperty();
        mFirstName = new SimpleStringProperty();
        mLastName = new SimpleStringProperty();
        mSex = new SimpleStringProperty();
        mBalance = new SimpleDoubleProperty();
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

    public String getNickname() {
        return mNickname.get();
    }

    public void setNickname(String nickname) {
        this.mNickname.set(nickname);
    }

    public StringProperty nicknameProperty() {
        return mNickname;
    }

    public String getFirstName() {
        return mFirstName.get();
    }

    public void setFirstName(String firstName) {
        this.mFirstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName.get();
    }

    public void setLastName(String lastName) {
        this.mLastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return mLastName;
    }

    public String getSex() {
        return mSex.get();
    }

    public void setSex(String sex) {
        this.mSex.set(sex);
    }

    public StringProperty sexProperty() {
        return mSex;
    }

    public double getBalance() {
        return mBalance.get();
    }

    public void setBalance(double balance) {
        this.mBalance.set(balance);
    }

    public DoubleProperty balanceProperty() {
        return mBalance;
    }
}
