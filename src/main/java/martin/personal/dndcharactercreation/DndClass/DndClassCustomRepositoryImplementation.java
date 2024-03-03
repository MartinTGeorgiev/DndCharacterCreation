package martin.personal.dndcharactercreation.DndClass;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import static martin.personal.dndcharactercreation.DndClass.DndClassEntity.DND_CLASS_ENTITY_FIND_BY_NAME;

@Repository
public class DndClassCustomRepositoryImplementation implements DndClassCustomRepository{

    private final EntityManager entityManager;

    public DndClassCustomRepositoryImplementation(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public DndClassEntity findByName(String dndClassName) {
            return entityManager
                .createNamedQuery(DND_CLASS_ENTITY_FIND_BY_NAME, DndClassEntity.class)
                .setParameter("dndClassName", dndClassName)
                .getSingleResult();
    }

    @Override
    public void updateDndClass(DndClassEntity dndClassEntity) {
        this.entityManager.persist(dndClassEntity);
        this.entityManager.flush();
    }
}
