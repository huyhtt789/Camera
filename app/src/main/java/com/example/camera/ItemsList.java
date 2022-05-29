package com.example.camera;

public class ItemsList {
    private Item[] items;

    public ItemsList(Item[] items){
        this.items = items;
    }

    public Item[] getItems() {
        return this.items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }
}
