package campaign.service.mapper;

import campaign.domain.Voucher;
import campaign.service.dto.VoucherDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class VoucherMapper {

    public VoucherDTO voucherToVoucherDTO(Voucher voucher) {
        return new VoucherDTO(voucher);
    }

    public List<VoucherDTO> voucherToVoucherDTOs(List<Voucher> voucherList) {
        return voucherList.stream()
            .filter(Objects::nonNull)
            .map(this::voucherToVoucherDTO)
            .collect(Collectors.toList());
    }

    public Voucher voucherDTOToVoucher(VoucherDTO voucherDTO) {
        if (voucherDTO == null) {
            return null;
        } else {
            Voucher voucher = new Voucher();
            if (voucherDTO.getId() != null) {
                voucher.setId(voucherDTO.getId());
            }
            voucher.setVoucherNumber(voucherDTO.getVoucherNumber());
            voucher.setVoucherCode(voucherDTO.getVoucherCode());
            voucher.setDescription(voucherDTO.getDescription());
            voucher.setImport(voucherDTO.getImport());
            voucher.setStartDate(voucherDTO.getStartDate());
            voucher.setExpiredDate(voucherDTO.getExpiredDate());

            return voucher;
        }
    }

    public List<Voucher> voucherDTOToVouchers(List<VoucherDTO> voucherDTOs) {
        return voucherDTOs.stream()
            .filter(Objects::nonNull)
            .map(this::voucherDTOToVoucher)
            .collect(Collectors.toList());
    }

}
