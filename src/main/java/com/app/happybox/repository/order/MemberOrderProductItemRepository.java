package com.app.happybox.repository.order;

import com.app.happybox.entity.order.MemberOrderProductItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberOrderProductItemRepository extends JpaRepository<MemberOrderProductItem, Long>, MemberOrderProductItemQueryDsl {
}
