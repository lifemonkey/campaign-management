package campaign.repository;

import campaign.domain.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {

    Page<File> findAllByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<File> findAllByFileType(Integer fileType, Pageable pageable);

    Page<File> findAllByNameContainingIgnoreCaseAndFileType(String name, Integer fileType, Pageable pageable);

    List<File> findByNameIgnoreCase(String name);

    Optional<File> findByImageUrl(String imageUrl);

    List<File> findByRewardId(Long rewardId);

    List<File> findByCampaignId(Long campaignId);
}
