/**
 * Created by desmondz on 7/31/2015.
 */

function DeferralEditorAjaxClient() {
    // https://w.amazon.com/index.php/Flash/SUIT/AceAndCarve/UIModel#Load_and_track_Deferral_State
    this.loadDeferral = function (workRequestName, deferralCountryCode, deferralName) {
        return {
            "product_name": "Prime",
            "country_code": "US",
            "elements": [
                {
                    "name": "Shipping",
                    "curve": "Product Delivery"
                }
                // more elements
            ],
            "reporting_attributes": [
                "Reporting 1"
                // more reporting_attributes
            ],
            "deferral_activities": {
                "==deferral_activity_name==": {
                    "name": "==name==",
                    "business_namespace": "==selected in the mapping usecase modal==",
                    "business_country_code": "==selected==",
                    "business_activity": "==selected==",

                    "element_mappings": {
                        "==element_name==": {
                            "name": "==name==",
                            "financial_component_group": "==value from selected==",
                            "financial_component": "==value from selected==",
                            "==variant name==": "==variant value selected=="
                            //more variants as needed
                        }
                        //more element mappings
                    },

                    // If the deferral activity have a reversal
                    "reversal": {
                        "name": "==name==",
                        "business_namespace": "==selected in the mapping usecase modal==",
                        "business_country_code": "==selected==",
                        "business_activity": "==selected==",

                        "element_mappings": {
                            "==element_name==": {
                                "name": "==name==",
                                "financial_component_group": "==value from selected==",
                                "financial_component": "==value from selected==",
                                "==variant name==": "==variant value selected==",
                                //more variants as needed
                            },
                            //more element mappings
                        }
                    }
                }
                // more deferral activities
            },

            "lifecycle_activity": {
                "==lifecycle_activity_name==": {
                    "name": "==name==",
                    "business_namespace": "==specified in the lifecycle activity modal==",
                    "business_country_code": "==specified==",
                    "business_activity": "==specified==",
                    "financial_component_group": "==specified==",
                    "financial_component": "==specified==",
                    "==variant name specifed==": "==variant value specified=="
                    //more variants as needed
                }
            }
        };
    };


// basic deferral options
// https://w.amazon.com/index.php/Flash/SUIT/AceAndCarve/UIModel#Deferral_Submission_Request_Data
    this.createDeferral = function (workRequestName) {
        return this.loadDeferral();
    };

// https://w.amazon.com/index.php/Flash/SUIT/AceAndCarve/UIModel#Deferral_Submission_Request_Data
    this.saveDeferral = function (workRequestName, deferralCountryCode, deferralName, requestData) {
        return {
            "status": "200",
            "message": "saved deferral successfully"
        };
    }

// loading of combined definition and enumeration data for Deferrals
// https://w.amazon.com/index.php/Flash/SUIT/AceAndCarve/UIModel#Initial_load_fetch_rendering_enumerations
    this.loadEnumerations = function (featureType) {
        var enumerations = {
            "product_names": ["Prime", "Fresh", "eBooks", "ProductFoo"],
            "country_codes": ["US", "EU"],
            "country_code_details": {
                "US": {
                    "timezone": "PST",
                    "currency_code": "US Dollars"
                },
                "EU": {
                    "timezone": "UTC",
                    "currency_code": "Euros"
                }
            },

            "elements": ["Shipping", "Video"],
            "curves": ["Product Delivery", "Even"],
            "reporting_attributes": ["Reporting 1", "Reporting 2"],

            "deferral_activity_definitions": {
                "Creation": {
                    "source": "input",
                    "impact": "billing",
                    "requirement": "required",
                    "reversibility": "non_reversible"
                    // only be present for a reversible act
                    //"alias": "==if no alias, use deferral_activity_name==",
                    //"reversal_alias": "==the alias for th reversal activity, if no alias use name + _reversal keyword==",
                    //"balance_change_direction": "==increase or decrease==",
                    //"reversal_balance_change_direction": "==increase or decrease=="
                }
                // more deferral activity element
            }
        };

        if (featureType == "DEFERRAL") {
            return enumerations;
        } else return enumerations;
    };

// loading of mapping attribute enumerations, the possible values for the 5 core and enum for variantNames
// https://w.amazon.com/index.php/Flash/SUIT/AceAndCarve/UIModel#AJAX_fetch_mapping_enumerations_after_initial_load
    this.loadMappingAttributeEnumerations = function () {
        return null
    };


// https://w.amazon.com/index.php/Flash/SUIT/AceAndCarve/UIModel#AJAX_fetch_dropdown_value_for_variant
    this.loadVariantEnumerations = function (variantName) {
        return null
    };

// search of mappings (5core + variant ordered as a tree) for given use cases (3core)
// https://w.amazon.com/index.php/Flash/SUIT/AceAndCarve/UIModel#AJAX_fetch_mappings_after_selected_mapping
    this.searchMappingsForDeferalActivities = function (loadMappingsForDeferralActivitiesRequest) {
        var loadMappingsForDeferralActivitesResponse;
        return loadMappingsForDeferralActivitesResponse;
    };

// search of use cases (3core) from a search criteria, used in mapping library browser
// TODO
    this.searchUseCasesForCritera = function (searchCriteria) {
        var mappingsList;
        return mappingsList;
    };
}