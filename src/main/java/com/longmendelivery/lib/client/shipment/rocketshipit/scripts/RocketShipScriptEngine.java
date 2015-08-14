package com.longmendelivery.lib.client.shipment.rocketshipit.scripts;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.service.initializer.EnvironmentStage;
import com.longmendelivery.service.initializer.EnvironmentUtil;
import php.java.script.InteractivePhpScriptEngineFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.IOException;

/**
 * Created by desmond on 21/06/15.
 */
public class RocketShipScriptEngine {
    public static String getRocketShipItPath() {
        if (EnvironmentUtil.getStage().equals(EnvironmentStage.DESKTOP)) {
            return "'./src/main/php/php-rocket-shipit/autoload.php'";
        } else {
            return "'/var/lib/tomcat7/webapps/ROOT/WEB-INF/classes/php-rocket-shipit/autoload.php'";
        }
    }

    private ScriptEngine engine;
    private ObjectMapper objectMapper;


    public RocketShipScriptEngine() throws DependentServiceException {
        engine = new InteractivePhpScriptEngineFactory().getScriptEngine();
        objectMapper = new ObjectMapper();
        String rsiLoadResult = this.executeScriptToString("require " + getRocketShipItPath());
    }

    public RocketShipScriptEngine(ScriptEngine engine, ObjectMapper objectMapper) {
        this.engine = engine;
        this.objectMapper = objectMapper;
    }

    public <T> T executeScript(String script, TypeReference<T> valueTypeRef) throws DependentServiceException {
        System.out.println(script);
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

    public String executeScriptToString(String script) throws DependentServiceException {
        try {
            String value = (String) engine.eval(script);
            return value;
        } catch (ScriptException e) {
            e.printStackTrace();
            throw new DependentServiceException(e);
        }
    }
}
