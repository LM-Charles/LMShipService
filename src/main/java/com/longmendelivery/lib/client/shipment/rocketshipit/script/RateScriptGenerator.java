package com.longmendelivery.lib.client.shipment.rocketshipit.script;

import com.longmendelivery.lib.client.shipment.rocketshipit.model.CourierType;
import com.longmendelivery.service.model.AddressModel;
import com.longmendelivery.service.model.PackageDimensionModel;
import org.apache.commons.lang.NotImplementedException;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by desmond on 05/07/15.
 */
public class RateScriptGenerator {
    public static final EnumSet<CourierType> SUPPORTED = EnumSet.of(CourierType.FEDEX, CourierType.UPS, CourierType.CANADA_POST);

    private static final String CODE_FALSE = "0";
    private static final String CODE_TRUE = "1";

    private final CourierType courierType;
    private final Map<String, String> parameters = new HashMap<>();

    public RateScriptGenerator(final CourierType courierType) {
        validate(courierType);
        this.courierType = courierType;
        parameters.put("packagingType", courierType.getSelfPackagingCode());
    }

    public RateScriptGenerator withSourceAddress(AddressModel address) {
        parameters.put("shipAddr1", address.getAddress());
        parameters.put("shipCity", address.getCity());
        parameters.put("shipState", address.getProvince());
        parameters.put("shipCode", address.getPostal());
        parameters.put("shipCountry", address.getCountry());
        return this;
    }

    public RateScriptGenerator withDestinationAddress(AddressModel address) {
        parameters.put("toAddr1", address.getAddress());
        parameters.put("toCity", address.getCity());
        parameters.put("toState", address.getProvince());
        parameters.put("toCode", address.getPostal());
        parameters.put("toCountry", address.getCountry());
        if (address.getResidential()) {
            parameters.put("residentialAddressIndicator", CODE_TRUE);
        } else {
            parameters.put("residentialAddressIndicator", CODE_FALSE);
        }
        return this;
    }

    public RateScriptGenerator withDimensions(PackageDimensionModel dimension) {
        parameters.put("length", dimension.getLength().toString());
        parameters.put("width", dimension.getWidth().toString());
        parameters.put("height", dimension.getHeight().toString());
        parameters.put("weight", dimension.getWeight().toString());
        return this;
    }

    public String generate() {
        StringBuilder scriptBuilder = new StringBuilder("$rate = new \\RocketShipIt\\Rate('" + courierType + "');\n");
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            scriptBuilder.append("$rate->setParameter('" + entry.getKey() + "', '" + entry.getValue() + "');\n");
        }
        scriptBuilder.append("$response = $rate->getSimpleRates();\n");
        scriptBuilder.append("echo json_encode($response);");
        return scriptBuilder.toString();
    }

    public void validate(CourierType courierType) {
        if (!SUPPORTED.contains(courierType)) {
            throw new NotImplementedException("RocketShip tracking does not support courier: " + courierType);
        }
    }
}
