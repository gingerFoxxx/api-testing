package trainingxyz;

import models.Product;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.proxy;

public class ChallengeFirst {
    //Create a new product called, Sweatband in Category 3 for 5 US dollars
    @Test
    public void createProduct(){
        String endpoint = "http://localhost:8888/API_testing/product/create.php";;
        Product product = new Product(
                "Sweatband",
                "New pink sweatband for the 5$",
                5,
                3);
        var response =
                given().
                        body(product).
                when().
                        post(endpoint).
                then();
        response.log().body();
    }
    // Then update the Sweatband's price to be 6 US dollars.
    @Test
    public void updateProduct(){
        String endpoint = "http://localhost:8888/API_testing/product/update.php";;
        /*Product product = new Product(
                21,
                "Sweatband",
                "New pink sweatband for the 6$",
                6,
                3);*/
        String body = """
        {
        "id":22,
        "price": 6
        }""";
        var response =
                given().
                        body(body).
                        when().
                        put(endpoint).
                        then();
        response.log().body();
    }

    //Then retrieve information about the Sweatband
    @Test
    public void getProduct(){
        String endpoint = "http://localhost:8888/API_testing/product/read.php";
        var response =
                given().
                        queryParam("id",22).
                when().
                        get(endpoint).
                then();
        response.log().body();
    }

    // Delete the Sweatband product.
    @Test
    public void deleteProduct(){
        String endpoint = "http://localhost:8888/API_testing/product/delete.php";;
        Product product = new Product(21);
        var response =
                given().
                        body(product).
                when().
                        delete(endpoint).
                then();
        response.log().body();
    }

}
