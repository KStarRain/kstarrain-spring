package com.kstarrain.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: DongYu
 * @create: 2019-10-21 09:06
 * @description:
 */
@Slf4j
@Service
public class DataImportService {

    @Autowired
    IStudentService studentService;


    public void dataImport() {

        for (int i = 1; i <= 3; i++) {
            try {
                studentService.propagation_annotated2(i);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

    }
}
