/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author julio.sevilla
 */
public class CreateProducts {
    public static void main(String args[]) throws IOException {
        File file = new File("exitsigns_OUTPUT_CSV.csv");
        PrintWriter output = new PrintWriter(file);
        int numberOfProducts = 100;
        int numberOfSKUs = 10;
        String prefixProducts = "TEST PRODUCT - ";
        String prefixSKUs = "SKU-";
        String category = "100";
        String optionSet = "ALL SIZES";
        
        output.println("Item Type,Product Name,Product Type,Product Code/SKU,Option Set,Option Set Align,Price,Product Weight,Current Stock Level,Low Stock Level,Category,Track Inventory,Current Stock Level,Low Stock Level");
        for(int counter = 0; counter < numberOfProducts; counter++) {
            output.println("Product," + prefixProducts + counter + ",P,," + optionSet + ",Right,123,2,0,0," + category + ",by option," + (numberOfSKUs * 5) + ",0");
            for(int skuCounter = 0; skuCounter < numberOfSKUs; skuCounter++) {
                output.println("  SKU,[RT]Size=" + prefixSKUs + skuCounter + ",," + prefixSKUs + ((counter * numberOfSKUs) + skuCounter) + ",,,,,,,,5,0");
            }
        }
        output.close();
    }
}
