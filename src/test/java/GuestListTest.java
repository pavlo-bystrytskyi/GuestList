import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestListTest {

    private final static String FILE_PATH = "guests.txt";

    @BeforeEach
    void init() {
        File file = new File(FILE_PATH);
        file.delete();
    }

    @Test
    void getGuestsTest_empty() {
        try {
            GuestList guestList = new GuestList(FILE_PATH);
            guestList.setGuests(List.of());

            int expected = 0;
            int actual = guestList.getGuests().size();

            assertEquals(expected, actual);
        } catch (IOException e) {
            fail("IOException");
        }
    }

    @Test
    void getGuestsTest_shouldReadSameGuestsAsWrittenBefore() {
        try {
            GuestList guestList = new GuestList(FILE_PATH);
            guestList.setGuests(List.of("Karl", "Ute"));

            List<String> expected = List.of("Karl", "Ute");

            List<String> actual = guestList.getGuests();
            assertEquals(expected, actual);
        } catch (IOException e) {
            fail("IOException");
        }
    }

    @Test
    void getGuestsTest_shouldWriteToFileSystem() throws FileNotFoundException {
        try {
            GuestList guestList = new GuestList(FILE_PATH);
            guestList.setGuests(List.of("Theodor", "Anette"));
            Path path = Path.of(FILE_PATH);

            List<String> expected = List.of("Theodor", "Anette");

            List<String> actual = Files.readAllLines(path);
            expected.stream().forEach(guest -> assertTrue(actual.contains(guest)));
        } catch (IOException e) {
            fail("IOException");
        }
    }

    @Test
    void getGuestsTest_shouldReadFromFileSystem() {
        try {
            Files.write(Path.of(FILE_PATH), List.of("Stephan", "Max"));
            GuestList guestList = new GuestList(FILE_PATH);

            List<String> expected = List.of("Stephan", "Max");

            List<String> actual = guestList.getGuests();
            expected.stream().forEach(guest -> assertTrue(actual.contains(guest)));
        } catch (IOException e) {
            fail("IOException");
        }
    }

    @Test
    void getGuestsTest_fileDoesNotExist() {
        GuestList guestList = new GuestList(FILE_PATH);

        Assertions.assertThrowsExactly(NoSuchFileException.class, () -> guestList.getGuests());
    }

    @Test
    void addGuestTest() {
        try {
            Path path = Path.of(FILE_PATH);
            Files.write(path, List.of("Stephan", "Max"));

            GuestList guestList = new GuestList(FILE_PATH);
            guestList.addGuest("Bob");

            List<String> expected = List.of("Stephan", "Max", "Bob");
            List<String> actual = Files.readAllLines(path);
            expected.stream().forEach(guest -> assertTrue(actual.contains(guest)));
        } catch (IOException e) {
            fail("IOException");
        }
    }
}