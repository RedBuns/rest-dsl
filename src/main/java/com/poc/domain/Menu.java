
package com.poc.domain;

import java.util.List;

public class Menu {

    private String header;
    private List<Item> items = null;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    

}
