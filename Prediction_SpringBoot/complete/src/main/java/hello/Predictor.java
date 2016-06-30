package hello;

import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.exception.PredictException;
import hex.genmodel.easy.prediction.BinomialModelPrediction;

public class Predictor {

    private final String Month;
    private final String DayofMonth;
    private final String DayofWeek;
    private final String DepTime;
    private final String Distance;
    private final String UniqueCarrier;
    private final String Origin;
    private final String Dest;

    public Predictor(String Month, String DayofMonth, String DayofWeek, String DepTime, String Distance, String UniqueCarrier, String Origin, String Dest) {
        this.Month = Month;
        this.DayofMonth = DayofMonth;
        this.DayofWeek = DayofWeek;
        this.DepTime = DepTime;
        this.Distance = Distance;
        this.UniqueCarrier = UniqueCarrier;
        this.Origin = Origin;
        this.Dest = Dest;
    }

    public String predict() throws ClassNotFoundException, IllegalAccessException, InstantiationException,PredictException {

        String label;
        hex.genmodel.GenModel rawModel;
        rawModel = new GBM_model_python_1466696584086_1();
        EasyPredictModelWrapper model = new EasyPredictModelWrapper(rawModel);
        RowData row = new RowData();
        row.put("Month", this.Month);
        row.put("DayofMonth", this.DayofMonth);
        row.put("DayofWeek", this.DayofWeek);
        row.put("DepTime", this.DepTime);
        row.put("Distance", this.Distance);
        row.put("UniqueCarrier", this.UniqueCarrier);
        row.put("Origin",this.Origin);
        row.put("Dest",this.Dest);

        BinomialModelPrediction p = model.predictBinomial(row);

        if (p.label == "0"){
            label = "Flight Will Not Be Delayed";
        }
        else{
            label = "Flight Will Be Delayed";
        }


        return "Label (aka prediction) is flight departure delayed: " + label;
    }

}
