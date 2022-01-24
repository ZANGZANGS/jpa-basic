package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {
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
            teamB.setName("teamA");
            em.persist(teamB);
            
            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setTeam(teamB);
            em.persist(member2);



            em.flush();
            em.clear();

            List<Member> members = em.createQuery("select m from Member m ", Member.class).getResultList();


            members.get(0).getTeam().getName();
            
            
            tx.commit();


        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }

        emf.close();
    }

    private static void logic(Member findMember1, Member findMember2) {
        System.out.println(findMember1 instanceof Member);
    }

    private static void printMember(Member member) {
        System.out.println("username = " + member.getUsername());
    }

    private static void printMemberAndTeam(Member member) {
        System.out.println("=============");
        System.out.println("username = " + member.getUsername());
        Team team = member.getTeam();
        System.out.println("team = " + team.getName());
        System.out.println("=============");

    }
}
