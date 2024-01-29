package mg.springboot.entity;

public class FilterRequest {
    private EtatVoiture[] etatVoitures;
    private Couleur[] couleurs;
    private Marque[] marques;

    // getters et setters


    public Marque[] getMarques() {
        return marques;
    }

    public void setMarques(Marque[] marques) {
        this.marques = marques;
    }

    public EtatVoiture[] getEtatVoitures() {
        return etatVoitures;
    }

    public void setEtatVoitures(EtatVoiture[] etatVoitures) {
        this.etatVoitures = etatVoitures;
    }

    public Couleur[] getCouleurs() {
        return couleurs;
    }

    public void setCouleurs(Couleur[] couleurs) {
        this.couleurs = couleurs;
    }
}
