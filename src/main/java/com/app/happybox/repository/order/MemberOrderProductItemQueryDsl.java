package com.app.happybox.repository.order;

import com.app.happybox.entity.order.MemberOrderProductItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface MemberOrderProductItemQueryDsl {
//    마이페이지 주문내역(일반회원)
    public Page<MemberOrderProductItem> findOrderListByMemberIdAndSearchDateDescWithPaging_QueryDSL(Pageable pageable, Long memberId/*, LocalDateTime searchStartDate, LocalDateTime searchEndDate*/);

//    마이페이지 취소내역(일반회원)
    public Page<MemberOrderProductItem> findCancleListByMemberIdAndSearchDateDescWithPaging_QueryDSL(Pageable pageable, Long memberId, LocalDateTime searchStartDate, LocalDateTime searchEndDate);

//    마이페이지 판매내역(유통회원)
    public Page<MemberOrderProductItem> findSaleListByDistributorIdAndSearchDateDescWithPaging_QueryDSL(Pageable pageable, Long distributorId, LocalDateTime searchStartDate, LocalDateTime searchEndDate);

//    판매건 조회(유통회원)
    public Long findSaleCountByDistributorAndPurchaseStatus_QueryDSL(Long distributorId);

//    마이페이지 취소내역(유통회원)
    public Page<MemberOrderProductItem> findCancleListByDistributorIdAndSearchDateDescWithPaging_QueryDSL(Pageable pageable, Long distributorId, LocalDateTime searchStartDate, LocalDateTime searchEndDate);

//    취소건 조회(유통회원)
    public Long findCancleCountByDistributorAndPurchaseStatus_QueryDSL(Long distributorId);
}