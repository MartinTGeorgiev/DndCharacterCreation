package martin.personal.dndcharactercreation.DndClass;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DndClassManager {
    public static final String DND_CLASS_BASE_URL = "https://www.dnd5eapi.co/api/classes";

    private final DndClassRepository dndClasses;
    private final DndClassCustomRepository dndClassesCustom;


    public List<DndClassEntity> getDndClasses() {
        return dndClasses.findAll();
    }

    @Transactional
    public DndClassDto getSpecificDndClassInfo(String dndClassName) throws JsonProcessingException {
        WebClient.Builder builder = WebClient.builder();
        DndClassEntity selectedDndClass = dndClassesCustom.findByName(dndClassName);

        String responseJson = builder
                .build()
                .get()
                .uri(DND_CLASS_BASE_URL + "/" + selectedDndClass.getDndClassName().toLowerCase())
                .retrieve()
                .bodyToMono(String.class)
                .block();


        DndClassDto dndClassDto = extractDndClassJsonData(responseJson);
        if (!StringUtils.hasLength(selectedDndClass.getDndClassDescription())) {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(dndClassDto);
            selectedDndClass.setDndClassDescription(json);
//            selectedDndClass.setDndClassDescription(String.valueOf(dndClassDto));
            dndClassesCustom.updateDndClass(selectedDndClass);

        }
        return dndClassDto;
    }

    private DndClassDto extractDndClassJsonData(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        String hitDie = String.valueOf(jsonObject.getInt("hit_die"));
        List<String> proficiencyChoicesList = new ArrayList<>();
        List<String> proficienciesList = new ArrayList<>();
        JSONArray proficiencyChoicesJsonArray = jsonObject
                .getJSONArray("proficiency_choices");
        JSONObject proficiencyChoicesObject = proficiencyChoicesJsonArray
                .getJSONObject(0);

        JSONArray proficiencyChoicesObjectArray = proficiencyChoicesObject
                .getJSONObject("from")
                .getJSONArray("options");

        for (Object proficiencyOption : proficiencyChoicesObjectArray) {
            JSONObject proficiencyChoiceJsonObject = (JSONObject) proficiencyOption;
            String proficiencyName = proficiencyChoiceJsonObject
                    .getJSONObject("item")
                    .getString("name");
            proficiencyChoicesList.add(proficiencyName);
        }

        JSONArray proficienciesJsonArray = jsonObject
                .getJSONArray("proficiencies");

        for (Object proficiency : proficienciesJsonArray) {
            JSONObject proficiencyJsonObject = (JSONObject) proficiency;
            String proficiencyName = proficiencyJsonObject
                    .getString("name");
            proficienciesList.add(proficiencyName);
        }

        return DndClassDto
                .builder()
                .hitDie("1d" + hitDie)
                .proficiencyChoices(proficiencyChoicesList)
                .proficiencies(proficienciesList)
                .build();
    }
}
