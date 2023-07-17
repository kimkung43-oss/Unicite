package co.ke.unicite.Repositories;

import co.ke.unicite.Entities.Chats.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessagesRepository extends JpaRepository<Messages, Long> {
//    List<Messages> findByUser(long id);

//    List<Messages> findByConversation(long id);
}
