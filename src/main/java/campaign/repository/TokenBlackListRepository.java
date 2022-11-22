package campaign.repository;

import campaign.domain.TokenBlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TokenBlackListRepository extends JpaRepository<TokenBlackList, Long> {

    void deleteAllByExpiredDateBefore(LocalDateTime now);
}
