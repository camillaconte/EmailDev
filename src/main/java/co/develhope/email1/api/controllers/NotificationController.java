package co.develhope.email1.api.controllers;

import co.develhope.email1.emails.services.EmailService;
import co.develhope.email1.api.entities.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import co.develhope.email1.students.entities.Student;
import co.develhope.email1.students.services.StudentService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ServerErrorException;

import javax.mail.MessagingException;

@Controller
public class NotificationController {

    @Autowired
    StudentService studentService;

    @Autowired
    EmailService emailService;

    //VERSIONE CARLO + CAMI
    @PostMapping("/notification")
    public ResponseEntity sendNotification(@RequestBody NotificationDTO payload){
        try {
            Student studentToNotify = studentService.getStudentById(payload.getContactId());
            //se non trovo lo studente viene lanciata un'eccezione e il metodo termina qui
            //altrimenti proseguiamo
            System.out.println("studentToNotify: " + studentToNotify);
            emailService.sendTo(studentToNotify.getEmail(), payload.getTitle(), payload.getText());
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch(NullPointerException npe) {
            System.out.println("Student with id " + payload.getContactId() + "does not exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("I couldn't find the student :(");
        }catch (MailException me){
            System.out.println("Problem in sending email");
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(me.getMessage());
        }catch(ServerErrorException se){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The server is broken :(");
        }
    }


    @PostMapping("/notification-HTML")
    public ResponseEntity sendNotificationHTML(@RequestBody NotificationDTO payload){
        try {
            Student studentToNotify = studentService.getStudentById(payload.getContactId());
            //se non trovo lo studente viene lanciata un'eccezione e il metodo termina qui
            //altrimenti proseguiamo
            System.out.println("studentToNotify: " + studentToNotify);
            emailService.sendToHTML(studentToNotify.getEmail(), payload.getTitle(), payload.getText());
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch(NullPointerException npe) {
            System.out.println("Student with id " + payload.getContactId() + "does not exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("I couldn't find the student :(");
        }catch (MailException me){
            System.out.println("Problem in sending email");
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(me.getMessage());
        }catch(ServerErrorException se){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The server is broken :(");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Messaging exception");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("HTML Message not correct");
        }
    }

    /* VERSIONE DEVELHOPE
    @PostMapping("/notification")
    public ResponseEntity sendNotification(@RequestBody NotificationDTO payload){
        try {
            Student studentToNotify = studentService.getStudentById(payload.getContactId());
            //se non trovo lo studente viene lanciata un'eccezione e il metodo termina qui
            //altrimenti proseguiamo
            System.out.println("studentToNotify: " + studentToNotify);
            if (studentToNotify == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("I couldn't find the student :(");
            emailService.sendTo(studentToNotify.getEmail(), payload.getTitle(), payload.getText());
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The server is broken :(");
        }
    }*/
}
