/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alex.jeediary.val;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Validator to check that two passwords match when entered.
 * @author alex
 */
@FacesValidator("passwordValidator")
public class PasswordValidator implements Validator {

    /**
     *
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException
     */
    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        String confirmationPassword = value.toString();

        UIInput uiInputPassword = (UIInput) component.getAttributes().get("password");

        String password = uiInputPassword.getValue().toString();

        if (confirmationPassword == null || confirmationPassword.isEmpty() || password == null || password.isEmpty()) {
            return;
        }

        if (!confirmationPassword.equals(password)) {
            uiInputPassword.setValid(false);
            throw new ValidatorException(new FacesMessage("Password must match confirm password."));
        }

    }
}