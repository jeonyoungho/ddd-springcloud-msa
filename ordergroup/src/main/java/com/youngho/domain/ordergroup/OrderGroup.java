package com.youngho.domain.ordergroup;

import com.youngho.domain.lineitem.LineItem;
import com.youngho.domain.product.Product;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Boolean status;

    @OneToMany(mappedBy = "orderGroup", cascade = CascadeType.ALL)
    private Set<LineItem> lineItems = new LinkedHashSet<>();

    public void updateLineItems(Set<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    @Builder
    public OrderGroup(String description, Set<LineItem> lineItems) {
        this.description = description;
        this.status = true;
        if(lineItems != null)
            this.lineItems = lineItems;
    }
}
