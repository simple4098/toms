package com.fanqielaile.toms.support.util;

import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangdayin on 2015/5/13.
 */
public class JsonModel extends ExtendedModelMap {
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

    /**
     * 用户前段 返回 JSON 结果集
     */
    private List<JsonModel> results;

    public String getMessage() {
        return (String) this.get("message");
    }

    public void setMessage(String message) {
        this.addAttribute("message", message);
    }

    public boolean isSuccess() {
        return (Boolean) this.get("status");
    }

    public void setSuccess(boolean success) {
        this.addAttribute("status", success);
    }

    @SuppressWarnings("unchecked")
    public JsonModel(boolean success, Model model) {
        this.addAttribute("status", success);
        Map<String, Object> modAttr = model.asMap();
        modAttr.clear();

    }

    /**
     * 新增 addResult方法，用于添加前段返回类似{results:[{},{},{}]} 的结果集
     *
     * @param jsonModel result 数组元素
     */

    @SuppressWarnings("unchecked")
    public void addResult(JsonModel jsonModel) {
        if (results == null) {
            this.addAttribute("results", new ArrayList<JsonModel>());
            results = (List<JsonModel>) this.get("results");
        }
        results.add(jsonModel);
    }

    public void addResultList(List list) {
        this.addAttribute("results", list);
    }

    public JsonModel(Model model) {
        this(true, model);
    }

    public JsonModel() {
        this(true, new ExtendedModelMap());
    }

    public JsonModel(boolean success) {
        this.addAttribute("status", success);

    }

    public JsonModel(boolean success, String message) {
        this.addAttribute("status", success);
        this.addAttribute("message", message);
    }


}
