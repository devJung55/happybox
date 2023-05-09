package com.app.happybox.repository.order;

public interface MemberOrderProductQueryDsl {
    //    마이페이지 주문건 조회(일반회원)
    public Long findOrderCountByMemberIdAndOrderStatus_QueryDSL(Long memberId);
}
