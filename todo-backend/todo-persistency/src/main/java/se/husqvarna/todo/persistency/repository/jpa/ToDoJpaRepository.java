package se.husqvarna.todo.persistency.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.husqvarna.todo.persistency.entity.ToDoEntity;

@Repository
public interface ToDoJpaRepository extends JpaRepository<ToDoEntity, Long> {

}
