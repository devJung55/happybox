package com.app.happybox.service.order;

import com.app.happybox.domain.MemberOrderProductItemDTO;
import com.app.happybox.entity.file.ProductFile;
import com.app.happybox.entity.file.ProductFileDTO;
import com.app.happybox.entity.order.MemberOrderProductItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

public interface MemberOrderProductItemService {
//    마이페이지 주문내역(일반회원)
    public Page<MemberOrderProductItemDTO> getListByIdAndSearchDate(Pageable pageable, Long memberId/*, LocalDateTime searchStartDate, LocalDateTime searchEndDate*/);

    default MemberOrderProductItemDTO toMemberOrderProductItemDTO(MemberOrderProductItem memberOrderProductItem){
        return MemberOrderProductItemDTO.builder()
                .id(memberOrderProductItem.getId())
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
