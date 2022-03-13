

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/test")
public class ExController {
    private static final Logger logger = LoggerFactory.getLogger(ExController.class);
    private final ExService service;


    @GetMapping(value = {"", "/"})
    public ResponseEntity<?> list(@ModelAttribute PaggingVo paggingVo, @ModelAttribute ExDto dto) {
        logger.info("[list] Start");

        Map<String, Object> responseMap = new HashMap<>();
        List<ExDto> resultList = service.list(paggingVo, dto);

        logger.info("[list] end");

        responseMap.put("list", resultList);
        responseMap.put("pagging", paggingVo);

        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }


    @GetMapping(value = {"/{pk}"})
    public ResponseEntity<?> view(@PathVariable String pk) {
        logger.info("[list] Start");

        ExDto resultView = service.view(pk);


        return new ResponseEntity<>(resultView, HttpStatus.OK);
    }


    @PostMapping(value = {"/{pk}"})
    public ResponseEntity<?> insert(@PathVariable String pk, @ModelAttribute ExDto dto) {
        logger.info("[list] Start");

        service.insert(dto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping(value = {"/{pk}"})
    public ResponseEntity<?> update(@PathVariable String pk, @ModelAttribute ExDto dto) {
        logger.info("[list] Start");

        service.update(pk, dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = {"/{pk}"})
    public ResponseEntity<?> delete(@PathVariable String pk) {
        logger.info("[list] Start");

        service.delete(pk);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
