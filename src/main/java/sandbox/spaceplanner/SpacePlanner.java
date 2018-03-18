package sandbox.spaceplanner;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import swingutils.spring.application.SwingApplication;
import swingutils.spring.application.SwingApplicationBootstrap;

import java.io.IOException;

@SpringBootApplication
@SwingApplication
public class SpacePlanner {

    public static void main(String[] args) throws IOException {
        SwingApplicationBootstrap.beforeSpring("/img/wood.png");
        new SpringApplicationBuilder(SpacePlanner.class).headless(false).run(args);
    }
}
