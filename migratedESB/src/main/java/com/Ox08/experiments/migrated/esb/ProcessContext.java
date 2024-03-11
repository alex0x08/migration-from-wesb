package com.Ox08.experiments.migrated.esb;
import java.util.HashMap;
import java.util.Map;
/**
 * This class is used as BPM process context storage, to keep properties changes between steps.
 *
 * Similar to BPM variables, defined in .bpel file:
 *
 *  <bpws:variables>
 *     <bpws:variable name="PersonalData" type="ns1:PersonalData" wpc:id="2"/>
 *     <bpws:variable name="ProductData" type="ns1:ProductData" wpc:id="3"/>
 *     <bpws:variable name="ShippingAcknowledgement" type="ns2:ShippingAcknowledgement" wpc:id="4"/>
 *     <bpws:variable name="ShippingReport" type="ns2:ShippingReport" wpc:id="5"/>
 *     <bpws:variable name="CheckCustomerResponse" type="ns1:CheckCustomerResponse" wpc:id="6"/>
 *     <bpws:variable name="IsProductAvailable" type="xsd:boolean" wpc:id="7"/>
 *     <bpws:variable name="PriceRequest" type="ns2:PriceRequest" wpc:id="8"/>
 *     <bpws:variable name="ShippingPrice" type="xsd:double" wpc:id="9"/>
 *     <bpws:variable name="TotalAmount" type="xsd:double" wpc:id="10"/>
 *     <bpws:variable name="ChargingRequest" type="ns3:ChargingRequest" wpc:id="11"/>
 *     <bpws:variable name="ChargingResponse" type="xsd:string" wpc:id="12"/>
 *     <bpws:variable name="ShippingOrder" type="ns2:ShippingOrder" wpc:id="13"/>
 *   </bpws:variables>
 *
 *
 */
public class ProcessContext {
    // in-memory storage
    private final Map<String, Object> context = new HashMap<>();
    /**
     * Set process property/variable
     *
     * @param name
     * @param value
     */
    public void set(String name, Object value) {
        this.context.put(name, value);
    }
    /**
     * Checks if property/variable is defined
     * @param name
     * @return
     */
    public boolean contains(String name) {
        return name != null && context.containsKey(name);
    }
    /**
     * Gets process variable with specified type
     * @param name
     * @param clazz
     * @return
     * @param <T>
     */
    public <T> T get(String name, Class<T> clazz) {
        return contains(name) ? clazz.cast(context.get(name)) : null;
    }
    /**
     * Dumps all variables names into string
     * @return
     */
    public String dumpInfo() {
        return String.join(",", context.keySet());
    }
    /**
     * Fills variables from another context
     * @param another
     */
    public void fillFrom(ProcessContext another) {
        this.context.putAll(another.context);
    }
}
