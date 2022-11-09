package trainingxyz;


import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ChallengeTwo {
    // Make API request to get multivitamin product
    // Product ID = 18
    //Verify Status code, Content-type header, Each Fields ot the body

    @Test
    public void getProduct(){
        String endpoint = "http://localhost:8888/api_testing/product/read_one.php";
        given().
                queryParam("id", 18).
        when().
                get(endpoint).
        then().
            assertThat().
                statusCode(200).
                header("Content-Type", equalTo("application/json")).
                body("id", equalTo("18")).
                body("name", equalTo("Multi-Vitamin (90 capsules)")).
                body("description", equalTo("A daily dose of our Multi-Vitamins fulfills a day’s nutritional needs for over 12 vitamins and minerals.")).
                body("price", equalTo("10.00")).
                body("category_id", equalTo("4")).
                body("category_name", equalTo("Supplements"));
    }

    }
