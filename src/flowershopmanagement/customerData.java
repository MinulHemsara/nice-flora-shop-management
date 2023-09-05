/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flowershopmanagement;

import java.util.Date;

/**
 *
 * @author minul
 */
public class customerData {
    
    private Integer flowerId;
    private Integer customerId;
    private String name;
    private Integer quantity;
    private Double price;
    private Date date;

    public customerData(Integer flowerId, Integer customerId, String name, Integer quantity, Double price, Date date) {
        this.flowerId = flowerId;
        this.customerId = customerId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
    }

    public Integer getFlowerId() {
        return flowerId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }
    
}
