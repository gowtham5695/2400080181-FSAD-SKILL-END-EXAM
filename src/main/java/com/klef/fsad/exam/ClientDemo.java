package com.klef.fsad.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;

 class ClientDemo {

   
    private static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .buildSessionFactory();
            System.out.println("=================================================");
            System.out.println("  SessionFactory created successfully.");
            System.out.println("  Database : fsadendexam");
            System.out.println("=================================================\n");
        } catch (Exception e) {
            System.err.println("SessionFactory creation failed: " + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    
    public static int insertService(Service service) {
        Session session = sessionFactory.openSession();
        Transaction tx  = null;
        int generatedId = 0;

        try {
            tx = session.beginTransaction();

            // Persistent object approach: session.save() moves object
            // from Transient state → Persistent state (Hibernate tracks it)
            generatedId = (int) session.save(service);

            tx.commit();
            System.out.println("  [INSERT] Service saved with ID = " + generatedId);
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("  [INSERT ERROR] " + e.getMessage());
        } finally {
            session.close();
        }
        return generatedId;
    }

    public static int updateNameAndStatus(int id, String newName, String newStatus) {
        Session session = sessionFactory.openSession();
        Transaction tx  = null;
        int rowsAffected = 0;

        try {
            tx = session.beginTransaction();

            // HQL UPDATE with named parameters
            String hql = "UPDATE Service s "
                       + "SET s.name = :newName, s.status = :newStatus "
                       + "WHERE s.id = :serviceId";

            Query<?> query = session.createQuery(hql);
            query.setParameter("newName",    newName);    // named param
            query.setParameter("newStatus",  newStatus);  // named param
            query.setParameter("serviceId",  id);         // named param

            rowsAffected = query.executeUpdate();
            tx.commit();

            System.out.println("  [UPDATE] Rows affected = " + rowsAffected
                             + "  (ID=" + id + " → Name='" + newName
                             + "', Status='" + newStatus + "')");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("  [UPDATE ERROR] " + e.getMessage());
        } finally {
            session.close();
        }
        return rowsAffected;
    }

    
    public static List<Service> getAllServices() {
        Session session = sessionFactory.openSession();
        List<Service> services = null;

        try {
            String hql = "FROM Service s ORDER BY s.id";
            Query<Service> query = session.createQuery(hql, Service.class);
            services = query.getResultList();
        } catch (Exception e) {
            System.err.println("  [SELECT ERROR] " + e.getMessage());
        } finally {
            session.close();
        }
        return services;
    }

   
    public static Service getServiceById(int id) {
        Session session = sessionFactory.openSession();
        Service service = null;

        try {
            String hql = "FROM Service s WHERE s.id = :serviceId";
            Query<Service> query = session.createQuery(hql, Service.class);
            query.setParameter("serviceId", id);
            service = query.uniqueResult();
        } catch (Exception e) {
            System.err.println("  [SELECT BY ID ERROR] " + e.getMessage());
        } finally {
            session.close();
        }
        return service;
    }

    
    private static void printSection(String title) {
        System.out.println("\n" + "─".repeat(55));
        System.out.println("  " + title);
        System.out.println("─".repeat(55));
    }

    

    public static void main(String[] args) {

       
        printSection("OPERATION I : Inserting Service Records (Persistent Objects)");

        Service s1 = new Service(
            "Web Development",
            LocalDate.of(2024, 1, 15),
            "Active",
            "Full-stack web development service",
            "IT",
            15000.00,
            "TechSolutions Pvt Ltd",
            "Hyderabad"
        );

        Service s2 = new Service(
            "Network Setup",
            LocalDate.of(2024, 2, 20),
            "Active",
            "LAN/WAN network configuration and setup",
            "Networking",
            8000.00,
            "NetPro Services",
            "Vijayawada"
        );

        Service s3 = new Service(
            "Cloud Migration",
            LocalDate.of(2024, 3, 10),
            "Pending",
            "Migrate on-premise infra to AWS cloud",
            "Cloud",
            25000.00,
            "CloudBase Inc",
            "Bangalore"
        );

        Service s4 = new Service(
            "Database Administration",
            LocalDate.of(2024, 4, 5),
            "Inactive",
            "MySQL/Oracle DB management and tuning",
            "Database",
            12000.00,
            "DataCare Ltd",
            "Chennai"
        );

        Service s5 = new Service(
            "Cybersecurity Audit",
            LocalDate.of(2024, 5, 1),
            "Active",
            "Complete security audit and penetration testing",
            "Security",
            30000.00,
            "SecureNet Solutions",
            "Mumbai"
        );

      
        int id1 = insertService(s1);
        int id2 = insertService(s2);
        int id3 = insertService(s3);
        int id4 = insertService(s4);
        int id5 = insertService(s5);

        
        printSection("All Records After INSERT");
        List<Service> allServices = getAllServices();
        if (allServices != null) {
            allServices.forEach(System.out::println);
        }

        
        printSection("OPERATION II : Update Name & Status via HQL Named Parameters");

        System.out.println("\n  Updating Service ID = " + id1);
        updateNameAndStatus(id1, "Advanced Web Development", "Completed");

        System.out.println("\n  Updating Service ID = " + id3);
        updateNameAndStatus(id3, "AWS Cloud Migration Pro", "Active");

        System.out.println("\n  Updating Service ID = " + id4);
        updateNameAndStatus(id4, "Oracle DB Administration", "Active");

        
        printSection("All Records After UPDATE");
        allServices = getAllServices();
        if (allServices != null) {
            allServices.forEach(System.out::println);
        }

       
        printSection("Fetch Single Record by ID (HQL Named Parameter)");
        Service fetched = getServiceById(id2);
        if (fetched != null) {
            System.out.println(fetched);
        }

        
        printSection("Closing SessionFactory");
        sessionFactory.close();
        System.out.println("  SessionFactory closed. Program complete.");
    }
}
