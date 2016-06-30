import java.io.*;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.prediction.*;

public class Main {
    private static String modelClassName = "GBM_model_python_1466696584086_1";

    public static void main(String[] args) throws Exception {
        hex.genmodel.GenModel rawModel;
        rawModel = (hex.genmodel.GenModel) Class.forName(modelClassName).newInstance();
        EasyPredictModelWrapper model = new EasyPredictModelWrapper(rawModel);
        RowData row = new RowData();
        row.put("Month", "10");
        row.put("DayofMonth", "1");
        row.put("DayofWeek", "1");
        row.put("DepTime", "1231");
        row.put("Distance", "730");
        row.put("UniqueCarrier", "WN");
        row.put("Origin","SMF");
        row.put("Dest", "PDX");

        BinomialModelPrediction p = model.predictBinomial(row);
        System.out.println("Label (aka prediction) is flight departure delayed: " + p.label);
        System.out.print("Class probabilities: ");
        for (int i = 0; i < p.classProbabilities.length; i++) {
            if (i > 0) {
                System.out.print(",");
            }
            System.out.print(p.classProbabilities[i]);
        }
        System.out.println("");
    }
}
