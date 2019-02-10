/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Custom class containing and image url and its category type
 * so that they can later be sorted.
 */
public class IngUrl {
    private String url;
    private Category category;
    
    public IngUrl(String url, Category category) {
        this.url = url;
        this.category = category;
    }
    
    public String getUrl() { return url; }
    public Category getCategory() { return category; }
}