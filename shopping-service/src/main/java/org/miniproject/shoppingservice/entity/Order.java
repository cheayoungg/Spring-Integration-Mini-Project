package org.miniproject.shoppingservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 고객 ID
     */
    @Column(nullable = false)
    private Long customerId;

    /**
     * 상품 ID
     */
    @Column(nullable = false)
    private Long productId;

    /**
     * 주문 수량
     */
    @Column(nullable = false)
    private Integer quantity;

    /**
     * 주문 상태
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    /**
     * 주문 생성 시간
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Entity 저장 전에 자동 실행
     */
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();

        if (this.status == null) {
            this.status = OrderStatus.CREATED;
        }
    }
}
