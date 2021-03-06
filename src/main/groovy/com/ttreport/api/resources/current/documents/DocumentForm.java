package com.ttreport.api.resources.current.documents;

import com.ttreport.logs.ServerLogger;
import groovy.json.JsonBuilder;

import java.util.Map;

public interface DocumentForm {
    Map<String,Object> getAsMap();
    default String serializeAsJson()
    {
        Map<String,Object> map = this.getAsMap();
        JsonBuilder builder = new JsonBuilder(map);
        ServerLogger.log("The document as json, 'prettified':", builder.toPrettyString());
        return  builder.toString();
    }
}
