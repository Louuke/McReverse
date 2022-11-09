package org.jannsen.mcreverse.api.entity.details;

import java.util.ArrayList;
import java.util.List;

public class ProductSet {

    private int minQuantity, quantity;
    private String alias;
    private Action action;
    private List<String> products;

    public Action getAction() {
        return action;
    }

    public String getAlias() {
        return alias;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public List<String> getProducts() {
        return products != null ? products : new ArrayList<>();
    }

    public int getQuantity() {
        return quantity;
    }
}
