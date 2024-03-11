package com.Ox08.experiments.migrated.esb.order;
import bpc.samples.order.PersonalData;
import bpc.samples.order.ProductData;
import bpc.samples.shipping.ObjectFactory;
import bpc.samples.shipping.ShippingOrder;
import com.Ox08.experiments.migrated.esb.AbstractStep;
import com.Ox08.experiments.migrated.esb.ProcessContext;
import com.Ox08.experiments.migrated.esb.ProcessStep;
import com.Ox08.experiments.migrated.esb.shipping.PrepareAcknowledgement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class PrepareShippingOrder extends AbstractStep implements ProcessStep {
    /**
     * <bpws:assign name="PrepareShippingOrder" wpc:displayName="PrepareShippingOrder" wpc:id="29">
     * <bpws:targets>
     * <bpws:target linkName="Link9"/>
     * </bpws:targets>
     * <bpws:sources>
     * <bpws:source linkName="Link11"/>
     * </bpws:sources>
     * <bpws:copy>
     * <bpws:from variable="PersonalData">
     * <bpws:query queryLanguage="http://www.w3.org/TR/1999/REC-xpath-19991116"><![CDATA[/orderNo]]></bpws:query>
     * </bpws:from>
     * <bpws:to variable="ShippingOrder">
     * <bpws:query queryLanguage="http://www.w3.org/TR/1999/REC-xpath-19991116"><![CDATA[/referenceNo]]></bpws:query>
     * </bpws:to>
     * </bpws:copy>
     * <bpws:copy>
     * <bpws:from variable="PersonalData">
     * <bpws:query queryLanguage="http://www.w3.org/TR/1999/REC-xpath-19991116"><![CDATA[/familyName]]></bpws:query>
     * </bpws:from>
     * <bpws:to variable="ShippingOrder">
     * <bpws:query queryLanguage="http://www.w3.org/TR/1999/REC-xpath-19991116"><![CDATA[/familyName]]></bpws:query>
     * </bpws:to>
     * </bpws:copy>
     * <bpws:copy>
     * <bpws:from variable="PersonalData">
     * <bpws:query queryLanguage="http://www.w3.org/TR/1999/REC-xpath-19991116"><![CDATA[/firstName]]></bpws:query>
     * </bpws:from>
     * <bpws:to variable="ShippingOrder">
     * <bpws:query queryLanguage="http://www.w3.org/TR/1999/REC-xpath-19991116"><![CDATA[/firstName]]></bpws:query>
     * </bpws:to>
     * </bpws:copy>
     * <bpws:copy>
     * <bpws:from variable="ProductData">
     * <bpws:query queryLanguage="http://www.w3.org/TR/1999/REC-xpath-19991116"><![CDATA[/zipcode]]></bpws:query>
     * </bpws:from>
     * <bpws:to variable="ShippingOrder">
     * <bpws:query queryLanguage="http://www.w3.org/TR/1999/REC-xpath-19991116"><![CDATA[/zipCode]]></bpws:query>
     * </bpws:to>
     * </bpws:copy>
     * <bpws:copy>
     * <bpws:from variable="ProductData">
     * <bpws:query queryLanguage="http://www.w3.org/TR/1999/REC-xpath-19991116"><![CDATA[/city]]></bpws:query>
     * </bpws:from>
     * <bpws:to variable="ShippingOrder">
     * <bpws:query queryLanguage="http://www.w3.org/TR/1999/REC-xpath-19991116"><![CDATA[/city]]></bpws:query>
     * </bpws:to>
     * </bpws:copy>
     * <bpws:copy>
     * <bpws:from variable="ProductData">
     * <bpws:query queryLanguage="http://www.w3.org/TR/1999/REC-xpath-19991116"><![CDATA[/street]]></bpws:query>
     * </bpws:from>
     * <bpws:to variable="ShippingOrder">
     * <bpws:query queryLanguage="http://www.w3.org/TR/1999/REC-xpath-19991116"><![CDATA[/street]]></bpws:query>
     * </bpws:to>
     * </bpws:copy>
     * </bpws:assign>
     *
     * @param ctx
     */

    public void exec(ProcessContext ctx) {
        PersonalData personalData = ctx.get("PersonalData", PersonalData.class);
        ShippingOrder shippingOrder;
        if (ctx.contains("ShippingOrder")) {
            shippingOrder = ctx.get("ShippingOrder", ShippingOrder.class);
        } else {
            shippingOrder = new ObjectFactory().createShippingOrder();
        }
        shippingOrder.setReferenceNo(personalData.getOrderNo());
        shippingOrder.setFamilyName(personalData.getFamilyName());
        shippingOrder.setFirstName(personalData.getFirstName());
        ProductData productData = ctx.get("ProductData", ProductData.class);
        shippingOrder.setZipCode(productData.getZipcode());
        shippingOrder.setCity(productData.getCity());
        shippingOrder.setStreet(productData.getStreet());
        ctx.set("ShippingOrder", shippingOrder);
        nextStep(ctx);
    }
}
