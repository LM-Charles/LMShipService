package com.longmendelivery.lib.client.shipment.rocketshipit.engine;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.service.initializer.EnvironmentUtil;
import php.java.script.InteractivePhpScriptEngine;
import php.java.script.InteractivePhpScriptEngineFactory;

import javax.script.ScriptException;
import java.io.IOException;

/**
 * This engine must be released explicitly by calling release().
 *
 * Created by rabiddesire on 21/06/15.
 */
public class RSIScriptEngine {
    private static String getRocketShipItPath() {
        return EnvironmentUtil.getApplicationPHPRoot();
    }

    private InteractivePhpScriptEngine engine;
    private ObjectMapper objectMapper;


    public RSIScriptEngine() throws DependentServiceException {
        engine = (InteractivePhpScriptEngine) new InteractivePhpScriptEngineFactory().getScriptEngine();
        objectMapper = new ObjectMapper();
        this.executeScriptToString("require '" + getRocketShipItPath() + "'");
    }


    public synchronized <T> T executeScript(String script, TypeReference<T> valueTypeRef) throws DependentServiceException {
        try {
            String value = (String) engine.eval(script);
            System.out.println(value);

            T response = objectMapper.readValue(value, valueTypeRef);
            return response;
        } catch (ScriptException e) {
            throw new DependentServiceException(e);
        } catch (JsonMappingException e) {
            throw new DependentServiceException(e);
        } catch (JsonParseException e) {
            throw new DependentServiceException(e);
        } catch (IOException e) {
            throw new DependentServiceException(e);
        }
    }

    public synchronized JsonNode executeScriptToTree(String script) throws DependentServiceException {
        try {
            String value = (String) engine.eval(script);
            System.out.println(value);

            JsonNode response = objectMapper.readTree(value);
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

    public synchronized String executeScriptToString(String script) throws DependentServiceException {
        try {
            String value = (String) engine.eval(script);
            System.out.println(value);

            return value;
        } catch (ScriptException e) {
            e.printStackTrace();
            throw new DependentServiceException(e);
        }
    }

    public void release() {
        engine.release();
    }
}
