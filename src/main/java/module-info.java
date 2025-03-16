    module lk.ijse.gdse71.orm_course_work {
    requires javafx.controls;
    requires javafx.fxml;
        requires org.hibernate.orm.core;


        opens lk.ijse.gdse71.orm_course_work to javafx.fxml;
    exports lk.ijse.gdse71.orm_course_work;
        exports lk.ijse.gdse71.orm_course_work.controller;
        opens lk.ijse.gdse71.orm_course_work.controller to javafx.fxml;
    }