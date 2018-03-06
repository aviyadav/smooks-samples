package sample.camel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("myBean")
public class SampleBean {

    @Value("${greeting}")
    private String say;

    public String saySomething() {
        return say;
    }
}
