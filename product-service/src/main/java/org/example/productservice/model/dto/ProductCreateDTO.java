package org.example.productservice.model.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.productservice.model.entity.Category;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateDTO {
    @Size(max = 255, message = "product name cannot exceed 255 word")
    private String name;
    @Size(max = 1000, message = "product name cannot exceed 1000 word")
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private Category category;
}
