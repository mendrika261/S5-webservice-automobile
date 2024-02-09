package mg.springboot.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterObject {
    private String modeleLike;
    private Integer[] marques = new Integer[0];
    private Integer[] boiteVitesses = new Integer[0];
    private Integer[] energie = new Integer[0];
    private Integer[] etatVoiture = new Integer[0];
    private Integer[] pays = new Integer[0];

    public String getModeleLike() {
        if (modeleLike == null)
            return "";
        return modeleLike;
    }
}
