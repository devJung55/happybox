package com.app.happybox.service.order;

import java.util.List;

public interface OrderProductService {

    public void saveProductOrder(List<Long> productCartIds, Long userId);
}
