package com.Ox08.experiments.migrated.esb.charging;
import bpc.samples.charging.ChargingRequest;
import bpc.samples.charging.CreditCardInformation;
import com.Ox08.experiments.migrated.esb.AbstractStep;
import com.Ox08.experiments.migrated.esb.ProcessContext;
import com.Ox08.experiments.migrated.esb.ProcessStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
/**
 *  <bpws:invoke name="RequestCharging" operation="charge" partnerLink="ChargingService" portType="ns8:Charging" wpc:displayName="RequestCharging" wpc:id="20">
 *       <wpc:input>
 *         <wpc:parameter name="chargingRequest" variable="ChargingRequest"/>
 *       </wpc:input>
 *       <wpc:output>
 *         <wpc:parameter name="chargingResponse" variable="ChargingResponse"/>
 *       </wpc:output>
 *       <bpws:targets>
 *         <bpws:target linkName="Link16"/>
 *       </bpws:targets>
 *       <bpws:sources>
 *         <bpws:source linkName="Link15"/>
 *       </bpws:sources>
 *     </bpws:invoke>
 */
@Component
public class ChargeService extends AbstractStep implements ProcessStep {
    public void exec(ProcessContext ctx) {
        ChargingRequest chargingRequest = ctx.get("ChargingRequest", ChargingRequest.class);
        CreditCardInformation creditCardInformation = chargingRequest.getCreditCardInformation();//.getDataObject("creditCardInformation");
        String customer = chargingRequest.getCustomer();//.getString("customer");
        double amount = chargingRequest.getAmount();//.getDouble("amount");
        String cardNumber = creditCardInformation.getCardNumber();//.getString("cardNumber");
        String cardCompany = creditCardInformation.getCardCompany();//.getString("cardCompany");
        LOGGER.info("Charging {} from Customer '{}'. CardNumber: {} , Company: {}" , amount , customer , cardNumber, cardCompany);
        ctx.set("ChargingResponse", "ok");

        nextStep(ctx);
    }
}
