package com.roi.car.services;

import com.roi.car.models.Car;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVUtil {

    public static void saveCarsToCSV(List<Car> cars, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("CarID,LicensePlateNumber,Make,Model,Year,Color,BodyType,EngineType,Transmission");
            writer.newLine();
            for (Car car : cars) {
                writer.write(String.format("%d,%s,%s,%s,%d,%s,%s,%s,%s",
                        car.getCarId(),
                        escapeCsv(car.getLicensePlateNumber()),
                        escapeCsv(car.getMake()),
                        escapeCsv(car.getModel()),
                        car.getYear(),
                        escapeCsv(car.getColor()),
                        escapeCsv(car.getBodyType()),
                        escapeCsv(car.getEngineType()),
                        escapeCsv(car.getTransmission())));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Car> readCarsFromCSV(String fileName) {
        List<Car> cars = new ArrayList<>();
        File file = new File(fileName);
        if (!file.exists()) {
            return null;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine(); // skip header
            if (line == null) return cars;
            while ((line = br.readLine()) != null) {
                String[] data = parseCsvLine(line);
                if (data.length < 9) continue;
                Car car = new Car();
                car.setCarId(Long.parseLong(data[0]));
                car.setLicensePlateNumber(data[1]);
                car.setMake(data[2]);
                car.setModel(data[3]);
                car.setYear(Integer.parseInt(data[4]));
                car.setColor(data[5]);
                car.setBodyType(data[6]);
                car.setEngineType(data[7]);
                car.setTransmission(data[8]);
                cars.add(car);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cars;
    }

    // Basic CSV escaping and parsing methods (similar to previous example)...

    private static String escapeCsv(String input) {
        if (input == null) return "";
        if (input.contains(",") || input.contains("\"") || input.contains("\n")) {
            input = input.replace("\"", "\"\"");
            return "\"" + input + "\"";
        }
        return input;
    }

    private static String[] parseCsvLine(String line) {
        List<String> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') inQuotes = !inQuotes;
            else if (c == ',' && !inQuotes) {
                tokens.add(sb.toString().trim());
                sb.setLength(0);
            } else sb.append(c);
        }
        tokens.add(sb.toString().trim());
        for (int i = 0; i < tokens.size(); i++) {
            String t = tokens.get(i);
            if (t.startsWith("\"") && t.endsWith("\"")) {
                t = t.substring(1, t.length() - 1).replace("\"\"", "\"");
                tokens.set(i, t);
            }
        }
        return tokens.toArray(new String[0]);
    }
}
