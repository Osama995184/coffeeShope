/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.lang.reflect.Field;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author OSOS
 */
public class functions {

    alert al = new alert();



    // هذه الداله للبحث فى اى جدول هضيف بس الاعمده
    public <T> void setupTableSearchFilter(TextField searchField, TableView<T> tableView, ObservableList<T> originalData, List<String> searchableFields) {
        FilteredList<T> filteredData = new FilteredList<>(originalData, b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(item -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                for (String field : searchableFields) {
                    try {
                        Field classField = item.getClass().getDeclaredField(field);
                        classField.setAccessible(true);
                        Object value = classField.get(item);
                        if (value != null && value.toString().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        }
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            });
        });

        SortedList<T> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }

    public boolean isntNumeric(String str) {
        try {
            Double.parseDouble(str);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }
}
