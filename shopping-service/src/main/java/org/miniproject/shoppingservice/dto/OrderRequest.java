package org.miniproject.shoppingservice.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequest {
    @NotNull(message = "customerId는 필수입니다.")
    private Long customerId;

    @NotNull(message = "productId는 필수입니다.")
    private Long productId;

    @NotNull(message = "quantity는 필수입니다.")
    @Min(value = 1, message = "수량은 1개 이상이어야 합니다.")
    private Integer quantity;
}
