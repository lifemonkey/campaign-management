package campaign.repository;

import campaign.domain.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {

    List<Voucher> findByRewardId(Long rewardId);

    List<Voucher> findByVoucherCodeIn(Set<String> voucherCodes);
}
