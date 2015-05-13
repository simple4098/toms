package com.fanqielaile.toms.support.util;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * Created by wangdayin on 2015/5/13.
 */
public class JsonModel {
    public static Model getModel(Model model, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute(Constants.STATUS, Constants.ERROR);
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError err : errors) {
                model.addAttribute(err.getField(), err.getDefaultMessage());
            }
        } else {

            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
        }
        return model;

    }
}
