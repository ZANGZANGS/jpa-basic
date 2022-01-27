package jpql.main;

import jpql.domain.Address;
import jpql.domain.Member;
import jpql.domain.Team;
import jpql.dto.MemberDto;

import javax.persistence.*;
import java.util.List;

public class JPQLMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");//persistence.xml의 persistence-unit name 속성
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = Member.builder()
                            .username("member1")
                            .age(10)
                            .build();
            em.persist(member);

            em.flush();
            em.clear();

            List<MemberDto> resultList = em.createQuery("select new jpql.dto.MemberDto(m.username, m.age) from Member m", MemberDto.class)
                    .getResultList();
            //생성자 호출하듯이 한다. 실제 dto 클래스에는 생성자가 필요하다.


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }

        emf.close();
    }
}
