package com.spotify.oath2.tests;

import com.spotify.oath2.api.applicationApi.PlaylistApi;

import com.spotify.oath2.pojo.Error;
import com.spotify.oath2.pojo.Playlist;
import io.restassured.response.Response;
import org.testng.annotations.Test;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistTests {

    @Test
    public void shouldBeAbleToCreateAPlaylist(){
        Playlist requestPlaylist = new Playlist();
        requestPlaylist.setName("New Playlist Six");
        requestPlaylist.setDescription("New Playlist Description Six");
        requestPlaylist.set_public(false);

        Response response = PlaylistApi.post(requestPlaylist);
        assertThat(response.statusCode(), equalTo(201));

        //this will deserialize Json object to java object of Playlist type
        Playlist responsePlaylist = response.as(Playlist.class);

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

        Response response = PlaylistApi.get("1gKdlpwcNiFELMktQrnk5V");
        assertThat(response.statusCode(), equalTo(200));

        //this will deserialize Json object to java object of Playlist type
        Playlist responsePlaylist = response.as(Playlist.class);

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

        Response response = PlaylistApi.update("5HSgNqJBep8uTEMNCG0OFA", requestPlaylist);

        assertThat(response.statusCode(), equalTo(200));
    }

    @Test
    public void shouldNotBeAbleToCreateAPlaylistWithoutName() {
        Playlist requestPlaylist = new Playlist().
                setName("").
                setDescription("New Playlist Description").
                set_public(false);

        Response response = PlaylistApi.post(requestPlaylist);
        assertThat(response.statusCode(), equalTo(400));

        Error error = response.as(Error.class);

        assertThat(error.getError().getStatus(), equalTo(400));
        assertThat(error.getError().getMessage(), equalTo("Missing required field: name"));

    }

    @Test
    public void shouldBeAbleToCreateAPlaylistWithExpiredToken() {

        String invalid_token = "12345";

        Playlist requestPlaylist = new Playlist().
                setName("New Playlist Six").
                setDescription("New Playlist Description Six").
                set_public(false);

        Response response = PlaylistApi.post(invalid_token, requestPlaylist);
        assertThat(response.statusCode(), equalTo(401));

        Error error = response.as(Error.class);

        assertThat(error.getError().getStatus(), equalTo(401));
        assertThat(error.getError().getMessage(), equalTo("Invalid access token"));

    }
}
