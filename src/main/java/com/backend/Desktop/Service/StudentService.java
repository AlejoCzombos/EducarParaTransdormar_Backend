package com.backend.Desktop.Service;

import com.backend.Desktop.Entity.Class;
import com.backend.Desktop.Entity.Division;
import com.backend.Desktop.Entity.Parent;
import com.backend.Desktop.Entity.Student;
import com.backend.Desktop.Repository.DivisionRepository;
import com.backend.Desktop.Repository.ParentRepository;
import com.backend.Desktop.Repository.StudentRepository;
import com.backend.Login.User.User;
import com.backend.Login.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final Logger log = LoggerFactory.getLogger(StudentService.class);
    private final UserRepository userRepository;

    private final DivisionRepository divisionRepository;
    private final StudentRepository studentRepository;
    private final ParentRepository parentRepository;
    private final NoteService noteService;

    public ResponseEntity<Student> getById(Integer id){

        Optional<Student> studentOptional = studentRepository.findById(id);

        if (studentOptional.isEmpty()){
            log.warn("Student is not found");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(studentOptional.get());
    }

    public ResponseEntity<List<Class>> getClassesByStudent(Integer studentId){

        Optional<Student> studentOptional = studentRepository.findById(studentId);

        if (studentOptional.isEmpty()){
            log.warn("Student is not found");
            return ResponseEntity.notFound().build();
        }

        Student student = studentOptional.get();

        return ResponseEntity.ok(student.getDivision().getClasses());
    }

    public void createManually(Student student, String username){
        Optional<User> userOptional = userRepository.findByUsername(username);

        student.setId(userOptional.get().getId());
        studentRepository.save(student);
    }

    public ResponseEntity<Student> create(Student student){

        if (student.getId() != null){
            log.warn("trying to create a student with id");
            return ResponseEntity.badRequest().build();
        }

        Student result = studentRepository.save(student);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<Student> linkParent(Integer studentId, Integer parentId){

        Optional<Student> studentOptional = studentRepository.findById(studentId);
        Optional<Parent> parentOptional = parentRepository.findById(parentId);

        if (studentOptional.isEmpty() || parentOptional.isEmpty()){
            log.warn("Student or Parent is not found");
            return ResponseEntity.notFound().build();
        }

        Student student = studentOptional.get();
        Parent parent = parentOptional.get();

        student.getParents().add(parent);

        Student result = studentRepository.save(student);

        return ResponseEntity.ok(result);
    }

    public ResponseEntity<Student> linkDivision(Integer studentId, Integer divisionId) {

        Optional<Student> studentOptional = studentRepository.findById(studentId);
        Optional<Division> divisionOptional = divisionRepository.findById(divisionId);

        if (studentOptional.isEmpty() || divisionOptional.isEmpty()) {
            log.warn("Student or Division is not found");
            return ResponseEntity.notFound().build();
        }

        Student student = studentOptional.get();
        Division division = divisionOptional.get();

        student.setDivision(division);
        student.setNotes( noteService.createNotes(student) );

        Student result = studentRepository.save(student);

        return ResponseEntity.ok(result);
    }

    public ResponseEntity<Student> deleteById(Integer id){
        Optional<Student> studentOptional = studentRepository.findById(id);

        if (studentOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        studentRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }


}