package co.develhope.email1.students.services;

import co.develhope.email1.tools.ConnectionTools;
import org.springframework.stereotype.Service;
import co.develhope.email1.students.entities.Student;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    static List<Student> students = Arrays.asList(
            new Student("1", "Camilla", "Conte", ConnectionTools.recipientEmail),
            new Student("2", "Pippo", "Pippi", "pippo@pippi.com"),
            new Student("3", "Pluto", "Pluti", "pluto@pluti.com"),
            new Student("4", "Pape", "Rino", "pape@rino.eu")
            );

    /**
     * Questo metodo prende in ingresso un id e mi restituisce lo studente che ha quell'id
     * Cosa ne faccio? Vorrò leggere la sua email (student.getEmail()) per potergli inviare una notifica
     */
    public Student getStudentById(String studentId) throws NullPointerException {
        Optional<Student> studentFromList =
                students
                    .stream()
                        .filter(student -> student.getId().equals(studentId))
                            .findAny();
        //return studentFromList.isPresent() ? studentFromList.get() : null;
        if(studentFromList.isEmpty()){
            throw new NullPointerException ("Cannot find student with id " + studentId);
            //return null;
        }
        return studentFromList.get();
    }
}
