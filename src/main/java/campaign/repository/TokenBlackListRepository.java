package campaign.repository;

import campaign.domain.TokenBlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface TokenBlackListRepository extends JpaRepository<TokenBlackList, Long> {

    List<TokenBlackList> findByExpiredDateBefore(Instant expiredDate);
}
