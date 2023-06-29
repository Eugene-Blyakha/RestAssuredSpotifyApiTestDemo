package com.spotify.oath2.api.applicationApi;

import com.spotify.oath2.pojo.Playlist;
import io.restassured.response.Response;

import static com.spotify.oath2.api.SpecBuilder.getRequestSpec;
import static com.spotify.oath2.api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;

public class PlaylistApi {

    public static String access_token = "BQCyt78gEE3DZtgpbcHXvjyfzksoacs3ea2QgQefJZ7qmV2AQ9bZu92NAdRoVpS-6pZs1y6mOVd0mCxf7AP1IpgLciBUIkcP2Aly-e38rftwb3YVp309uBylNO9pNHc3IsaJDN_WZwn6yCCaqc-nMU8RoxoIsofdlaEFyu4Ge18HrqpYuEeciHwnoGtNbl9BFCcXn0uGj7tZDtSBPmljeGHDccw13diPuIHecZLNfP7-yLCbbbUy-xs6OKdaNUb19QIysu1zuZ4gNHtS";

    public static Response post(Playlist requestPlaylist){
        return given(getRequestSpec()).
                body(requestPlaylist).
                header("Authorization", "Bearer " + access_token).
        when().post("/users/31bcbekz5mtavklq3g3ccqbhyshm/playlists").
        then().spec(getResponseSpec()).
            extract().
            response();
    }

    public static Response post(String invalidToken, Playlist requestPlaylist){
        return given(getRequestSpec()).
                body(requestPlaylist).
                header("Authorization", "Bearer " + invalidToken).
                when().post("/users/31bcbekz5mtavklq3g3ccqbhyshm/playlists").
                then().spec(getResponseSpec()).
                extract().
                response();
    }

    public static Response get(String playlistId){
        return given(getRequestSpec()).
                header("Authorization", "Bearer " + access_token).
        when().get("/playlists/" + playlistId).
        then().spec(getResponseSpec()).
                extract().
                response();
    }

    public static Response update(String playlistId, Playlist requestPlaylist){
        return given(getRequestSpec()).
                body(requestPlaylist).
                header("Authorization", "Bearer " + access_token).
                when().put("/playlists/" + playlistId).
                then().spec(getResponseSpec()).
                extract().
                response();
    }

}
