package mg.springboot.security;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "url", nullable = false)
    private String url; // ex: /connexion

    @Column(name = "level", nullable = false)
    private int level; // sup ou égal

    @Column(name = "exact", nullable = false)
    private boolean exact = false; // == level

    public boolean isValid(int level) {
        if (isExact()) {
            return getLevel() == level;
        }
        return getLevel() <= level;
    }
}
