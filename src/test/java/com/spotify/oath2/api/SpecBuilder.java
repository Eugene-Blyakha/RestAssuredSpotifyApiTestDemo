package com.spotify.oath2.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilder {

    public static RequestSpecification getRequestSpec(){
        return new RequestSpecBuilder().
                setBaseUri("https://api.spotify.com/").
                setBasePath("/v1").
                setContentType(ContentType.JSON).
                log(LogDetail.ALL).build();
    }

    public static ResponseSpecification getResponseSpec(){
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                log(LogDetail.ALL);

        ResponseSpecification responseSpecification = responseSpecBuilder.build();

        return responseSpecification;
    }


}
