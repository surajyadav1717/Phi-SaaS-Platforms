package com.dashboard.saas.dtos;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
public class ProductVariantBulkRequestDTO {

    @NotEmpty
    private List<ProductVariantRequestDTO> variants;


    public @NotEmpty List<ProductVariantRequestDTO> getVariants() {
        return variants;
    }

    public void setVariants(@NotEmpty List<ProductVariantRequestDTO> variants) {
        this.variants = variants;
    }

}
