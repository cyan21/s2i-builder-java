package hello;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Hashtable;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

//@Controller
//@RequestMapping("/getMoodjson")
@RestController
public class MoodJSONController {

    private final AtomicLong counter = new AtomicLong();
    Map<String,String> moodMap = new HashMap();
    
/*    
    private static final Hashtable<String,String> MOODIES = new Hashtable<String,String>();
    static {
        MOODIES.put("happy","Don't cry because it's over, smile because it happened.");
        MOODIES.put("sad","Tears are words that need to be written.");
        MOODIES.put("hungry","An empty stomach is not a good political adviser.");
        MOODIES.put("thirsty","Drink in the moon as though you might die of thirst.");
        MOODIES.put("anger","Anger is one letter short of danger.");
    };
*/
    
    public  MoodJSONController() {
        moodMap.put("happy","Don't cry because it's over, smile because it happened.");
        moodMap.put("sad","Tears are words that need to be written.");
        moodMap.put("hungry","An empty stomach is not a good political adviser.");
        moodMap.put("thirsty","Drink in the moon as though you might die of thirst.");
        moodMap.put("anger","Anger is one letter short of danger.");    
    }

    public static final Logger logger = LoggerFactory.getLogger(MoodJSONController.class);

    /**
     * get All mood
     * @return
     */
    @RequestMapping (value="/getAllMood", method=RequestMethod.GET)
    public @ResponseBody Map<String,String> getTheMoodjson() {
      return moodMap;
    }

    @RequestMapping(value="/getMoodjson", method=RequestMethod.GET,produces = "application/json")
    public @ResponseBody String getTheMoodjson(@RequestParam(value="id", required=false, defaultValue="happy") String id) {             
        logger.info("Looking for mood : {}", id);  
        moodMap.keySet().stream().forEach(s -> logger.info("{}",s));
        return moodMap.keySet().stream().anyMatch(s -> s.compareTo(id) == 0) ? moodMap.get(id) : "...";
//        return moodMap.get(id);
    }
    
    @RequestMapping(value="/changeMoodjson/{mood}", method=RequestMethod.PUT)
    public ResponseEntity<?> changeMoodjson(@PathVariable("mood") String mood, @RequestBody String quote) {
        logger.info("Updating quote for mood : {}", mood);  
        moodMap.put(mood, quote);
        return new ResponseEntity<>("Successfully updated quote for mood" + quote, HttpStatus.OK);
    }

}
