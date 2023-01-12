package campaign.repository;

import campaign.domain.RewardCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RewardConditionRepository extends JpaRepository<RewardCondition, Long> {

    List<RewardCondition> findAllByRuleId(Long ruleId);
}
