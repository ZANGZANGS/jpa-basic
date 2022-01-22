package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter@Setter
public class Item {

    @Id @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;
    private String name;
    private Integer price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    List<Category> categories = new ArrayList<>();
}
