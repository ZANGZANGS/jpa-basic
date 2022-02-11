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

            //벌크연산 update, delete 쿼리 한번으로 여러 테이블의 로우를 변경한다.
            //영속성 컨텍스트를 무시하고 데이터베이스에 직접 쿼리를 날린다.
            //따라서 영속성 컨텍스트의 데이터가 맞지 않을 수 있다.
            //해결방법
            //벌크 연산을 완료한 후 영속성 컨텍스트를 초기화하여 해결할 수 있다.
            int resultCount = em.createQuery("update Member m set m.age = :age")
                    .setParameter("age", 20)
                    .executeUpdate();//쿼리 날리기 전 이전 영속성 컨텍스트의 변경사항은 자동으로 FlUSH 된다.

            Member findMember = em.find(Member.class, member1.getId()); //다시 조회해도 영속성 컨텍스트에는 변경 감지가 되지 않아 데이터를 가져오지 않음.
            //em.clear();

            //실제론 DB에는 나이가 20이지만, 영속성 컨텍스트에는 데이터가 그대로다.
            System.out.println("member1 age = " + member1.getAge());
            System.out.println("member2 age = " + member2.getAge());
            System.out.println("member3 age = " + member3.getAge());

            System.out.println("resultCount = " + resultCount);

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
