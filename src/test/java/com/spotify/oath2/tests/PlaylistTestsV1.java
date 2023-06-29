package com.spotify.oath2.tests;

import com.spotify.oath2.pojo.Playlist;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static com.spotify.oath2.api.SpecBuilder.getRequestSpec;
import static com.spotify.oath2.api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistTestsV1 {

    @Test
    public void shouldBeAbleToCreateAPlaylist(){
        Playlist requestPlaylist = new Playlist();
        requestPlaylist.setName("New Playlist Six");
        requestPlaylist.setDescription("New Playlist Description Six");
        requestPlaylist.set_public(false);

        Playlist responsePlaylist = given(getRequestSpec()).
                body(requestPlaylist).
        when().post("/users/31bcbekz5mtavklq3g3ccqbhyshm/playlists").
        then().spec(getResponseSpec()).
                assertThat().
                statusCode(201).
                extract().
                response().
                as(Playlist.class);

        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));

    }

    @Test
    public void shouldBeAbleToGetAPlaylist(){

        Playlist requestPlaylist = new Playlist();
        requestPlaylist.setName("New Playlist Six");
        requestPlaylist.setDescription("New Playlist Description Six");
        requestPlaylist.set_public(false);

        Playlist responsePlaylist = given(getRequestSpec()).
        when().get("/playlists/1gKdlpwcNiFELMktQrnk5V").
        then().spec(getResponseSpec()).
                assertThat().
                statusCode(200).
                extract().
                response().
                as(Playlist.class);

        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));
    }

    @Test
    public void shouldBeAbleToUpdateAPlaylist(){
        Playlist requestPlaylist = new Playlist().
                setName("New Playlist Six").
                setDescription("New Playlist Description Six").
                set_public(false);

        given(getRequestSpec()).
                body(requestPlaylist).
        when().put("/playlists/1gKdlpwcNiFELMktQrnk5V").
        then().spec(getResponseSpec()).
                assertThat().
                statusCode(200);
    }

    @Test
    public void shouldNotBeAbleToCreateAPlaylistWithoutName() {
        String payload = "{\n" +
                "  \"name\": \"\",\n" +
                "  \"description\": \"New playlist description One\",\n" +
                "  \"public\": false\n" +
                "}";
        given(getRequestSpec()).
                body(payload).
                when().post("/users/31bcbekz5mtavklq3g3ccqbhyshm/playlists").
                then().spec(getResponseSpec()).
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
                then().spec(getResponseSpec()).
                assertThat().
                statusCode(401).
                body("error.status", equalTo(401),
                        "error.message", equalTo("Invalid access token"));
    }
}
