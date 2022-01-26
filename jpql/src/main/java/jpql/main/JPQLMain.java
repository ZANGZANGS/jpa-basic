package jpql.main;

import jpql.domain.Member;

import javax.persistence.*;

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

            TypedQuery<Member> query = em.createQuery("select m from Member m where m.username = :username", Member.class);
            query.setParameter("username", "member1");
            Member singleResult = query.getSingleResult();
            System.out.println("singleResult = " + singleResult.getUsername());


//            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
//            Query query2 = em.createQuery("select m.username, m.age from Member m", Member.class); //불명확한 반환 타입



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
