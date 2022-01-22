package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity

public class Product {

    @Id @GeneratedValue
    private Long id;
    private String name;

    //다대다 사용X
//    @ManyToMany(mappedBy = "products")
//    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<MemberProduct> memberProducts = new ArrayList<>();
}
