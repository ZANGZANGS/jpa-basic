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

            Member member1 = new Member();
            member1.setUsername("member1");
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            em.persist(member2);


            em.flush();
            em.clear();

            Member findMember1 = em.getReference(Member.class, member1.getId());
            System.out.println("ref = " + findMember1.getClass());

            Member findMember2 = em.find(Member.class, member1.getId());
            System.out.println("find = " + findMember2.getClass());

            System.out.println("ref == find : " + (findMember1 == findMember2));

            em.flush();
            em.clear();

            //=============================//

            Member member = new Member();
            member.setUsername("zzs");
            em.persist(member);

            em.flush();
            em.clear();

            Member refMember = em.getReference(Member.class, member.getId());
            System.out.println("refMember = " + refMember.getClass());
            
//            refMember.getUsername(); //초기화를 위해 이런 메서드를 호출하면 없어 보인다
            Hibernate.initialize(refMember); //강제 초기화
            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(refMember)); //프록시 로딩 체크

            
            
            
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
