package com.spotify.oath2.tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistTestsOriginal {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    String access_token = "BQC3Ceh2ev-G9mLYJgtJoecKYHzK-eTg9PQhuC18GbtRNpsn9nJsbn-IHbP3u-J1wUTAi50YJXPyqaP5M-6_lKtjCxE6zcl1Xs2zfjcR3FcGMFzycOACKUI73j5birYeP1l3PSuvFSukTBfnXvb0Wo719ahH_R-HGRsctZk9SYRYfmLDTEULhThrj7wOxm7-esbybtSNrQtg5lNKoDfrDqEnQ6_ZsOssv63OdMwE43UNLY9fxrR0d4OH36ougT5I5_0nhg4GHbuZOFHo";


    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://api.spotify.com/").
                setBasePath("/v1").
                addHeader("Authorization", "Bearer "+access_token).
                setContentType(ContentType.JSON).
                log(LogDetail.ALL);

        requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                log(LogDetail.ALL);

        responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void shouldBeAbleToCreateAPlaylist(){

        String payload = "{\n" +
                "  \"name\": \"New Playlist One\",\n" +
                "  \"description\": \"New playlist description\",\n" +
                "  \"public\": false\n" +
                "}";
        given(requestSpecification).
                body(payload).
        when().post("/users/31bcbekz5mtavklq3g3ccqbhyshm/playlists").
        then().spec(responseSpecification).
                assertThat().
                statusCode(201).
                body("name",equalTo("New Playlist One"),
                    "description", equalTo("New playlist description"),
                    "public", equalTo(false));
    }

    @Test
    public void shouldBeAbleToGetAPlaylist(){

        given(requestSpecification).
        when().get("/playlists/7L41WvVslkHuZ7w4wEfldC").
        then().spec(responseSpecification).
                assertThat().
                statusCode(200).
                body("name",equalTo("Playlist r r_h0 0_z"),
                        "description", equalTo("Description D&amp;&amp;s&amp;+F__#&amp;_Isg+A s+&amp;++y6R&amp;-_0#  e+3+_HO+2PN#R # +"),
                        "public", equalTo(false));
    }

    @Test
    public void shouldBeAbleToUpdateAPlaylist(){
        String payload = "{\n" +
                "  \"name\": \"Updated Playlist Name Five\",\n" +
                "  \"description\": \"Updated playlist description Five\",\n" +
                "  \"public\": true\n" +
                "}";
        given(requestSpecification).
                body(payload).
        when().put("/playlists/3RKimBoLNFwtSuF7KkOQ0j").
        then().spec(responseSpecification).
                assertThat().
                statusCode(200);
    }

    @Test
    public void shouldBeAbleToCreateAPlaylistWithoutName() {
        String payload = "{\n" +
                "  \"name\": \"\",\n" +
                "  \"description\": \"New playlist description One\",\n" +
                "  \"public\": false\n" +
                "}";
        given(requestSpecification).
                body(payload).
                when().post("/users/31bcbekz5mtavklq3g3ccqbhyshm/playlists").
                then().spec(responseSpecification).
                assertThat().
                statusCode(400).
                body("error.status", equalTo(400),
                        "error.message", equalTo("Missing required field: name"));
    }

    @Test
    public void shouldBeAbleToCreateAPlaylistWithExpiredToken() {
        String payload = "{\n" +
                "  \"name\": \"New Playlist\",\n" +
                "  \"description\": \"New playlist description One\",\n" +
                "  \"public\": false\n" +
                "}";
        given().
                baseUri("https://api.spotify.com/").
                basePath("/v1").
                header("Authorization", "Bearer " + "expired_token").
                contentType(ContentType.JSON).
                log().all().
                body(payload).
                when().post("/users/31bcbekz5mtavklq3g3ccqbhyshm/playlists").
                then().spec(responseSpecification).
                assertThat().
                statusCode(401).
                body("error.status", equalTo(401),
                        "error.message", equalTo("Invalid access token"));
    }
}
