package dk.cphbusiness.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.cphbusiness.dtos.PoemDTO;
import dk.cphbusiness.persistence.HibernateConfig;
import dk.cphbusiness.persistence.daos.IDAO;
import dk.cphbusiness.persistence.daos.PoemDAO;
import dk.cphbusiness.persistence.entities.*;
import dk.cphbusiness.security.entities.Role;
import dk.cphbusiness.security.entities.User;
import dk.cphbusiness.utils.Utils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * Purpose: To populate the database with users and roles
 * Author: Thomas Hartmann
 */
public class Populator {
    ObjectMapper objectMapper = new Utils().getObjectMapper();
    IDAO poemDAO = new PoemDAO(HibernateConfig.getEntityManagerFactory());
    // method to create users and roles before each test
    public void createUsersAndRoles(EntityManagerFactory emf) {
        try (EntityManager em = emf.createEntityManager()) {
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

    public void createPoemEntities(EntityManagerFactory emf){
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Poem").executeUpdate();
            em.getTransaction().commit();

            JsonNode poemJsonArray = objectMapper.readTree(new File("poems.json")).get("poems");
            System.out.println("POEMS: "+poemJsonArray);
            Set<PoemDTO> poems = objectMapper.convertValue(poemJsonArray, new TypeReference<Set<PoemDTO>>(){});

            poems.forEach(poem -> poemDAO.create(poem.toEntity()));
//            Poem p1 = new Poem("The Raven","Freddy", "Once upon a midnight dreary, while I pondered, weak and weary, Over many a quaint and curious volume of forgotten lore— While I nodded, nearly napping, suddenly there came a tapping, As of some one gently rapping, rapping at my chamber door. “’Tis some visitor,” I muttered, “tapping at my chamber door— Only this and nothing more.”");
//            em.persist(p1);

            em.createQuery("SELECT p FROM Poem p", Poem.class).getResultList().forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Populator populator = new Populator();
//        populator.createUsersAndRoles(HibernateConfig.getEntityManagerFactory());
        populator.createPoemEntities(HibernateConfig.getEntityManagerFactory());
    }
}

