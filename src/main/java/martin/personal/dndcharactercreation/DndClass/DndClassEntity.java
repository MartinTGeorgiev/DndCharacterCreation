package martin.personal.dndcharactercreation.DndClass;

import com.fasterxml.jackson.annotation.JsonRawValue;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NamedQuery;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "dndclasses")
@NamedQuery(name = DndClassEntity.DND_CLASS_ENTITY_FIND_BY_NAME, query = "SELECT dc FROM DndClassEntity dc WHERE dndClassName = :dndClassName")
public class DndClassEntity {

    public static final String DND_CLASS_ENTITY_FIND_BY_NAME = "findByName";

    @Id
    @Column(name = "dnd_class_id")
    private Long dndClassID;

    @Column(name = "dnd_class_name")
    private String dndClassName;

    @JsonRawValue
    @Column(name = "dnd_class_description")
    private String dndClassDescription;
}
