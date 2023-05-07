package com.app.happybox.repository.order;

import com.app.happybox.entity.order.WelfareOrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WelfareOrderProductRepository extends JpaRepository<WelfareOrderProduct, Long>, WelfareOrderProductQueryDsl {
}
