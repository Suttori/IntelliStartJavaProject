package com.suttori.util;

import com.suttori.dao.UserDAO;
import com.suttori.models.Buy;
import com.suttori.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private final UserDAO userDAO;

    @Autowired
    public UserValidator(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Buy.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Buy buy = (Buy) target;
        if (userDAO.showId(buy.getUserId()).isEmpty())
            errors.rejectValue("userId", "", "User with this id was not found");
    }
}
