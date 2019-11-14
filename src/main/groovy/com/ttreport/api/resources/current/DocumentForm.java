package com.ttreport.api.resources.current;

import com.ttreport.logs.DevCycleLogger;
import groovy.json.JsonBuilder;

import java.util.Map;

public interface DocumentForm {
    Map<String,Object> getAsMap();
    default String serializeAsJson()
    {
        Map<String,Object> map = this.getAsMap();
        JsonBuilder builder = new JsonBuilder(map);
        DevCycleLogger.log("The document as json, 'prettified':");
        DevCycleLogger.log(builder.toPrettyString());
        return  builder.toString();
    }
}
