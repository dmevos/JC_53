package ru.osipov;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String json = readString("new_data.json");
        List<Employee> list = jsonToList(json);

        for (Employee e : list) {
            System.out.println(e);
        }

        System.out.println("Все выполнено!");
    }

    private static List<Employee> jsonToList(String json) {
        List<Employee> listEmpl = new ArrayList<>();

        //Уберем все пробелы и переносы строк. Уберем в начале и в конце строки квадратные скобки
        json = json.replaceAll("\\s", "");
        if ("[".equals(json.substring(0, 1))) json = json.substring(1, json.length() - 1);

        //Создадим массив Стрингов  фигурными скобками в начале и конце
        List<String> listJsonStr = new ArrayList<>(List.of(json.split("},\\{")));
        listJsonStr.set(0, listJsonStr.get(0) + "}");
        for (int i = 1; i < listJsonStr.size(); i++) listJsonStr.set(i, "{" + listJsonStr.get(i));

        for (String lJsonStr : listJsonStr) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            listEmpl.add(gson.fromJson(String.valueOf(lJsonStr), Employee.class));
        }
        return listEmpl;
    }

    private static String readString(String file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder stringBuilder = new StringBuilder();
            // чтение посимвольно
            int c;
            while ((c = br.read()) != -1) {
                stringBuilder.append((char) c);
            }
            return String.valueOf(stringBuilder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        return null;
    }

}