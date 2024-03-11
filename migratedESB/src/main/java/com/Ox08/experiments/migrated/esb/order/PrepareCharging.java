package com.Ox08.experiments.migrated.esb.order;
import bpc.samples.charging.ChargingRequest;
import bpc.samples.charging.CreditCardInformation;
import bpc.samples.charging.ObjectFactory;
import bpc.samples.order.CheckCustomerResponse;
import bpc.samples.order.PersonalData;
import com.Ox08.experiments.migrated.esb.AbstractStep;
import com.Ox08.experiments.migrated.esb.ProcessContext;
import com.Ox08.experiments.migrated.esb.ProcessStep;
import com.Ox08.experiments.migrated.esb.charging.ChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class PrepareCharging extends AbstractStep implements ProcessStep {
    /**
     * <bpws:assign name="PrepareCharging" wpc:displayName="PrepareCharging" wpc:id="28">
     * <bpws:targets>
     * <bpws:target linkName="Link10"/>
     * </bpws:targets>
     * <bpws:sources>
     * <bpws:source linkName="Link16"/>
     * </bpws:sources>
     * <bpws:copy>
     * <bpws:from variable="PersonalData">
     * <bpws:query queryLanguage="http://www.w3.org/TR/1999/REC-xpath-19991116"><![CDATA[/familyName]]></bpws:query>
     * </bpws:from>
     * <bpws:to variable="ChargingRequest">
     * <bpws:query queryLanguage="http://www.w3.org/TR/1999/REC-xpath-19991116"><![CDATA[/customer]]></bpws:query>
     * </bpws:to>
     * </bpws:copy>
     * <bpws:copy>
     * <bpws:from variable="TotalAmount"/>
     * <bpws:to variable="ChargingRequest">
     * <bpws:query queryLanguage="http://www.w3.org/TR/1999/REC-xpath-19991116"><![CDATA[/amount]]></bpws:query>
     * </bpws:to>
     * </bpws:copy>
     * <bpws:copy>
     * <bpws:from variable="CheckCustomerResponse">
     * <bpws:query queryLanguage="http://www.w3.org/TR/1999/REC-xpath-19991116"><![CDATA[/creditCardNumber]]></bpws:query>
     * </bpws:from>
     * <bpws:to variable="ChargingRequest">
     * <bpws:query queryLanguage="http://www.w3.org/TR/1999/REC-xpath-19991116"><![CDATA[/creditCardInformation/cardNumber]]></bpws:query>
     * </bpws:to>
     * </bpws:copy>
     * <bpws:copy>
     * <bpws:from variable="CheckCustomerResponse">
     * <bpws:query queryLanguage="http://www.w3.org/TR/1999/REC-xpath-19991116"><![CDATA[/creditCardCompany]]></bpws:query>
     * </bpws:from>
     * <bpws:to variable="ChargingRequest">
     * <bpws:query queryLanguage="http://www.w3.org/TR/1999/REC-xpath-19991116"><![CDATA[/creditCardInformation/cardCompany]]></bpws:query>
     * </bpws:to>
     * </bpws:copy>
     * </bpws:assign>
     *
     * @param ctx
     */
    public void exec(ProcessContext ctx) {
        PersonalData personalData = ctx.get("PersonalData", PersonalData.class);
        ChargingRequest chargingRequest;
        if (ctx.contains("ChargingRequest")) {
            chargingRequest = ctx.get("ChargingRequest", ChargingRequest.class);
        } else {
            chargingRequest = new ObjectFactory().createChargingRequest();
        }
        Double totalAmount = ctx.get("TotalAmount", Double.class);
        chargingRequest.setCustomer(personalData.getFamilyName());
        chargingRequest.setAmount(totalAmount);
        CheckCustomerResponse checkCustomerResponse = ctx.get("CheckCustomerResponse", CheckCustomerResponse.class);
        if (chargingRequest.getCreditCardInformation() == null) {
            chargingRequest.setCreditCardInformation(new CreditCardInformation());
        }
        chargingRequest.getCreditCardInformation().setCardNumber(checkCustomerResponse.getCreditCardNumber());
        chargingRequest.getCreditCardInformation().setCardCompany(checkCustomerResponse.getCreditCardCompany());
        ctx.set("ChargingRequest", chargingRequest);
        nextStep(ctx);
    }
}
