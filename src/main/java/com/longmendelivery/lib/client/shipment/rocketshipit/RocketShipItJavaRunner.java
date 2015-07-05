package com.longmendelivery.lib.client.shipment.rocketshipit;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.longmendelivery.lib.client.shipment.rocketshipit.model.UPSRateResponseEntry;
import php.java.script.InteractivePhpScriptEngineFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.IOException;
import java.util.List;

/**
 * Created by  rabiddesireon 21/06/15.
 */
public class RocketShipItJavaRunner {
    public List<UPSRateResponseEntry> runLibrary(String script) throws ScriptException {
        ScriptEngine engine = new InteractivePhpScriptEngineFactory().getScriptEngine();
        try {
            String value = (String) engine.eval(script);
            List<UPSRateResponseEntry> response = new ObjectMapper().readValue(value, new TypeReference<List<UPSRateResponseEntry>>() {
            });
            return response;
        } catch (ScriptException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
