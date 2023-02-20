package campaign.repository;

import campaign.domain.RewardCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RewardConditionRepository extends JpaRepository<RewardCondition, Long> {

    List<RewardCondition> findAllByRuleId(Long ruleId);

    Page<RewardCondition> findByAmountMinGreaterThanEqualAndTimesMinGreaterThanEqual(
        Pageable pageable, Float amountMin, Integer timesMin);

    Page<RewardCondition> findByAmountMinGreaterThanEqualAndTimesMinGreaterThanEqualAndAmountMaxLessThanEqual(
        Pageable pageable, Float amountMin, Integer timeMin, Float amountMax);

    Page<RewardCondition> findByAmountMinGreaterThanEqualAndTimesMinGreaterThanEqualAndTimesMaxLessThanEqual(
        Pageable pageable, Float amountMin, Integer timeMin, Integer timesMax);

    Page<RewardCondition> findByAmountMinGreaterThanEqualAndTimesMinGreaterThanEqualAndAmountMaxLessThanEqualAndTimesMaxLessThanEqual(
        Pageable pageable, Float amountMin, Integer timeMin, Float amountMax, Integer timesMax);
}
