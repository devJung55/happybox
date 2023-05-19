package com.app.happybox.service.order;

import com.app.happybox.domain.AddressDTO;
import com.app.happybox.domain.OrderInfoDTO;
import com.app.happybox.entity.order.MemberOrderProduct;
import com.app.happybox.entity.order.MemberOrderProductItem;
import com.app.happybox.entity.order.WelfareOrderProduct;
import com.app.happybox.entity.order.WelfareOrderProductItem;
import com.app.happybox.entity.payment.Payment;
import com.app.happybox.entity.product.Product;
import com.app.happybox.entity.product.ProductCart;
import com.app.happybox.entity.user.Address;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.User;
import com.app.happybox.entity.user.Welfare;
import com.app.happybox.exception.CartNotFoundException;
import com.app.happybox.exception.NotEnoughStockException;
import com.app.happybox.exception.UserNotFoundException;
import com.app.happybox.exception.UserRoleUnsuitableException;
import com.app.happybox.repository.order.MemberOrderProductRepository;
import com.app.happybox.repository.order.WelfareOrderProductRepository;
import com.app.happybox.repository.payment.PaymentRepository;
import com.app.happybox.repository.product.ProductCartRepository;
import com.app.happybox.repository.user.UserRepository;
import com.app.happybox.type.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Qualifier("product")
public class ProductOrderServiceImpl implements ProductOrderService {
    private final ProductCartRepository productCartRepository;
    private final UserRepository userRepository;
    private final MemberOrderProductRepository memberOrderProductRepository;
    private final WelfareOrderProductRepository welfareOrderProductRepository;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    // 장바구니 id들과 회원 id 받아옴
    public Long saveProductOrder(OrderInfoDTO orderInfoDTO) {
        if(orderInfoDTO.getCartIds().isEmpty()) return -1L;

        // 회원 ID로 회원 find
        User user = userRepository.findById(orderInfoDTO.getId()).orElseThrow(() -> {
            throw new UserNotFoundException(); // 회원 조회 실패 시 예외 발생
        });

        // 결제내역
        Payment payment = null;
        Long paymentAmount = 0L;

        boolean isMember = user.getUserRole().equals(Role.MEMBER);
        boolean isWelfare = user.getUserRole().equals(Role.WELFARE);

        // 알맞은 회원 타입이 아닐 경우 예외 발생
        if (!isMember && !isWelfare) throw new UserRoleUnsuitableException();

        List<ProductCart> productCarts = productCartRepository.findAllByIdsWithDetail_QueryDSL(orderInfoDTO.getCartIds());

        if(productCarts.isEmpty()) throw new CartNotFoundException();

        if (isMember) {
            Member member = (Member) user;
            MemberOrderProduct orderProduct = new MemberOrderProduct(
                    orderInfoDTO.getDeliveryName(),
                    orderInfoDTO.getDeliveryPhoneNumber(),
                    new Address(orderInfoDTO.getAddressDTO().getZipcode(), orderInfoDTO.getAddressDTO().getFirstAddress(), orderInfoDTO.getAddressDTO().getAddressDetail()),
                    member
            );

            // 주문 product List 로 반환
            List<MemberOrderProductItem> items = productCarts.stream().map(this::productCartToMemberOrderProductItem).collect(Collectors.toList());

            // order 편의 메소드
            orderProduct.addProducts(items);

            // 주문 저장
            memberOrderProductRepository.save(orderProduct);

            paymentAmount = items.stream().mapToLong(item -> item.getProduct().getProductPrice() * item.getOrderAmount()).sum();
            payment = new Payment(paymentAmount, member, orderProduct);

        } else {
            Welfare welfare = (Welfare) user;
            WelfareOrderProduct orderProduct = new WelfareOrderProduct(
                    orderInfoDTO.getDeliveryName(),
                    orderInfoDTO.getDeliveryPhoneNumber(),
                    new Address(orderInfoDTO.getAddressDTO().getZipcode(), orderInfoDTO.getAddressDTO().getFirstAddress(), orderInfoDTO.getAddressDTO().getAddressDetail()),
                    welfare
            );

            // 주문 product List 로 반환
            List<WelfareOrderProductItem> items = productCarts.stream().map(this::productCartToWelfareOrderProductItem).collect(Collectors.toList());

            // order 편의 메소드
            orderProduct.addProducts(items);

            // 주문 저장
            welfareOrderProductRepository.save(orderProduct);

            paymentAmount = items.stream().mapToLong(item -> item.getProduct().getProductPrice() * item.getOrderAmount()).sum();
            payment = new Payment(paymentAmount, welfare, orderProduct);
        }
        // 장바구니 내역 삭제
        productCartRepository.deleteAllById(orderInfoDTO.getCartIds());

        // 결제내역 저장
        paymentRepository.save(payment);

        return payment.getPaymentAmount();
    }

    // 장바구니를 통해 실제 주문 Item 반환 (회원)
    private MemberOrderProductItem productCartToMemberOrderProductItem(ProductCart productCart) {
        Long orderAmount = productCart.getCartOrderAmount();
        Product product = productCart.getProduct();

        // 상품 재고와 주문 수 업데이트, 주문 수량보다 상품 재고가 많으면 예외 발생
        updateProductStock(orderAmount, product);

        return new MemberOrderProductItem(orderAmount, product);
    }

    // 장바구니를 통해 실제 주문 Item 반환 (복지관)
    private WelfareOrderProductItem productCartToWelfareOrderProductItem(ProductCart productCart) {
        Long orderAmount = productCart.getCartOrderAmount();
        Product product = productCart.getProduct();

        // 상품 재고와 주문 수 업데이트, 주문 수량보다 상품 재고가 많으면 예외 발생
        updateProductStock(orderAmount, product);

        return new WelfareOrderProductItem(orderAmount, product);
    }

    private void updateProductStock(Long orderAmount, Product product) {
        // 주문 수량보다 상품 재고가 많으면 예외 발생,
        Long updatedStock = product.getProductStock() - orderAmount;
        if (updatedStock < 0) throw new NotEnoughStockException();

        // 재고 업데이트
        product.setProductStock(updatedStock);
        // 주문 수 업데이트
        product.setProductOrderCount(product.getProductOrderCount() + 1);
    }
}
