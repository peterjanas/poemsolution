package rest;

import dk.cphbusiness.persistence.HibernateConfig;
import dk.cphbusiness.persistence.entities.IJPAEntity;
import dk.cphbusiness.persistence.entities.Poem;
import dk.cphbusiness.security.entities.Role;
import dk.cphbusiness.security.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.Map;

public class TestUtils {
    Poem poem1, poem2;
    // method to create users and roles before each test
    public void createUsersAndRoles(EntityManagerFactory emfTest) {
        try (EntityManager em = emfTest.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM User u").executeUpdate();
            em.createQuery("DELETE FROM Role r").executeUpdate();
            User user = new User("user", "user123");
            User admin = new User("admin", "admin123");
            User superUser = new User("super", "super123");
            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            user.addRole(userRole);
            admin.addRole(adminRole);
            superUser.addRole(userRole);
            superUser.addRole(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(superUser);
            em.persist(userRole);
            em.persist(adminRole);
            em.getTransaction().commit();
        }
    }

    public Map<String, IJPAEntity> createPoemsInDB(EntityManagerFactory emfTest) {
        try (EntityManager em = emfTest.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Poem").executeUpdate();
            poem1 = new Poem("Roses", "Benjamin Rosewelt", "Roses are red, violets are blue, sugar is sweet, and so are you");
            poem2 = new Poem("Lillies", "Benjamin Lollipop", "Lillies are white, violets are blue, sugar is sweet, and so are you");
            em.persist(poem1);
            em.persist(poem2);
            em.getTransaction().commit();
//            em.createQuery("SELECT p FROM Poem p", Poem.class).getResultList().forEach(System.out::println);
            return Map.of("poem1", poem1 // MAX 10 entries this way (alternatively use Map.ofEntries(Map.entry("Person1", p1))
                    , "poem2", poem2
            );
        }
    }

    public static void main(String[] args) {
        HibernateConfig.setTestMode(true);
        EntityManagerFactory emfTest = HibernateConfig.getEntityManagerFactoryForTest();
        new TestUtils().createPoemsInDB(emfTest);
    }
}

