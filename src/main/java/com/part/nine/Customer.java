package com.part.nine;

import java.util.List;

/**
 * Created by Administrator on 2017/2/13.
 */
public class Customer {
    private long customerNum;
    private String firstName;
    private String lastName;
    private List<String> middleNames;

    public long getCustomerNum() {
        return customerNum;
    }

    public void setCustomerNum(long customerNum) {
        this.customerNum = customerNum;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<String> getMiddleNames() {
        return middleNames;
    }

    public void setMiddleNames(List<String> middleNames) {
        this.middleNames = middleNames;
    }
}
