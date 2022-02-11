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

            Team teamA = new Team();
            teamA.setName("teamA");
            em.persist(teamA);
            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);


            Member member1 = Member.builder()
                    .username("회원1")
                    .age(10)
                    .team(teamA)
                    .type(MemberType.ADMIN.ADMIN)
                    .build();
            em.persist(member1);

            Member member2 = Member.builder()
                    .username("회원2")
                    .age(12)
                    .team(teamA)
                    .type(MemberType.ADMIN.ADMIN)
                    .build();
            em.persist(member2);

            Member member3 = Member.builder()
                    .username("회원3")
                    .age(16)
                    .team(teamB)
                    .type(MemberType.ADMIN.ADMIN)
                    .build();
            em.persist(member3);

            em.flush();
            em.clear();


//            List<Member> resultList = em.createQuery("select m from Member m join fetch m.team", Member.class)
//                    .getResultList();
//
//            for (Member m : resultList) {
//                System.out.println("Member = " + m.getUsername() + "  Team = " + m.getTeam().getName());
//            }

            Member result = em.createNamedQuery("Member.findByUsername", Member.class)
                    .setParameter("username", "회원1")
                    .getSingleResult();

            System.out.println("member = " + result.getUsername());



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
