package Service;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import service.Service;
import validation.GradeValidator;
import validation.HomeworkValidator;
import validation.StudentValidator;
import validation.Validator;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTest {

    static Service service;

    @BeforeAll
    public static void init() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Homework> homeworkValidator = new HomeworkValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "students.xml");
        HomeworkXMLRepository fileRepository2 = new HomeworkXMLRepository(homeworkValidator, "homework.xml");
        GradeXMLRepository fileRepository3 = new GradeXMLRepository(gradeValidator, "grades.xml");

        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @Test
    public void findAllStudents() {
        long n = service.findAllStudents().spliterator().getExactSizeIfKnown();
        service.saveStudent("42","Test", 222);
        long m = service.findAllStudents().spliterator().getExactSizeIfKnown();
        if (n == -1) {
            assertEquals(m, n+2);
        }
        assertEquals(m, n + 1);
        service.deleteStudent("42");
    }

    @ParameterizedTest
    @ValueSource(strings = {"42","43","44"})
    void findAllHomework(String homeWorkId) {
        long n = service.findAllHomework().spliterator().getExactSizeIfKnown();
        service.saveHomework(homeWorkId, "Matek", 10, 8);
        long m = service.findAllHomework().spliterator().getExactSizeIfKnown();
        if (n == -1) {
            assertEquals(m, n+2);
        }
        assertEquals(m, n + 1);
        service.deleteHomework(homeWorkId);
    }

    @Test
    void saveStudent() {

        Iterator<Student> it = service.findAllStudents().iterator();

        boolean existStud = false;

        while(it.hasNext()){
            if (it.next().getID().equals("42")) {
                existStud = true;
            }
        }

        if (!existStud){
            service.saveStudent("42", "Test", 222);
        }

        it = service.findAllStudents().iterator();

        existStud = false;

        while(it.hasNext()){
            if (it.next().getID().equals("42")) {
                existStud = true;
            }
        }
        assertTrue(existStud);

        service.deleteStudent("42");
    }

    @Test
    void deleteStudent() {
        Iterator<Student> it = service.findAllStudents().iterator();

        boolean existStud = false;

        while(it.hasNext()){
            if (it.next().getID().equals("42")) {
                existStud = true;
            }
        }

        if (!existStud){
            service.saveStudent("42", "Test", 222);
        }

        service.deleteStudent("42");

        it = service.findAllStudents().iterator();

        existStud = false;

        while(it.hasNext()){
            if (it.next().getID().equals("42")) {
                existStud = true;
            }
        }
        assertFalse(existStud);
    }

    @Test
    void updateStudent() {
        Iterator<Student> it = service.findAllStudents().iterator();

        boolean existStud = false;

        while(it.hasNext()){
            if (it.next().getID().equals("42")) {
                existStud = true;
            }
        }

        if (!existStud){
            service.saveStudent("42", "Test", 222);
        }

        service.updateStudent("42", "UpdatedTest", 223);

        it = service.findAllStudents().iterator();

        boolean updated = false;

        while(it.hasNext()){
            Student st = it.next();
            if (st.getID().equals("42")) {
                if(st.getName().equals("UpdatedTest") && st.getGroup() == 223) {
                    updated = true;
                }
            }
        }
        assertTrue(updated);

        service.deleteStudent("42");
    }
}
