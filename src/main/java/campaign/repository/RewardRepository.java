package campaign.repository;

import campaign.domain.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RewardRepository extends JpaRepository<Reward, Long> {

    List<Reward> findAllByNameContainingIgnoreCase(String name);

    List<Reward> findAllByPrizeType(Integer Integer);

    List<Reward> findAllByNameContainingIgnoreCaseAndPrizeType(String name, Integer prizeType);

    Optional<Reward> findByNameIgnoreCase(String name);

    List<Reward> findByNameStartsWithIgnoreCase(String name);

    List<Reward> findAllByCampaignId(Long campaignId);
}
