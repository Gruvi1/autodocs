package ru.nsu.astakhov.autodocs.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.astakhov.autodocs.core.model.StudentEntity;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
}
