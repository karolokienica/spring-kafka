package app.controller;

import app.common.Foo1;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private final KafkaTemplate<Object, Object> template;

    public Controller(KafkaTemplate<Object, Object> template) {
        this.template = template;
    }

    @PostMapping("/send/foo/{what}")
    public ResponseEntity<String> post(@PathVariable String what) {
        Foo1 foo = new Foo1(what);
        this.template.send("topic1", foo);
        return ResponseEntity.status(200).body(foo.foo());
    }
}
