package de.exxcellent.challenge;
import tech.tablesaw.api.*;
import tech.tablesaw.io.csv.CsvReadOptions;
import java.io.IOException;


public final class App {
    public static void main(String... args){
        task1();
        task2();
    }

    public static void task1(){
        try {
            String dayWithSmallestTempSpread = getSmallestWeatherSpread();
            System.out.printf("Day with smallest temperature spread : %s%n", dayWithSmallestTempSpread);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void task2(){
        try {
            String dayWithSmallestTempSpread = getSmallestSpread(getData("weather.csv"), "MxT", "MnT", "Day");
            System.out.printf("Day with smallest temperature spread : %s%n", dayWithSmallestTempSpread);

            String teamWithSmallestGoalSpread = getSmallestSpread(getData("football.csv"), "Goals", "Goals Allowed", "Team");
            System.out.printf("Team with smallest goal spread       : %s%n", teamWithSmallestGoalSpread);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Table getData(String fileName) throws IOException {
        return Table.read().usingOptions(
                CsvReadOptions.builder(App.class.getResourceAsStream(fileName))
                .header(true)
                .separator(',')
                .build());
    }

    public static String getSmallestWeatherSpread() throws IOException {
        Table weather = getData("weather.csv");
        weather.insertColumn(3, IntColumn.create("DfT", weather.rowCount()));
        weather.stream().
                forEach(
                        row -> {
                            row.setInt("DfT",
                                    Math.abs(row.getInt("MxT") - row.getInt("MnT")));
                        }
                );
        return weather.sortAscendingOn("DfT").column(0).get(0).toString();
    }

    public static String getSmallestSpread(Table data, String columnName1, String columnName2, String targetColumn){
        if(!(data.columnNames().contains(columnName1)) || !(data.columnNames().contains(columnName2))){
            throw new IllegalArgumentException("Column does not exist.");
        }

        if(!(data.column(data.columnIndex(columnName1)) instanceof NumberColumn) ||
                !(data.column(data.columnIndex(columnName2)) instanceof NumberColumn)){
            throw new IllegalArgumentException("Selected column needs to be of type NumberColumn.");
        }


        int column_idx = data.columnIndex(columnName2) + 1;
        data.column(data.columnIndex(columnName2));
        data.insertColumn(column_idx, DoubleColumn.create("Difference", data.rowCount()));
        data.stream().
                forEach(
                        row -> {
                            row.setDouble("Difference",
                                          Math.abs(row.getNumber(columnName1) - row.getNumber(columnName2)));
                        }
                );

        int targetIdx = data.columnIndex(targetColumn);
        return data.sortAscendingOn("Difference").column(targetIdx).get(0).toString();
    }
}
