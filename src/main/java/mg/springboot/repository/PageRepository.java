package mg.springboot.repository;

import mg.springboot.security.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageRepository extends JpaRepository<Page, String> {

    @Query("select p from Page p where ?1 like concat(p.url, '%')")
    List<Page> findAllByUrlStartsWith(String url);

}