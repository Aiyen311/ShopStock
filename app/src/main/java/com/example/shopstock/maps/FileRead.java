package com.example.shopstock.maps;

import com.google.android.gms.maps.model.LatLng;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class FileRead {
    public ArrayList readData() {
        ArrayList<MyMarker> markers = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream(new File("C\\Users\\adith\\Downloads\\ShopNames.xlsx"));
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);

            for (Row row: sheet) {
                double lat = row.getCell(0).getNumericCellValue();
                double lon = row.getCell(1).getNumericCellValue();
                String name = row.getCell(2).getStringCellValue();

                MyMarker myMarker = new MyMarker (
                        new LatLng(lat , lon),
                        name);
                markers.add(myMarker);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return markers;

    }
}
