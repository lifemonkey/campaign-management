package campaign.repository;

import campaign.domain.Reward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardRepository extends JpaRepository<Reward, Long> {

    Page<Reward> findAllByNameContaining(String name, Pageable pageable);

    Page<Reward> findAllByPrizeType(Integer Integer, Pageable pageable);

    Page<Reward> findAllByNameContainingAndPrizeType(String name, Integer prizeType, Pageable pageable);
}
