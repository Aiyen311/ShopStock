package com.example.shopstock;

import java.util.ArrayList;
import java.util.List;

public class StoresData {
    private static List<String> stores ;
    static {
        stores =  new ArrayList<String>();

// Need to import CSV file with store data
        stores.add("Black Market KY");
        stores.add("logan Street Bodega");
        stores.add("Kroger");
        stores.add("Farmers Market");
    }

    public static List<String> getStores(){
        return stores;
    }

    public static List<String> filterData(String searchString){
        List<String> searchResults =  new ArrayList<String>();
        if(searchString != null){
            searchString = searchString.toLowerCase();

            for(String rec :  stores){
                if(rec.toLowerCase().contains(searchString)){
                    searchResults.add(rec);
                }
            }
        }
        return searchResults;
    }
}