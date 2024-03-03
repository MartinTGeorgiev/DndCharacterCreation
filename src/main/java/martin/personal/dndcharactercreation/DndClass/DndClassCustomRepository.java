package martin.personal.dndcharactercreation.DndClass;

public interface DndClassCustomRepository {

    DndClassEntity findByName(String dndClassName);

    void updateDndClass(DndClassEntity dndClassEntity);
}
