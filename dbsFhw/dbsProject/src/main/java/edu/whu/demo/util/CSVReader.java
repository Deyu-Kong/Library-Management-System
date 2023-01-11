package edu.whu.demo.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import de.siegmar.fastcsv.reader.CsvContainer;
import de.siegmar.fastcsv.reader.CsvParser;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "上传csv")
@RestController
@RequestMapping("importcsv")
public class CSVReader {
    public static void csvReadOperation2() throws IOException {
        File file = new File("C:\\Users\\asus\\Desktop\\books.csv");
        CsvReader csvReader = new CsvReader();

        try (CsvParser csvParser = csvReader.parse(file, StandardCharsets.UTF_8)) {
            CsvRow row;
            while ((row = csvParser.nextRow()) != null) {
//                System.out.println("Read line: " + row);
//                System.out.println("First column of line: " + row.getField(0));
            }
        }
    }

}
