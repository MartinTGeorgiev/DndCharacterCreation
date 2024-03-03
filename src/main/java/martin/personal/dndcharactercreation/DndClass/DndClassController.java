package martin.personal.dndcharactercreation.DndClass;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DndClassController {

    private final DndClassManager dndClassManager;

    @Autowired
    public DndClassController(DndClassManager dndClassManager) {
        this.dndClassManager = dndClassManager;
    }

    @GetMapping(value = "/classes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DndClassEntity> getDefaultClassInfo(){
        return dndClassManager.getDndClasses();
    }

    @GetMapping("/classInfo")
    public DndClassDto getSpecificClassInfo(@RequestParam String dndClassName) throws JsonProcessingException {
        return dndClassManager.getSpecificDndClassInfo(dndClassName);
    }

}
