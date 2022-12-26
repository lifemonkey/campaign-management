package campaign.repository;

import campaign.domain.GeneratedTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeneratedTimeRepository extends JpaRepository<GeneratedTime, Long> {

    List<GeneratedTime> findAllByCampaignId(Long campaignId);
}
