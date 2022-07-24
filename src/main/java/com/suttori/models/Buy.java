package com.suttori.models;

import org.springframework.stereotype.Component;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Component
public class Buy {

    @Min(value = 0, message = "User Id should be greater than 0")
    private int userId;
    @Min(value = 0, message = "Product Id should be greater than 0")
    private int productId;

    public Buy(int userId, int productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public Buy() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
