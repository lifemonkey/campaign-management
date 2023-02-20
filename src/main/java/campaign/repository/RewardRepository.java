package campaign.repository;

import campaign.domain.Reward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RewardRepository extends JpaRepository<Reward, Long> {

    Page<Reward> findAllByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Reward> findAllByPrizeType(Integer Integer, Pageable pageable);

    Page<Reward> findAllByNameContainingIgnoreCaseAndPrizeType(String name, Integer prizeType, Pageable pageable);

    Optional<Reward> findByNameIgnoreCase(String name);

    List<Reward> findByNameStartsWithIgnoreCase(String name);
}
