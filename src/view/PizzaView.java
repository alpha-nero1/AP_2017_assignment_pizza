/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.LinkedList;
import javafx.beans.Observable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import model.IngUrl;
import model.Ingredient;
import model.Kitchen;
import model.Pizza;

/**
 * Custom StackPane class.
 * @author alessandroalberga
 */
public class PizzaView extends StackPane {
 
    // List of image urls.
    private LinkedList<IngUrl> urls = new LinkedList<IngUrl>();
    
    // Specific pizza being created.
    private Pizza pizza;    
    
    // Constructor.
    public PizzaView() {
        super();
    }
    
    // set the pizza and set its associated image urls.
    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
        
        pizza.getIngredients().addListener((Observable obs) -> {
            setUrls();
        });

    }
    
    /**
     * Sort ingredients method, in order of crust -> sauce -> topping
     */
    private void sortIngUrls() {
        for (IngUrl ingUrl : urls) {
            
            for (int i = 0; i < urls.size() - 1 ; i++) {     // crust bubbled to top.
                if (urls.get(i + 1).getCategory() == Kitchen.CRUST) {
                    swap(i);
                }
                
                // terrible statement, i know.
                // essentially if next item is a sauce and the current one isn't a crust.
                if ((urls.get(i + 1).getCategory() == Kitchen.SAUCE) && (urls.get(i).getCategory() != Kitchen.CRUST)) {
                    swap(i);
                }
            }
        }
        addImageViews();
    }
    
    // set the image urls of the current pizza.
    private void setUrls() {
        urls.clear();
        for (Ingredient ingredient: pizza.getIngredients()) {
            urls.add(new IngUrl("/view/bonusImages/" + ingredient.getName() + ".png", ingredient.getCategory()));
        }
        sortIngUrls();
    }
    
    // Add the image views unto the Stack Pane
    private void addImageViews() {
        this.getChildren().clear();
        for (IngUrl url: urls) {
            ImageView image = new ImageView(new Image(url.getUrl()));
            this.getChildren().add(image);
        }
    }
    
    // perform the urls swap.
    private void swap(int index) {
        IngUrl temp = urls.get(index + 1); //crust
        urls.set(index + 1, urls.get(index));
        urls.set(index, temp);
    }
}