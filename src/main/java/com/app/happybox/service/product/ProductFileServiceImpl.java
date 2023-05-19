package com.app.happybox.service.product;

import com.app.happybox.entity.file.ProductFile;
import com.app.happybox.entity.file.ProductFileDTO;
import com.app.happybox.repository.product.ProductFileRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductFileServiceImpl implements ProductFileService {

    private final ProductFileRepository productFileRepository;

    //    상품Id로 대표파일 조회하기
    @Override
    public ProductFileDTO findFileByProductId(Long id) {
        ProductFile productFile = productFileRepository.findFileByProductId(id).get();
        return fileToDTO(productFile);
    }
}
