package hello;

import hex.genmodel.easy.exception.PredictException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class PredictorController {

    private static final String template = "Label (aka prediction) is flight departure delayed:, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/machinelearning")
    public String greeting(@RequestParam(value="Month") String Month,
                           @RequestParam(value="DayofMonth") String DayofMonth,
                           @RequestParam(value="DayofWeek") String DayofWeek,
                           @RequestParam(value="DepTime") String DepTime,
                           @RequestParam(value="Distance") String Distance,
                           @RequestParam(value="UniqueCarrier") String UniqueCarrier,
                           @RequestParam(value="Origin") String Origin,
                           @RequestParam(value="Dest") String Dest) throws IllegalAccessException, InstantiationException, ClassNotFoundException, PredictException {
        Predictor g = new Predictor(Month,DayofMonth,DayofWeek,DepTime,Distance,UniqueCarrier,Origin,Dest);
        return g.predict();
    }
}
