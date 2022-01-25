package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");//persistence.xml의 persistence-unit name 속성

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setHomeAddress(new Address("homeCity", "street", "zipcode"));
            em.persist(member1);

            member1.getFavoriteFoods().add("치킨");
            member1.getFavoriteFoods().add("피자");
            member1.getFavoriteFoods().add("햄버거");

            AddressEntity addressEntity = new AddressEntity("old1", "street1", "zipcode1");
            member1.getAddressHistory().add(addressEntity);
            member1.getAddressHistory().add(new AddressEntity("old2", "street2", "zipcode2"));

            em.persist(member1);

            em.flush();
            em.clear();

            System.out.println("======================= START ========================");
            Member findMember = em.find(Member.class, member1.getId());

            //멤버 주소 변경
            findMember.setHomeAddress(new Address("zzz", "newStreet", "zipcode"));

            //음식 변경
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("김치찌개");

            //주소 기록 변경
            findMember.getAddressHistory().remove(new AddressEntity("old1", "street1", "zipcode1"));
            findMember.getAddressHistory().add(new AddressEntity("newCity1", "street1", "zipcode1"));

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

}
