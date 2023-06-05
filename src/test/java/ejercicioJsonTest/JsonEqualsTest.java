package ejercicioJsonTest;

import ejercicioJson.JsonEquals;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class JsonEqualsTest {

    private JsonEquals jsonEquals;

    @BeforeEach
    public void setup() {
        jsonEquals = new JsonEquals();
    }

    @ParameterizedTest
    @CsvSource(value = {

            "\"\";{\"path\"\"JSON\"}; false",
            "{\"path\"\"JSON\"};\"\"; false",

            "{\"path\":\"JSON\"};\"path\":\"JSON\"}; false",
            "\"path\":\"JSON\"};{\"path\":\"JSON\"}; false",
            "{\"path\"\"JSON\"};{\"path\":\"JSON\"}; false",
            "{\"path\":\"JSON\"};{\"path\"\"JSON\"}; false",
            "{\"path\":\"JSON\"\"date\":\"IGNORE\"};{\"path\":\"JSON\",\"date\":\"15-04-2021\"}; false",
            "{\"path\":\"JSON\",\"date\":\"IGNORE\"};{\"path\":\"JSON\"\"date\":\"15-04-2021\"}; false",



            "{\"path\":\"JSON\"};{\"path\":\"JSON\"}; true",
            "{\"path\":\"JSON\"};{\"path\":\"67\"}; false",
            "{\"path\":\"JSON\"};{\"holaBuenas\":\"JSON\"}; false",
            "{\"path\":\"JSON\"};{\"holaBuenas\":\"ufa\"}; false",
            "{\"id\":5};{\"id\":3}; false",
            "{\"id\":5};{\"id\":5}; true",
            "{\"isGreater\":true};{\"isGreater\":false}; false",
            "{\"isGreater\":true};{\"isGreater\":true}; true",

            "{\"path\":\"JSON\",\"date\":\"IGNORE\"};{\"path\":\"JSON\",\"date\":\"15-04-2021\"}; true",
            "{\"path\":\"JSON\",\"date\":\"23-04-2020\"};{\"path\":\"JSON\",\"date\":\"15-04-2021\"}; false",

            "{\"path\":\"JSON\",\"date\":\"IGNORE\", \"embedded\":{\"id\":1}};{\"path\":\"JSON\",\"date\":\"15-04-2021\", \"embedded\":{\"id\":1}}; true",
            "{\"path\":\"JSON\",\"date\":\"IGNORE\", \"embedded\":{\"id\":1}};{\"path\":\"JSON\",\"date\":\"15-04-2021\", \"embedded\":{\"id\":false}}; false",
            "{\"path\":\"JSON\",\"date\":\"IGNORE\", \"embedded\":{\"id\":1, \"date\":\"IGNORE\"}};{\"path\":\"JSON\",\"date\":\"15-04-2021\", \"embedded\":{\"id\":1, \"date\":\"15-09-2023\"}}; true",
            "{\"path\":\"JSON\",\"date\":\"IGNORE\", \"embedded\":{\"id\":1, \"date\":\"23-05-2014\"}};{\"path\":\"JSON\",\"date\":\"15-04-2021\", \"embedded\":{\"id\":1, \"date\":\"15-09-2023\"}}; false",

    }, delimiter = ';')
    public void verifyEqualJson(String expectedJSON, String actualJSON, boolean expectedResult) {
        boolean actualResult = jsonEquals.equalJSON(expectedJSON, actualJSON);
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void verifyEqualFormattedJson() {
        boolean expectedResult = true;
        boolean actualResult = jsonEquals.equalJSON("{\n" +
                "  \"path\": \"JSON\",\n" +
                "  \"date\": \"IGNORE\",\n" +
                "  \"embedded\": {\n" +
                "    \"id\": 1\n" +
                "  }\n" +
                "}", "{\"path\":\"JSON\",\"date\":\"23-06-2019\", \"embedded\":{\"id\":1}}");
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void verifyEmptyJson() {
        boolean expectedResult = true;
        boolean actualResult = jsonEquals.equalJSON("{}", "{}");
        Assertions.assertEquals(expectedResult, actualResult);
    }

}
