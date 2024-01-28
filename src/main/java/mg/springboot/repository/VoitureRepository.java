package mg.springboot.repository;

import mg.springboot.entity.Annonce;
import mg.springboot.entity.Utilisateur;
import mg.springboot.entity.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoitureRepository extends JpaRepository<Voiture, Integer> {
    @Query(value = "select c.nom attribut, count(*) valeur, c.code_couleur couleur from voiture v " +
            "join couleur c on v.couleur_id = c.id " +
            "group by couleur_id, c.nom, c.code_couleur", nativeQuery = true)
    List<Object> getStatsCouleurVoiture();

    @Query(value = "select b.nom attribut, count(*) valeur from voiture v " +
            "join sortie_voiture s on v.sortie_voiture_id = s.id " +
            "join boite_vitesse b on s.boite_vitesse_id = b.id " +
            "group by b.nom", nativeQuery = true)
    List<Object> getStatsBoiteVitesseVoiture();

    @Query(value = "select e.nom attribut, count(*) valeur from voiture v " +
            "join sortie_voiture s on v.sortie_voiture_id = s.id " +
            "join energie e on s.energie_id = e.id " +
            "group by e.nom", nativeQuery = true)
    List<Object> getStatsEnergieVoiture();

    @Query(value = "select e.designation attribut, count(*) valeur from voiture v " +
            "join etat_voiture e on v.etat_voiture_id = e.id " +
            "group by e.designation", nativeQuery = true)
    List<Object> getStatsEtatVoiture();

    @Query(value = "select p.nom attribut, count(*) valeur, p.code from voiture v " +
            "join sortie_voiture s on v.sortie_voiture_id = s.id " +
            "join pays p on s.pays_id = p.id " +
            "group by p.nom, p.code", nativeQuery = true)
    List<Object> getStatsPaysVoiture();

    @Query(value = "select annee attribut, count(*) valeur from voiture v  " +
            "join sortie_voiture s on v.sortie_voiture_id = s.id  " +
            "group by s.annee order by valeur limit 10", nativeQuery = true)
    List<Object> getStatsAnneeVoiture();

    @Query(value = "select concat(m.nom,' ',ma.nom) attribut, count(*) valeur from voiture v  " +
            "join sortie_voiture s on v.sortie_voiture_id = s.id  " +
            "join modele m on s.modele_id = m.id  " +
            "join marque ma on m.marque_id = ma.id  " +
            "group by m.nom, ma.nom order by valeur limit 10", nativeQuery = true)
    List<Object> getStatsModeleVoiture();

    @Query(value = "select ma.nom attribut, count(*) valeur from voiture v  " +
            "join sortie_voiture s on v.sortie_voiture_id = s.id  " +
            "join modele m on s.modele_id = m.id  " +
            "join marque ma on m.marque_id = ma.id  " +
            "group by ma.nom order by valeur limit 10", nativeQuery = true)
    List<Object> getStatsMarqueVoiture();

    @Query(value = "SELECT " +
            "    date(d.date), " +
            "    coalesce(sum(nb), 0) AS nb " +
            "FROM (SELECT generate_series(date(now()) - 30, date(now()), interval '1 day') AS date) d " +
            "         LEFT JOIN ( " +
            "    SELECT date(date_validation) AS start_date, date(date_fin) AS end_date, count(*) AS nb " +
            "    FROM annonce " +
            "    GROUP BY date(date_validation), date(date_fin) " +
            ") a ON d.date >= a.start_date AND d.date <= a.end_date " +
            "GROUP BY date(d.date) " +
            "ORDER BY date(d.date)", nativeQuery = true)
    List<Object> getStatsNbAnnonce();

    @Query(value = "SELECT " +
            "    to_char(date_trunc('month', d.date), 'YYYY-MM') AS mois, " +
            "    coalesce(ca, 0) AS chiffre_affaire " +
            "FROM (SELECT date_trunc('month', generate_series(date(now()) - interval '1 year', date(now()), interval '1 month')) date) d " +
            "LEFT JOIN (SELECT date_trunc('month', date_validation) d_valid_month, sum(prix) ca FROM annonce GROUP BY d_valid_month) a ON d_valid_month = d.date " +
            "GROUP BY d.date, ca " +
            "ORDER BY d.date", nativeQuery = true)
    List<Object> getChiffreAffaire12DernierMois();

    @Query(value = "select count(distinct t.utilisateur_id) filter (where last_activity + duration * interval '1 second' > now()) active," +
            "count(distinct u.id) total, " +
            "       round(cast(count(distinct t.utilisateur_id) filter (where last_activity + duration * interval '1 second' > now()) as decimal) / COALESCE(NULLIF(count(distinct u.id),0),1) * 100, 2) pourcentage " +
            "from token t " +
            "right join utilisateur u on u.id = t.utilisateur_id where deleted is false and level < " + Utilisateur.LEVEL_ADMIN, nativeQuery = true)
    Object getStatsUtilisateurEnLigne();

    @Query(value = "select count(*) filter ( where date_validation is null ) en_attente, " +
            "    count(*) filter ( where date_fin is null ) total, " +
            "    round(cast(count(*) filter ( where date_validation is null ) as decimal) / COALESCE(NULLIF(count(*),0),1) * 100, 2) pourcentage " +
            "from annonce", nativeQuery = true)
    Object getStatsAnnonceEnAttente();

    @Query(value = "SELECT count(*) filter ( where v.id is not null ) vendu, " +
            "       count(*) total, " +
            "    round(cast(count(*) filter ( where v.id is not null ) as decimal) / COALESCE(NULLIF(count(*),0),1) * 100, 2) pourcentage " +
            "from annonce " +
            "left join vente v on annonce.id = v.annonce_id", nativeQuery = true)
    Object getStatsNbVente();

    @Query(value = "SELECT count(*) filter ( where etat >= "+ Annonce.ETAT_VALIDE +" ) valide, " +
            "         count(*) total, " +
            "     round(cast(count(*) filter ( where etat >= " + Annonce.ETAT_VALIDE + " ) as decimal) / COALESCE(NULLIF(count(*),0),1) * 100,2) pourcentage " +
            "from annonce " +
            "where date_validation is not null", nativeQuery = true)
    Object getStatsValidationAnnonce();

    @Query(value = "select count(*) filter ( where v.id is not null ) vendeur, " +
            "        count(*) total, " +
            "     round(cast(count(*) filter ( where v.id is not null ) as decimal) / COALESCE(NULLIF(count(*),0),1) * 100,2) pourcentage " +
            "from utilisateur " +
            "left join voiture v on utilisateur.id = v.utilisateur_id where level <"+Utilisateur.LEVEL_ADMIN, nativeQuery = true)
    Object getNbUtilisateur();

    @Query(value = "select count(*) filter ( where premiere_main = true ) as premiere_main, " +
            "        count(*) total, " +
            "     round(cast(count(*) filter ( where premiere_main = true ) as decimal) / COALESCE(NULLIF(count(*),0),1) * 100,2) pourcentage " +
            "from voiture", nativeQuery = true)
    Object getNbVoiture();

    @Query(value = "select count(*) filter ( where date_fin is null and date_validation is not null ) as en_cours, " +
            "        count(*) total, " +
            "     round(cast(count(*) filter ( where date_fin is null and date_validation is not null ) as decimal) / COALESCE(NULLIF(count(*),0),1) * 100,2) pourcentage " +
            "from annonce", nativeQuery = true)
    Object getNbAnnonce();

    @Query(value = "SELECT count(*) filter ( where v.id is not null ) vendu, " +
            "       coalesce(sum(prix * (c.pourcentage / 100)), 0) as commission " +
            "from annonce " +
            "left join vente v on annonce.id = v.annonce_id " +
            "join commission c on v.commission_id = c.id", nativeQuery = true)
    Object getNbVente();
}