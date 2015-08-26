package com.longmendelivery.lib.client.shipment.rocketshipit.engine;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.service.initializer.EnvironmentStage;
import com.longmendelivery.service.initializer.EnvironmentUtil;
import php.java.script.InteractivePhpScriptEngineFactory;

import javax.script.ScriptException;
import java.io.IOException;

/**
 * Created by  rabiddesireon 21/06/15.
 */
public class RSIScriptEngine {
    public static String getRocketShipItPath() {
        if (EnvironmentUtil.getStage().equals(EnvironmentStage.DESKTOP)) {
            return "'./src/main/php/php-rocket-shipit/autoload.php'";
        } else {
            return "'/var/lib/tomcat7/webapps/ROOT/WEB-INF/classes/php-rocket-shipit/autoload.php'";
        }
    }

    private javax.script.ScriptEngine engine;
    private ObjectMapper objectMapper;


    public RSIScriptEngine() throws DependentServiceException {
        engine = new InteractivePhpScriptEngineFactory().getScriptEngine();
        objectMapper = new ObjectMapper();
        this.executeScriptToString("require " + getRocketShipItPath());
    }

    public <T> T executeScript(String script, TypeReference<T> valueTypeRef) throws DependentServiceException {
        try {
            String value = (String) engine.eval(script);
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

    public JsonNode executeScriptToTree(String script) throws DependentServiceException {
        try {
            String value = (String) engine.eval(script);
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
