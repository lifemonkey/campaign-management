package campaign.repository;

import campaign.domain.Reward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardRepository extends JpaRepository<Reward, Long> {

    Page<Reward> findAllByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Reward> findAllByPrizeType(Integer Integer, Pageable pageable);

    Page<Reward> findAllByNameContainingIgnoreCaseAndPrizeType(String name, Integer prizeType, Pageable pageable);
}
