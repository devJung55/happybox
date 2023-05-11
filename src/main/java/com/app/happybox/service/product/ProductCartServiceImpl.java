package com.app.happybox.service.product;


import com.app.happybox.entity.product.ProductCartDTO;
import com.app.happybox.repository.product.ProductCartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductCartServiceImpl implements ProductCartService {
    private final ProductCartRepository productCartRepository;

    @Override
    public List<ProductCartDTO> findAllByUserId(Long id) {
        return productCartRepository.findAllByUserId_QueryDSL(id).stream()
                .map(this::cartToDTO).collect(Collectors.toList());
    }
}
