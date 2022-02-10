package jpql.main;

import jpql.domain.Member;
import jpql.domain.MemberType;
import jpql.domain.Team;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

public class JPQLMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");//persistence.xml의 persistence-unit name 속성
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member1 = Member.builder()
                    .username("member1")
                    .age(10)
                    .team(team)
                    .type(MemberType.ADMIN.ADMIN)
                    .build();
            em.persist(member1);

            Member member2 = Member.builder()
                    .username("member2")
                    .age(12)
                    .team(team)
                    .type(MemberType.ADMIN.ADMIN)
                    .build();
            em.persist(member2);

            em.flush();
            em.clear();


//            members. 경로 탐색이 되지 않는다. 명시적인 Join을 사용할 것
//            List<Collection> resultList = em.createQuery("select t.members from Team t", Collection.class)
//                    .getResultList();

            List<Member> resultList = em.createQuery("select m from Team t join t.members m", Member.class)
                    .getResultList();

            for (Member m : resultList) {
                System.out.println("Member = " + m.toString());
            }



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
