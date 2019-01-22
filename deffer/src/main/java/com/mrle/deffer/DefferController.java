package com.mrle.deffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class DefferController {

    private final static Logger LOGGER = LoggerFactory.getLogger(DefferController.class);

    private DeferredResult<String> deferredResult = new DeferredResult<>();

    @RequestMapping("/testDeferr")
    public DeferredResult<String> testDeferr() {
        System.out.println(deferredResult.getResult());
        return deferredResult;
    }

    @RequestMapping("/setDeferr")
    public String setDeferr() {

        deferredResult.setResult("Test result");

        return "success";
    }

}
