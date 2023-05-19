package com.app.happybox.service.product;

import com.app.happybox.entity.file.ProductFile;
import com.app.happybox.entity.file.ProductFileDTO;

import java.util.Optional;

public interface ProductFileService {

    //    상품Id로 대표파일 조회하기
    public ProductFileDTO findFileByProductId(Long id);

    default ProductFileDTO fileToDTO(ProductFile productFile){
        return ProductFileDTO.builder()
                .id(productFile.getId())
                .fileUuid(productFile.getFileUuid())
                .fileRepresent(productFile.getFileRepresent())
                .filePath(productFile.getFilePath())
                .fileOrgName(productFile.getFileOrgName())
                .build();
    }

}
