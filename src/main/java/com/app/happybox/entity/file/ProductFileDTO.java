package com.app.happybox.entity.file;

import com.app.happybox.domain.product.ProductDTO;
import com.app.happybox.type.FileRepresent;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class ProductFileDTO {
    private Long id;

    private String filePath;

    private String fileUuid;

    private String fileOrgName;

    private FileRepresent fileRepresent;

    private ProductDTO productDTO;

    @Builder
    public ProductFileDTO(Long id, String filePath, String fileUuid, String fileOrgName, FileRepresent fileRepresent) {
        this.id = id;
        this.filePath = filePath;
        this.fileUuid = fileUuid;
        this.fileOrgName = fileOrgName;
        this.fileRepresent = fileRepresent;
    }

    public void setFileRepresent(FileRepresent fileRepresent) {
        this.fileRepresent = fileRepresent;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }
}
