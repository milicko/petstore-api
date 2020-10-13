import common.Base;
import constants.RequestConstants;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import models.dogModel.DogCreationCategory;
import models.dogModel.DogCreationModel;
import models.dogModel.DogCreationTags;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class T001createPetTest extends Base {

    static int id = createRandomNumber(100);
    static String categoryName = createRandomString(5);
    static String status = createRandomString(10);
    static String name = createRandomString(10);
    static String categoryNameUpdated = createRandomString(5);
    static String statusUpdated = createRandomString(10);
    static String nameUpdated = createRandomString(10);

    DogCreationCategory category = new DogCreationCategory(id, categoryName);
    List<String> photoUrls = new ArrayList<>();
    List<DogCreationTags> tags = new ArrayList<>();
    DogCreationCategory categoryUpdated = new DogCreationCategory(id, categoryNameUpdated);
    List<String> photoUrlsUpdated = new ArrayList<>();
    List<DogCreationTags> tagsUpdated = new ArrayList<>();

    private static ResponseSpecification responseSpec200;
    private static ResponseSpecification responseSpec404;

    private static RequestSpecification requestSpec;

    @BeforeClass
    public static void createRequestSpecification() {
        requestSpec = new RequestSpecBuilder().addHeader("Authorization", "api-key=special-key").setContentType(ContentType.JSON).build();
    }

    @BeforeClass
    public static void createResponseSpecification() {
        responseSpec200 = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        responseSpec404 = new ResponseSpecBuilder().expectStatusCode(404).expectContentType(ContentType.JSON).build();

    }


    @Test
    public void t001_createPet() {


        photoUrls.add("test abc");
        photoUrls.add("test 123");
        tags.add(new DogCreationTags(id, "test tag1"));
        tags.add(new DogCreationTags(id, "test tag2"));

        DogCreationModel b = new DogCreationModel(id, category, name, photoUrls, tags, status);


        RestAssured.given().spec(requestSpec)
                .when().body(b)
                .post(RequestConstants.V2_PET)
                .then().spec(responseSpec200);


    }

    @Test
    public void t002_verifyPetCreated() {
        category = new DogCreationCategory(id, categoryName);

        DogCreationModel a = RestAssured.given().spec(requestSpec)
                .when().get(RequestConstants.V2_PET_ID(id))
                .as(DogCreationModel.class);
        Assert.assertEquals("ID doesn't match!", id, a.getId());
        Assert.assertEquals("Category ID doesn't match!", category.getId(), a.getCategory().getId());
        Assert.assertEquals("Category name doesn't match!", category.getName(), a.getCategory().getName());
        Assert.assertEquals("Name doesn't match!", name, a.getName());
        for (int i = 0; i < photoUrls.size(); i++) {
            Assert.assertEquals(String.format("Photo url for index %s doesn't match!", i), photoUrls.get(i), a.getPhotoUrls().get(i));
        }

        for (int i = 0; i < tags.size(); i++) {
            Assert.assertEquals(String.format("Tag ID for index %s doesn't match!", i), tags.get(i).getId(), a.getTags().get(i).getId());
            Assert.assertEquals(String.format("Tag Name for index %s doesn't match!", i), tags.get(i).getName(), a.getTags().get(i).getName());

        }


    }

    @Test
    public void t003_updatePet() {

        photoUrlsUpdated.add("test abc");
        photoUrlsUpdated.add("test 123");
        tagsUpdated.add(new DogCreationTags(id, "test tag1"));
        tagsUpdated.add(new DogCreationTags(id, "test tag2"));

        DogCreationModel b = new DogCreationModel(id, categoryUpdated, nameUpdated, photoUrlsUpdated, tagsUpdated, statusUpdated);

        RestAssured.given().spec(requestSpec)
                .when()
                .body(b)
                .put(RequestConstants.V2_PET)
                .as(DogCreationModel.class);
    }

    @Test
    public void t004_verifyPetUpdated() {
        DogCreationModel a = RestAssured.given().spec(requestSpec)
                .when()
                .get(RequestConstants.V2_PET_ID(id))
                .as(DogCreationModel.class);

        Assert.assertEquals("ID doesn't match!", id, a.getId());
        Assert.assertEquals("Category ID doesn't match!", categoryUpdated.getId(), a.getCategory().getId());
        Assert.assertEquals("Category name doesn't match!", categoryUpdated.getName(), a.getCategory().getName());
        Assert.assertEquals("Name doesn't match!", nameUpdated, a.getName());
        for (int i = 0; i < photoUrlsUpdated.size(); i++) {
            Assert.assertEquals(String.format("Photo url for index %s doesn't match!", i), photoUrlsUpdated.get(i), a.getPhotoUrls().get(i));
        }

        for (int i = 0; i < tagsUpdated.size(); i++) {
            Assert.assertEquals(String.format("Tag ID for index %s doesn't match!", i), tagsUpdated.get(i).getId(), a.getTags().get(i).getId());
            Assert.assertEquals(String.format("Tag Name for index %s doesn't match!", i), tagsUpdated.get(i).getName(), a.getTags().get(i).getName());

        }


    }

    @Test
    public void t005_deletePet() {
        RestAssured.given().spec(requestSpec)
                .when()
                .delete(RequestConstants.V2_PET_ID(id)).then().spec(responseSpec200).assertThat()
                .body("message", equalTo(Integer.toString(id)));

    }

    @Test
    public void t006_verifyPetDeleted() {

        RestAssured.given().spec(requestSpec)
                .when()
                .get(RequestConstants.V2_PET_ID(id)).then().spec(responseSpec404).assertThat()
                .body("message", equalTo("Pet not found"));

    }


}
