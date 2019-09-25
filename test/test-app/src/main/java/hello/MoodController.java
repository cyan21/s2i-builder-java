package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoodController {

    private final AtomicLong counter = new AtomicLong();

    private static final String[] MOODIES = {
        "Don't cry because it's over, smile because it happened.",
        "Tears are words that need to be written.",
        "Anger is one letter short of danger.",
        "An empty stomach is not a good political adviser.",
        "Drink in the moon as though you might die of thirst.",
        "Winter is coming."
    };

    @RequestMapping("/getMood")
    public String getTheMood(@RequestParam(value="id", defaultValue="0") String id) {

        int status = 0;
        try {
            status = (Integer.parseInt(id) < 4 && Integer.parseInt(id) > 0) ? Integer.parseInt(id) : 0;
        } catch (NumberFormatException e) {
            System.out.println("id is not a number !! set to 0");
        }

        return new String(MOODIES[status]);
    }
}
