package mg.springboot.repository;

import mg.springboot.model.Discussion;
import mg.springboot.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscussionRepository extends MongoRepository<Discussion, Integer> {
    @Query("{'$or':[{'utilisateur1':?0}, {'utilisateur1':?0}]}")
    List<Discussion> findDiscussions(String user);

    @Query("{'$or':[{'$and':[{'utilisateur1':?0}, {'utilisateur2':?1}]}, {'$and':[{'utilisateur1':?0}, {'utilisateur2':?1}]}]}")
    Discussion findDiscussionsByTwoUsers(String userId1, String userId2);
}
