package co.ke.unicite.Repositories;

import co.ke.unicite.Entities.Chats.MessageRecipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRecipientRepository extends JpaRepository<MessageRecipient, Long> {
    List<MessageRecipient> findByIsRead(boolean isRead);
}
