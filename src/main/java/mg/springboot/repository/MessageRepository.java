package mg.springboot.repository;

import mg.springboot.entity.Discussion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Discussion, Integer> {
    List<Discussion> searchDiscussionByEnvoyeurIdOrReceveurIdOrderByDateheureDesc(String idUtilisateur1, String idUtilisateur2);
}