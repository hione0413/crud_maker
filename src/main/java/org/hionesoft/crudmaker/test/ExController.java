package org.hionesoft.crudmaker.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> list(@ModelAttribute PaggingInfo paggingInfo, @ModelAttribute SearchInfo searchInfo, @ModelAttribute ExDto dto) {

        int totalCnt = service.listCnt(searchInfo, dto);
        paggingInfo.setTotalRecordCount(totalCnt);

        List<ExDto> resultList = service.list(paggingInfo, searchInfo, dto);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("list", resultList);
        responseMap.put("PaggingInfo", paggingInfo);

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
