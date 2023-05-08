package com.app.happybox.repository.order;

import com.app.happybox.entity.order.MemberOrderProductItem;
import com.app.happybox.entity.user.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberOrderProductItemQueryDsl {
//    마이페이지 주문내역
    public Page<MemberOrderProductItem> findOrderListByMemberIdDescWithPaging_QueryDSL(Pageable pageable, Long id);
}
