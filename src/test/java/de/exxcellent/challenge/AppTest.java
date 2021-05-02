package de.exxcellent.challenge;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.tablesaw.api.Table;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Example JUnit 5 test case.
 * @author Benjamin Schmid <benjamin.schmid@exxcellent.de>
 */
class AppTest {

    private final String weatherPath = "weather.csv";
    private final String footballPath = "football.csv";

    @BeforeEach
    @DisplayName("Basic: Instantiate App class.")
    void setUp() {
        App app = new App();
    }

    @Test
    @DisplayName("Basic: Test non-existing .csv file.")
    void nullPointerExceptionTest() {
        Throwable exception = assertThrows(NullPointerException.class, () -> App.getData("test.csv"));
        assertNull(exception.getMessage());
    }

    @Test
    @DisplayName("Task 1: Get day with smallest weather spread.")
    void testTaskOneSmallestWeatherSpread() throws IOException {
        assertEquals("14", App.getSmallestWeatherSpread(), "Day extraction works.");
    }

    @Test
    @DisplayName("Task 2: Get day with smallest weather spread.")
    void testTaskTwoSmallestWeatherSpread() throws IOException {
        Table data = App.getData("weather.csv");
        String columnOne = "MxT";
        String columnTwo = "MnT";
        String targetColumn = "Day";

        assertEquals("14", App.getSmallestSpread(data, columnOne,columnTwo,targetColumn),"Day extraction works.");
    }

    @Test
    @DisplayName("Task 2: Get average temperature with smallest weather spread.")
    void testTaskTwoAvTSmallestWeatherSpread() throws IOException {
        Table data = App.getData("weather.csv");
        String columnOne = "MxT";
        String columnTwo = "MnT";
        String targetColumn = "AvT";

        assertEquals("60", App.getSmallestSpread(data, columnOne,columnTwo,targetColumn),"AvT extraction works.");
    }

    @Test
    @DisplayName("Task 2: Get team with smallest goal spread.")
    void testTaskTwoSmallestGoalSpread() throws IOException {
        Table data = App.getData("football.csv");
        String columnOne = "Goals";
        String columnTwo = "Goals Allowed";
        String targetColumn = "Team";

        assertEquals("Aston_Villa", App.getSmallestSpread(data, columnOne,columnTwo,targetColumn),"Team extraction works.");
    }

    @Test
    @DisplayName("Task 2: Test non-numerical column selection.")
    void testTaskTwoNonNumericalColumn() throws IOException {
        Table data = App.getData("football.csv");
        String columnOne = "Goals";
        String columnTwo = "Team";
        String targetColumn = "Team";

        Throwable exception = assertThrows(IllegalArgumentException.class,
                                           () -> App.getSmallestSpread(data, columnOne,columnTwo,targetColumn));
        assertEquals("Selected column needs to be of type NumberColumn.", exception.getMessage());
    }

    @Test
    @DisplayName("Task 2: Test non-existing column name.")
    void testTaskTwoNonExistingColumnName() throws IOException {
        Table data = App.getData("football.csv");
        String columnOne = "Goal";
        String columnTwo = "Goals Allowed";
        String targetColumn = "Team";

        Throwable exception = assertThrows(IllegalArgumentException.class,
                                           () -> App.getSmallestSpread(data, columnOne,columnTwo,targetColumn));
        assertEquals("Column does not exist.", exception.getMessage());
    }

    @Test
    @DisplayName("Task 3: Test application status.")
    void testJobApplication(){
        assertEquals("employed.", "employed.");
    }

}