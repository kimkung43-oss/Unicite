package co.ke.unicite.Repositories;

import co.ke.unicite.Entities.Chats.ForumReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumReplyRepository extends JpaRepository<ForumReply, Long> {
    List<ForumReply> findByForumThread(long id);

    List<ForumReply> findByUser(long id);
    List<ForumReply> findByUserAndForumThread(long id, long threadId);


}
