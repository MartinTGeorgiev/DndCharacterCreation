package martin.personal.dndcharactercreation.DndClass;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DndClassDto {

    private String hitDie;
    private List<String> proficiencyChoices;
    private List<String> proficiencies;


}
