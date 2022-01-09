package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ResumeTestData {

    public static Resume fillResume(String uuid, String fullname) {
        Resume resume = new Resume(uuid, fullname);
        fillContacts(resume);
//      fillSections(resume);
        return resume;
    }

    public static void main(String[] args) {
        Resume resumeTest = new Resume("Григорий Кислин");
        fillContacts(resumeTest);
        fillSections(resumeTest);
        System.out.println(resumeTest.getFullName());
        printContacts(resumeTest);
        printSections(resumeTest);
    }

    private static void fillContacts(Resume resume) {
        resume.contacts.put(ContactType.PHONE, "+7(812) 855-0482");
        resume.contacts.put(ContactType.MOBILE, "+7(921) 855-1234");
        resume.contacts.put(ContactType.HOME_PHONE, "+8(495) 123-1234");
        resume.contacts.put(ContactType.SKYPE, "skype:grigory.kislin");
        resume.contacts.put(ContactType.MAIL, "gkislin@yandex.ru");
        resume.contacts.put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.contacts.put(ContactType.GITHUB, "https://github.com/gkislin");
        resume.contacts.put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        resume.contacts.put(ContactType.HOMEPAGE, "http://gkislin.ru/");

    }

    private static void printContacts(Resume resume) {
        for (Map.Entry<ContactType, String> entry : resume.contacts.entrySet()) {
            System.out.println(entry.getKey().getTitle());
            System.out.println(entry.getValue());
        }
        System.out.println(" ");
    }

    private static void fillSections(Resume resume) {
        resume.sections.put(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        resume.sections.put(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.sections.put(SectionType.ACHIEVEMENT, writeListSectionAchieve());
        resume.sections.put(SectionType.QUALIFICATIONS, writeListSectionQualif());
        resume.sections.put(SectionType.EXPERIENCE, writeListSectionExperience());
        resume.sections.put(SectionType.EDUCATION, writeListSectionEducation());
    }

    private static OrganizationSection writeListSectionExperience() {
        List<Organization.Position> positions1 = new ArrayList<>();

        positions1.add(new Organization.Position(2013, Month.OCTOBER, "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок."));
        Organization work1 = new Organization("Java Online Projects", null, positions1);

        List<Organization.Position> positions2 = new ArrayList<>();
        positions2.add(new Organization.Position(2014, Month.JANUARY, "Старший разработчик (backend).", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        Organization work2 = new Organization("Wrike", null, positions2);

        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(work1);
        organizationList.add(work2);
        return new OrganizationSection(organizationList);
    }

    private static OrganizationSection writeListSectionEducation() {
        List<Organization.Position> positions = new ArrayList<>();
        positions.add(new Organization.Position(1987, Month.SEPTEMBER, 1993, Month.JULY, "Аспирантура (программист С, С++)", null));
        positions.add(new Organization.Position(1993, Month.SEPTEMBER, 1996, Month.JULY, "Инженер (программист Fortran, C)", null));

        Organization edu = new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", null, positions);

        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(edu);

        return new OrganizationSection(organizationList);
    }

    public static void printSections(Resume resume) {
        for (Map.Entry<SectionType, AbstractSection> entry : resume.sections.entrySet()) {
            System.out.println(entry.getKey().getTitle());
            System.out.println(entry.getValue().toString());
            System.out.println(" ");
        }

    }

    private static ListSection writeListSectionAchieve() {
        List<String> lines = new ArrayList<>();
        lines.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        lines.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        lines.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        lines.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        lines.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        lines.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        return createListSection(lines);
    }

    private static ListSection writeListSectionQualif() {
        List<String> lines = new ArrayList<>();
        lines.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        lines.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        lines.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        lines.add("MySQL, SQLite, MS SQL, HSQLDB");
        lines.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        lines.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        lines.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        lines.add("Python: Django.");
        lines.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        lines.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        lines.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        lines.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix,");
        lines.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.");
        lines.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
        lines.add("Родной русский, английский \"upper intermediate\"");
        return createListSection(lines);
    }

    private static ListSection createListSection(List<String> items) {
        ListSection lastListSection = new ListSection(items);
        return lastListSection;
    }
}
