package com.app.happybox.service.order;

import com.app.happybox.domain.MemberOrderProductItemDTO;
import com.app.happybox.entity.order.MemberOrderProductItem;
import com.app.happybox.repository.order.MemberOrderProductItemRepository;
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

    @Override
    public Page<MemberOrderProductItemDTO> getListByIdAndSearchDate(Pageable pageable, Long memberId/*, LocalDateTime searchStartDate, LocalDateTime searchEndDate*/) {
        Page<MemberOrderProductItem> list =  memberOrderProductItemRepository.findOrderListByMemberIdAndSearchDateDescWithPaging_QueryDSL(pageable, memberId/*, searchStartDate, searchEndDate*/);
        List<MemberOrderProductItemDTO> listDTO = list.get().map(this::toMemberOrderProductItemDTO).collect(Collectors.toList());
        return new PageImpl<>(listDTO, pageable, list.getTotalElements());
    }
}
