package com.app.happybox.service.product;


import com.app.happybox.domain.product.ProductCartDTO;
import com.app.happybox.domain.product.ProductDTO;
import com.app.happybox.entity.file.ProductFile;
import com.app.happybox.entity.file.ProductFileDTO;
import com.app.happybox.entity.product.Product;
import com.app.happybox.entity.product.ProductCart;
import com.app.happybox.entity.user.User;
import com.app.happybox.exception.ProductNotFoundException;
import com.app.happybox.exception.UserNotFoundException;
import com.app.happybox.repository.product.ProductCartRepository;
import com.app.happybox.repository.product.ProductRepository;
import com.app.happybox.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductCartServiceImpl implements ProductCartService {
    private final ProductCartRepository productCartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public List<ProductCartDTO> findAllByUserId(Long id) {
        List<ProductCart> carts = productCartRepository.findAllByUserId_QueryDSL(id);
        List<ProductCartDTO> productCartDTOS = new ArrayList<>();
        carts.forEach(productCart -> productCartDTOS.add(cartToDTO(productCart)));
        return productCartDTOS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveCart(ProductCartDTO cartDTO, Long userId, Long productId) {
        User user = null;
        Product product = null;
        try {
            user = userRepository.findById(userId).orElseThrow(() -> {
                throw new UserNotFoundException();
            });
            product = productRepository.findById(productId).orElseThrow(() -> {
                throw new ProductNotFoundException();
            });
        } catch (RuntimeException e) {
            log.error("상품 번호 혹은 유저 번호 잘못됨.");
            return -1L;
        }

        ProductCart cart = productCartRepository.save(productCartDTOToEntity(cartDTO, user, product));

        return cart.getId();
    }

    @Override
    public ProductDTO findProductById(Long id) {
        Product product = productCartRepository.findProductById(id).get();

        return productToProductDTO(product);
    }

}
