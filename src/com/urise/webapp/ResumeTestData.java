package com.urise.webapp;

import com.urise.webapp.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ResumeTestData {

    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");
        System.out.println(resume.getFullName());

        resume.contacts.put(ContactType.TELEPHONE, "+7(921) 855-0482");
        resume.contacts.put(ContactType.SKYPE, "skype:grigory.kislin");
        resume.contacts.put(ContactType.MAIL, "gkislin@yandex.ru");
        resume.contacts.put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.contacts.put(ContactType.GITHUB, "https://github.com/gkislin");
        resume.contacts.put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        resume.contacts.put(ContactType.HOMEPAGE, "http://gkislin.ru/");

        for (Map.Entry<ContactType, String> entry : resume.contacts.entrySet()) {
            System.out.println(entry.getKey().getTitle());
            System.out.println(entry.getValue());
        }

        System.out.println(" ");

        resume.sections.put(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        resume.sections.put(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.sections.put(SectionType.ACHIEVEMENT, writeListSectionAchieve());
        resume.sections.put(SectionType.QUALIFICATIONS, writeListSectionQualif());
        resume.sections.put(SectionType.EXPERIENCE, new TextSection("Java Online Projects\n" +
                "10/2013 - Сейчас\tАвтор проекта.\n" +
                "Создание, организация и проведение Java онлайн проектов и стажировок.\n" +
                "Wrike\n" +
                "10/2014 - 01/2016\tСтарший разработчик (backend)\n" +
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.\n" +
                "RIT Center\n" +
                "04/2012 - 10/2014\tJava архитектор\n" +
                "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python\n" +
                "Luxoft (Deutsche Bank)\n" +
                "12/2010 - 04/2012\tВедущий программист\n" +
                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.\n" +
                "Yota\n" +
                "06/2008 - 12/2010\tВедущий специалист\n" +
                "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)\n" +
                "Enkata\n" +
                "03/2007 - 06/2008\tРазработчик ПО\n" +
                "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).\n" +
                "Siemens AG\n" +
                "01/2005 - 02/2007\tРазработчик ПО\n" +
                "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).\n" +
                "Alcatel\n" +
                "09/1997 - 01/2005\tИнженер по аппаратному и программному тестированию\n" +
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."));

        resume.sections.put(SectionType.EDUCATION, new TextSection("Coursera\n" +
                "03/2013 - 05/2013\t\"Functional Programming Principles in Scala\" by Martin Odersky\n" +
                "Luxoft\n" +
                "03/2011 - 04/2011\tКурс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"\n" +
                "Siemens AG\n" +
                "01/2005 - 04/2005\t3 месяца обучения мобильным IN сетям (Берлин)\n" +
                "Alcatel\n" +
                "09/1997 - 03/1998\t6 месяцев обучения цифровым телефонным сетям (Москва)\n" +
                "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики\n" +
                "09/1993 - 07/1996\tАспирантура (программист С, С++)\n" +
                "09/1987 - 07/1993\tИнженер (программист Fortran, C)\n" +
                "Заочная физико-техническая школа при МФТИ\n" +
                "09/1984 - 06/1987\tЗакончил с отличием"));

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

    private static ListSection createListSection(List<String> lines) {
        ListSection lastListSection = new ListSection();
        lastListSection.setLines(lines);
        return lastListSection;
    }
}
