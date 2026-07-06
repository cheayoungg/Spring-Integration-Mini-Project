package org.miniproject.mocksap.repository;

import org.miniproject.mocksap.entity.SapOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SapOrderRepository extends JpaRepository<SapOrder,Long> {
}
