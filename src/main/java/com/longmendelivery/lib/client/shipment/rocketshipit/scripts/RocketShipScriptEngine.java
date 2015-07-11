package com.longmendelivery.lib.client.shipment.rocketshipit.scripts;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import php.java.script.InteractivePhpScriptEngineFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.IOException;

/**
 * Created by desmond on 21/06/15.
 */
public class RocketShipScriptEngine {
    private ScriptEngine engine;
    private ObjectMapper objectMapper;

    public RocketShipScriptEngine() {
        engine = new InteractivePhpScriptEngineFactory().getScriptEngine();
        objectMapper = new ObjectMapper();
    }

    public RocketShipScriptEngine(ScriptEngine engine, ObjectMapper objectMapper) {
        this.engine = engine;
        this.objectMapper = objectMapper;
    }

    public <T> T executeScript(String script, TypeReference<T> valueTypeRef) throws DependentServiceException {
        try {
            String value = (String) engine.eval(script);
            T response = objectMapper.readValue(value, valueTypeRef);
            return response;
        } catch (ScriptException e) {
            e.printStackTrace();
            throw new DependentServiceException(e);
        } catch (JsonMappingException e) {
            e.printStackTrace();
            throw new DependentServiceException(e);
        } catch (JsonParseException e) {
            e.printStackTrace();
            throw new DependentServiceException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new DependentServiceException(e);
        }
    }
}
