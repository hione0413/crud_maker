package org.hionesoft.crudmaker.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/test")
@Slf4j
public class ExController {
    private final ExService service;


    @GetMapping(value = {"", "/"})
    public ResponseEntity<?> list(@ModelAttribute PaggingInfo PaggingInfo, @ModelAttribute SearchInfo searchInfo, @ModelAttribute ExDto dto) {
        List<ExDto> resultList = service.list(PaggingInfo, searchInfo, dto);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("list", resultList);
        responseMap.put("PaggingInfo", PaggingInfo);

        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }


    @GetMapping(value = {"/{pk}"})
    public ResponseEntity<?> view(@PathVariable String pk) {
        ExDto resultView = service.view(pk);
        return new ResponseEntity<>(resultView, HttpStatus.OK);
    }


    @PostMapping(value = {"/"})
    public ResponseEntity<?> insert(@ModelAttribute ExDto dto) {
        service.insert(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping(value = {"/{pk}"})
    public ResponseEntity<?> update(@PathVariable String pk, @ModelAttribute ExDto dto) {
        service.update(pk, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping(value = {"/{pk}"})
    public ResponseEntity<?> delete(@PathVariable String pk) {
        service.delete(pk);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
