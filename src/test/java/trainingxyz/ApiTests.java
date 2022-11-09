package trainingxyz;
import groovy.transform.ASTTest;
import models.Product;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ApiTests {
    @Test
    public void getCategories(){
        String endpoint = "http://localhost:8888/API_testing/category/read.php";
        var response =
                given().
                when().
                        get(endpoint).
                then();
        response.log().body();
    }

    @Test
    public void getProduct(){
        String endpoint = "http://localhost:8888/api_testing/product/read_one.php";
                given().
                        queryParam("id", 2).
                when().
                        get(endpoint).
                then().
                        assertThat().
                            statusCode(200).
                            body("id", equalTo("2")).
                            body("name", equalTo("Cross-Back Training Tank")).
                            body("description", equalTo("The most awesome phone of 2013!")).
                            body("price", equalTo("299.00")).
                            body("category_id", equalTo("2")).
                            body("category_name", equalTo("Active Wear - Women"));
    }
    @Test
    public void getProductAll() {
        String endpoint = "http://localhost:8888/API_testing/product/read.php";
        //for (int i = 0; i < 22; i++) {
            //String recordsId = "records.id["+ i++ + "]";
        given().
        when().
                get(endpoint).
        then().//log().headers().
                assertThat().
                    statusCode(200).
                    header("Content-Type", equalTo("application/json; charset=UTF-8")).
                    body("records.size()", greaterThan(0)). // there may be more than one object returned to us.we are getting a list of all of the ID values.
                    body("records.id", everyItem(notNullValue())). //assert that all Ids are not null value
                    body("records.name", everyItem(notNullValue())).
                    body("records.description", everyItem(notNullValue())).
                    body("records.price", everyItem(notNullValue())).
                    body("records.category_id", everyItem(notNullValue())).
                    body("records.category_name",everyItem(notNullValue())).
                    body("records.id[0]", equalTo(22)); //(recordsId, equalTo(i+1))
        //}
    }

    @Test
    public void createProduct(){
        String endpoint = "http://localhost:8888/API_testing/product/create.php";
        String body = """
                {
                "name":"New bottle",
                "description":"New bottle. Holds 64 ounces.",
                "price": 22,
                "category_id":3
                }""";
        var response = given().body(body).when().post(endpoint).then();
        response.log().body();

    }

    @Test
    public void updateProduct(){
    String endpoint = "http://localhost:8888/API_testing/product/update.php";
    String body = """
            {
            "id":20,
            "name":"New bottle",
            "description":"New bottle. Holds 64 ounces.",
            "price": 15,
            "category_id":3
            }""";
    var response =
            given().
                body(body).
            when().put(endpoint).
            then();
    response.log().body();
    }

    @Test
    public void deleteProduct(){
        String endpoint = "http://localhost:8888/API_testing/product/delete.php";
        String body = """
                {
                "id":"19"
                }""";
        var responce =
                given().
                        body(body).
                when().
                        delete(endpoint).
                then();
        responce.log().body();
    }

    @Test
    public void createSerializedProduct(){
        String endpoint = "http://localhost:8888/API_testing/product/create.php";
        Product product = new Product(
                "Water bottle 2",
                "Water bottle description",
                12.50,
                3);
        var response =
                given().
                        body(product).
                when().
                        post(endpoint).
                then();
        response.log().body();
    }

    @Test
    public void getDesirializedProduct(){
        String endpoint = "http://localhost:8888/API_testing/product/read_one.php";
        // "id": "2", "name": "Cross-Back Training Tank", "description": "The most awesome phone of 2013!", "price": "299.00", "category_id": "2", "category_name": "Active Wear - Women"
        Product expectedProduct = new Product(
                2,
                "Cross-Back Training Tank",
                "The most awesome phone of 2013!",
                299.00,
                2,
                "Active Wear - Women"
        );
        Product actualProduct =
                given().
                    queryParam("id","2").
                when().
                    get(endpoint).as(Product.class);
        assertThat(actualProduct, samePropertyValuesAs(expectedProduct));
    }
}
