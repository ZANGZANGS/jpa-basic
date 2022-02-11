package jpql.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Team {

    @Id @GeneratedValue
    @Column(name = "team_id")
    private Long id;
    private String name;

//    @BatchSize(size = 100)
    @OneToMany(mappedBy = "team")
    List<Member> members = new ArrayList<>();
}
