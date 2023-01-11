package edu.whu;

import edu.whu.demo.util.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;

@SpringBootApplication
@EnableSwagger2
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
//    public static void main(String[] args) throws IOException {
//        SpringApplication.run(DemoApplication.class, args);
//
//        CSVReader csvreader=new CSVReader();
//        csvreader.csvReadOperation2();
//    }

}
