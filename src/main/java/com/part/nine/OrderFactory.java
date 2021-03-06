package com.part.nine;

import com.alibaba.fastjson.JSON;

/**
 * Created by Administrator on 2017/2/13.
 */
public class OrderFactory {

    public static Order create(long orderID){
        Order order = new Order();
        order.setOrderNumber(orderID);
        order.setTotal(9999.99f);
        Address address = new Address();
        address.setCity("南京市");
        address.setCountry("中国");
        address.setPostCode("123321");
        address.setState("江苏省");
        address.setStreet1("龙眠大道");
        order.setBillTo(address);
        Customer customer = new Customer();
        customer.setCustomerNum(orderID);
        customer.setFirstName("李");
        customer.setLastName("林峰");
        order.setCustomer(customer);
        order.setShipping(Shipping.INTERNATIONAL_EXPRESS);
        //order.setShipTo(address);
        return order;
    }

    public static void main(String[] args){
        System.out.println(JSON.toJSONString(OrderFactory.create(123111)));
    }

}
