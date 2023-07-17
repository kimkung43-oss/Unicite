package co.ke.unicite.Repositories;

import co.ke.unicite.Entities.Chats.ForumThreads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumThreadsRepository extends JpaRepository<ForumThreads, Long> {
//    ForumThreads existsByTitle(String title);
//    List<ForumThreads> findByUser(long Id);

//    ForumThreads findByForumReply(long id);
}
