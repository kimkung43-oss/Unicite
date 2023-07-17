package co.ke.unicite.Services;

import co.ke.unicite.Entities.RoomManagement.Courses;
import co.ke.unicite.Entities.UserManagement.Roles;
import co.ke.unicite.Entities.UserManagement.Users;
import co.ke.unicite.Exceptions.ResourceNotFoundException;
import co.ke.unicite.Models.CoursesModel;
import co.ke.unicite.Repositories.CoursesRepository;
import co.ke.unicite.Repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CourseServiceImpl implements CourseService{
    @Autowired
    CoursesRepository coursesRepository;

    @Autowired
    UsersRepository usersRepository;
    @Override
    public List<Courses> getAllCourses() {
        return coursesRepository.findAll();
    }

    @Override
    public List<Courses> getCoursesByUser(Long userId) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Users not found with id: " + userId));
        return coursesRepository.findByUser(user);
    }

    @Override
    public Courses getCourseById(Long id) {
        return coursesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Courses not found with id: " + id));
    }

    @Override
    public Courses createCourse(CoursesModel coursesModel) {
        Users user = usersRepository.findById(coursesModel.getUserId()).get();
//                .orElseThrow(() -> new ResourceNotFoundException("Users not found with id: " + userId));
//        Long userIdFromUserObject = user.getId();
        Courses courses = new Courses();
        courses.setCourseName(coursesModel.getCourseName());
        courses.setCourseCode(coursesModel.getCourseCode());
        courses.setDescription(coursesModel.getDescription());
        courses.setVenueName(coursesModel.getVenueName());
        courses.setUser(user);
        return coursesRepository.save(courses);
    }

    @Override
    public Courses updateCourse(Long id, CoursesModel coursesModel) {
        Courses existingCourse = coursesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Courses not found with id: " + id));
        existingCourse.setCourseName(coursesModel.getCourseName());
        existingCourse.setCourseCode(coursesModel.getCourseCode());
        existingCourse.setDescription(coursesModel.getDescription());
        existingCourse.setDescription(coursesModel.getDescription());
        existingCourse.setVenueName(coursesModel.getVenueName());
        existingCourse.setIsActive(true);
        return coursesRepository.save(existingCourse);
    }

    @Override
    public Courses deleteCourse(Long id) {
        Courses updatedCourse = coursesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Courses not found with id: " + id));
        updatedCourse.setInactive();

        return coursesRepository.save(updatedCourse);
    }

    @Override
    public boolean existsByCourseName(String name) {
        return coursesRepository.existsByCourseName(name);
    }

    @Override
    public Optional<Courses> findById(Long id) {
        return coursesRepository.findById(id);
    }
}
