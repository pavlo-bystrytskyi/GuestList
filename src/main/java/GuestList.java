import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class GuestList {
    private Path path;

    public GuestList(String path) {
        this.path = Path.of(path);
    }

    public List<String> getGuests() throws IOException {
        return Files.readAllLines(this.path);
    }

    public void setGuests(List<String> guests) throws IOException {
        Files.write(this.path, guests);
    }

    public void addGuest(String guest) throws IOException {
        Files.write(this.path, List.of(guest), StandardOpenOption.APPEND);
    }
}
