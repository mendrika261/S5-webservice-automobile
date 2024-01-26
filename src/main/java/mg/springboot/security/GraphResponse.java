package mg.springboot.security;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GraphResponse {
    private String[] labels;
    private GraphDataSet[] datasets;

    public GraphResponse(List<Object> objectList, String label) {
        this.labels = new String[objectList.size()];
        this.datasets = new GraphDataSet[1];
        this.datasets[0] = new GraphDataSet(label);
        this.datasets[0].setData(new double[objectList.size()]);
        for(int i = 0; i < objectList.size(); i++) {
            Object[] object = (Object[]) objectList.get(i);
            this.labels[i] = object[0].toString();
            this.datasets[0].getData()[i] = Math.round(Double.parseDouble(object[1].toString()) * 100.0) / 100.0;
        }
    }
}
