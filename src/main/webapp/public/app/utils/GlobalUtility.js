/**
 * Created by desmondz on 7/25/2015.
 */

function toTitleCase(stringValue) {
    var result = stringValue.replace(/([A-Z])/g, " $1");
    var finalResult = result.charAt(0).toUpperCase() + result.slice(1);
    return finalResult;
}

function deepClone(object) {
    return JSON.parse(JSON.stringify(object))
}