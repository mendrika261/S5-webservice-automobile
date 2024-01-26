package mg.springboot.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GraphDataSet {
    private String label;
    private double[] data;

    public GraphDataSet(String label) {
        setLabel(label);
    }
}
