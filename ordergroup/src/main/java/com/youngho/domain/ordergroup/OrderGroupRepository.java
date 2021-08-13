package com.youngho.domain.ordergroup;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;

public interface OrderGroupRepository extends JpaRepository<OrderGroup, Long> {

    @EntityGraph(attributePaths = {"lineItems", "lineItems.product"})
    @Query("select o from OrderGroup o")
    List<OrderGroup> findAllWithProducts();

    @EntityGraph(attributePaths = {"lineItems", "lineItems.product"})
    @Query("select o from OrderGroup o where o.id=:id")
    OrderGroup findByIdWithProducts(Long id);
}