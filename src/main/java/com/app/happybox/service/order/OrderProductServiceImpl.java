package com.app.happybox.service.order;

import com.app.happybox.entity.product.ProductCart;
import com.app.happybox.entity.user.User;
import com.app.happybox.exception.UserNotFoundException;
import com.app.happybox.repository.product.ProductCartRepository;
import com.app.happybox.repository.user.UserRepository;
import com.app.happybox.type.ErrorCode;
import com.app.happybox.type.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProductServiceImpl implements OrderProductService {
    private final ProductCartRepository productCartRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void saveProductOrder(List<Long> productCartIds, Long userId) {
        // 회원 ID로 회원 find
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        boolean isMember = user.getUserRole().equals(Role.MEMBER);
        boolean isWelfare = user.getUserRole().equals(Role.WELFARE);

        if(!isMember && !isWelfare) throw new IllegalArgumentException(ErrorCode.AUTHENTICATION_FAILED.name());

        List<ProductCart> productCarts = productCartRepository.findAllByIdsWithDetail_QueryDSL(productCartIds);
//        productCarts.stream().map(productCart -> {});
    }
}
