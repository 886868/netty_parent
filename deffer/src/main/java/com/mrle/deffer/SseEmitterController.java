package com.mrle.deffer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController
public class SseEmitterController {
    private SseEmitter sseEmitter = new SseEmitter();

    @RequestMapping("/testSseEmitter")
    public SseEmitter testSseEmitter() {
        return sseEmitter;
    }

    @RequestMapping("/setSseEmitter")
    public String setSseEmitter() {
        try {
            sseEmitter.send(System.currentTimeMillis());
        } catch (IOException e) {
//            logger.error("IOException!", e);
            return "error";
        }

        return "Succeed!";
    }

    @RequestMapping("/completeSseEmitter")
    public String completeSseEmitter() {
        sseEmitter.complete();

        return "Succeed!";
    }
}
