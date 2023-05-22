package com.app.happybox.service.order;

import com.app.happybox.domain.MemberOrderProductItemDTO;
import com.app.happybox.domain.SearchDateDTO;
import com.app.happybox.entity.order.MemberOrderProductItem;
import com.app.happybox.repository.order.MemberOrderProductItemRepository;
import com.app.happybox.repository.order.MemberOrderProductQueryDsl;
import com.app.happybox.repository.order.MemberOrderProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Qualifier("memberOrderProductItem")
public class MemberOrderProductItemServiceImpl implements MemberOrderProductItemService {
    private final MemberOrderProductItemRepository memberOrderProductItemRepository;
    private final MemberOrderProductRepository memberOrderProductRepository;

    @Override
    public Page<MemberOrderProductItemDTO> getListByIdAndSearchDate(Pageable pageable, Long memberId, SearchDateDTO searchDateDTO) {
        Page<MemberOrderProductItem> list =  memberOrderProductItemRepository.findOrderListByMemberIdAndSearchDateDescWithPaging_QueryDSL(pageable, memberId, searchDateDTO);
        List<MemberOrderProductItemDTO> listDTO = list.get().map(this::toMemberOrderProductItemDTO).collect(Collectors.toList());
        return new PageImpl<>(listDTO, pageable, list.getTotalElements());
    }

    @Override
    public Long getOrderCountByMemberId(Long id) {
        return memberOrderProductRepository.findOrderCountByMemberIdAndOrderStatus_QueryDSL(id);
    }

    @Override
    public Page<MemberOrderProductItemDTO> getSaleListByDistributorIdAndSearchDate(Pageable pageable, Long distributorId, SearchDateDTO searchDateDTO) {
        Page<MemberOrderProductItem> productItems = memberOrderProductItemRepository.findSaleListByDistributorIdAndSearchDateDescWithPaging_QueryDSL(pageable, distributorId, searchDateDTO);
        List<MemberOrderProductItemDTO> productItemDTOS = productItems.get().map(this::toMemberOrderProductItemDTO).collect(Collectors.toList());
        return new PageImpl<>(productItemDTOS, pageable, productItems.getTotalElements());
    }

    @Override
    public Long getSalesCountByDistributorId(Long distributorId) {
        return memberOrderProductItemRepository.findSaleCountByDistributorAndPurchaseStatus_QueryDSL(distributorId);
    }
}
