package mg.springboot.repository;

import mg.springboot.security.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageRepository extends JpaRepository<Page, String> {
    List<Page> findAllByUrlStartingWith(String url);
}