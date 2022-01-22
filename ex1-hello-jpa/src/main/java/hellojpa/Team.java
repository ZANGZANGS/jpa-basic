package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;


    //다대일 양방향 매핑
    @OneToMany(mappedBy = "team")
    public List<Member> members = new ArrayList<>(); //arrayList 초기화 관례, null point 안뜨도록 처리

    //일대다 (update 쿼리가 여러번 나가네)
//    @OneToMany
//    @JoinColumn(name = "TEAM_ID") joincolumn 이 없으면 중간 테이블(조인 테이블)을 생성해 버린다.
//    public List<Member> members = new ArrayList<>();


    public void addMember(Member member) {
        member.setTeam(this);
        members.add(member);
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> lismembers) {
        this.members = lismembers;
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
