package com.suttori.util;

import com.suttori.dao.ProductDAO;
import com.suttori.models.Buy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProductValidator implements Validator {

    private final ProductDAO productDAO;

    @Autowired
    public ProductValidator(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Buy.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Buy buy = (Buy) target;
        if (productDAO.showId(buy.getProductId()).isEmpty())
            errors.rejectValue("productId", "", "Product with this id was not found");
    }
}
