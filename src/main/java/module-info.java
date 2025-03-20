    module lk.ijse.gdse.orm_course_work {
    requires java.naming;  // Add thisrequires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires jakarta.persistence;
    requires static lombok;
    requires javafx.controls;

    opens lk.ijse.gdse71.orm_course_work.entity;
    opens lk.ijse.gdse71.orm_course_work to javafx.fxml;
    exports lk.ijse.gdse71.orm_course_work;
    exports lk.ijse.gdse71.orm_course_work.controller;
    opens lk.ijse.gdse71.orm_course_work.controller to javafx.fxml;
    }