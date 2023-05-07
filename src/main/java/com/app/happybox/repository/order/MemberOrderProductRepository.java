package com.app.happybox.repository.order;

import com.app.happybox.entity.order.MemberOrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberOrderProductRepository extends JpaRepository<MemberOrderProduct, Long>, MemberOrderProductQueryDsl {
}
