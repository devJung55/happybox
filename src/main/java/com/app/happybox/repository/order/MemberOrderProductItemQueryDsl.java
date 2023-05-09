package com.app.happybox.repository.order;

import com.app.happybox.entity.order.MemberOrderProductItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface MemberOrderProductItemQueryDsl {
//    마이페이지 주문내역
    public Page<MemberOrderProductItem> findOrderListByMemberIdAndSearchDateDescWithPaging_QueryDSL(Pageable pageable, Long memberId, LocalDateTime searchStartDate, LocalDateTime searchEndDate);

//    마이페이지 취소내역
    public Page<MemberOrderProductItem> findCancleListByMemberIdAndSearchDateDescWithPaging_QueryDSL(Pageable pageable, Long memberId, LocalDateTime searchStartDate, LocalDateTime searchEndDate);

//    마이페이지 판매내역
    public Page<MemberOrderProductItem> findSaleListByDistributorIdAndSearchDateDescWithPaging_QueryDSL(Pageable pageable, Long distributorId, LocalDateTime searchStartDate, LocalDateTime searchEndDate);
}