package com.longmendelivery.lib.client.shipment.rocketshipit;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import php.java.bridge.*;
import php.java.script.*;
import php.java.servlet.*;

/**
 * Created by desmond on 21/06/15.
 */
public class RocketShipItJavaRunner {
    public void runLibrary(){
        String code="echo 5+5;"; //sample bit of code
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByExtension("php");
        try {
            engine.eval(code);
        } catch (ScriptException ex) {
            //catch statement
        }
    }
}
