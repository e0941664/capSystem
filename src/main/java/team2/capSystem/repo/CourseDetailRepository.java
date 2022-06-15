package team2.capSystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import team2.capSystem.model.*;

public interface CourseDetailRepository extends JpaRepository<CourseDetail, Integer> {
    Boolean existsBy();

}
