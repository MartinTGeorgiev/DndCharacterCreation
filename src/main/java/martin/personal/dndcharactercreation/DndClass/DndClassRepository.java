package martin.personal.dndcharactercreation.DndClass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DndClassRepository extends JpaRepository<DndClassEntity, Long> {

    DndClassEntity findFirstByDndClassID(Long dndClassId);
    List<DndClassEntity> findAll();
}
