package jpql.main;

import jpql.domain.Member;
import jpql.domain.MemberType;
import jpql.domain.Team;

import javax.persistence.*;
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

            List<String> resultList = em.createQuery("select function('group_concat', m.username) from Member m", String.class)
                    .getResultList();

            System.out.println("결과 = "+resultList.get(0));
            System.out.println("result.size = " + resultList.size());



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
