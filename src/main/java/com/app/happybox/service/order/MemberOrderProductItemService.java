package com.app.happybox.service.order;

import com.app.happybox.domain.AddressDTO;
import com.app.happybox.domain.MemberOrderProductItemDTO;
import com.app.happybox.domain.SearchDateDTO;
import com.app.happybox.entity.file.ProductFile;
import com.app.happybox.entity.file.ProductFileDTO;
import com.app.happybox.entity.order.MemberOrderProductItem;
import com.app.happybox.entity.user.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

public interface MemberOrderProductItemService {
//    마이페이지 주문내역(일반회원)
    public Page<MemberOrderProductItemDTO> getListByIdAndSearchDate(Pageable pageable, Long memberId, SearchDateDTO searchDateDTO);

//    일반 마이페이지 주문건수 조회
    public Long getOrderCountByMemberId(Long id);

//    마이페이지 판매내역(유통회원)
    public Page<MemberOrderProductItemDTO> getSaleListByDistributorIdAndSearchDate(Pageable pageable, Long distributorId, SearchDateDTO searchDateDTO);

//    마이페이지 판매건수 조회(유통회원)
    public Long getSalesCountByDistributorId(Long distributorId);

    default AddressDTO toAddressDTO(Address address) {
        return AddressDTO.builder()
                .zipcode(address.getZipcode())
                .firstAddress(address.getFirstAddress())
                .addressDetail(address.getAddressDetail())
                .build();
    }

    default MemberOrderProductItemDTO toMemberOrderProductItemDTO(MemberOrderProductItem memberOrderProductItem){
        return MemberOrderProductItemDTO.builder()
                .id(memberOrderProductItem.getId())
                .memberName(memberOrderProductItem.getMemberOrderProduct().getMember().getMemberName())
                .userPhoneNumber(memberOrderProductItem.getMemberOrderProduct().getReceiverPhoneNumber())
                .userAddress(toAddressDTO(memberOrderProductItem.getMemberOrderProduct().getOrderAddress()))
                .productName(memberOrderProductItem.getProduct().getProductName())
                .orderStock(memberOrderProductItem.getOrderAmount())
                .productPrice(memberOrderProductItem.getProduct().getProductPrice())
                .createdDate(memberOrderProductItem.getCreatedDate())
                .productFiles(memberOrderProductItem.getProduct().getProductFiles().stream().map(file -> productFileToDTO(file)).collect(Collectors.toList()))
                .build();
    }

    default ProductFileDTO productFileToDTO(ProductFile productFile) {
        return ProductFileDTO.builder()
                .id(productFile.getId())
                .filePath(productFile.getFilePath())
                .fileUuid(productFile.getFileUuid())
                .fileOrgName(productFile.getFileOrgName())
                .build();
    }
}
