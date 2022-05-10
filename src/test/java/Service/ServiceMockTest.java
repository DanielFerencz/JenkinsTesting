package Service;

import domain.Homework;
import domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import service.Service;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;

public class ServiceMockTest {
    Service  service;

    @Mock
    private StudentXMLRepository studentXMLRepository;

    @Mock
    private HomeworkXMLRepository homeworkXMLRepository;

    @Mock
    private GradeXMLRepository gradeXMLRepository;

    @BeforeEach
    void init() {

        studentXMLRepository = mock(StudentXMLRepository.class);

        homeworkXMLRepository = mock(HomeworkXMLRepository.class);

        gradeXMLRepository = mock(GradeXMLRepository.class);

        List<Homework> homeworks = new ArrayList<>();
        homeworks.add(new Homework("42", "Matek", 9, 6));
        homeworks.add(new Homework("43", "Info", 10, 7));



        Mockito.when(homeworkXMLRepository.findAll()).thenReturn(homeworks);
        Mockito.when(studentXMLRepository.update(any(Student.class)))
                .thenReturn(new Student("42", "Test", 42));
        Mockito.when(studentXMLRepository.delete(anyString()))
                .thenReturn(new Student("42", "Test", 42));



        service = new Service(studentXMLRepository, homeworkXMLRepository, gradeXMLRepository);
    }

    @Test
    public void findAllHomework(){
        assertEquals(2, service.findAllHomework().spliterator().getExactSizeIfKnown());
    }

    @Test
    void updateStudent() {
        assertEquals(1, service.updateStudent("42", "Test", 42));
        Mockito.verify(studentXMLRepository).update(new Student("42", "Test", 42));
    }

    @Test
    void deleteStudent() {
        assertEquals(1,service.deleteStudent("42"));
    }

}
