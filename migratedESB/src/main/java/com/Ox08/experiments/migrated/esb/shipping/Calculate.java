package com.Ox08.experiments.migrated.esb.shipping;
import bpc.samples.shipping.PriceRequest;
import com.Ox08.experiments.migrated.esb.AbstractStep;
import com.Ox08.experiments.migrated.esb.ProcessStep;
import com.Ox08.experiments.migrated.esb.order.CalculateTotalAmount;
import com.Ox08.experiments.migrated.esb.ProcessContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * <bpws:invoke name="GetShippingPrice" operation="calculatePrice" partnerLink="ShippingService" portType="ns7:Shipping" wpc:displayName="GetShippingPrice" wpc:id="19">
 * <p>
 * <bpws:receive createInstance="yes" name="CalculatePrice" operation="calculatePrice" partnerLink="Shipping" portType="ns1:Shipping" variable="PriceRequest" wpc:displayName="calculatePrice" wpc:id="3">
 * <bpws:sources>
 * <bpws:source linkName="Link1"/>
 * </bpws:sources>
 */
@Component
public class Calculate extends AbstractStep implements ProcessStep {
    public void exec(ProcessContext ctx) {
        double price = 15.0;
        if (ctx.contains("PriceRequest")) {
            PriceRequest priceRequest = ctx.get("PriceRequest", PriceRequest.class);
            String size = priceRequest.getSize();
            if ("S".equals(size)) price = 2.0;
            else if ("M".equals(size)) price = 4.0;
            else if ("L".equals(size)) price = 6.0;
            else if ("XL".equals(size)) price = 8.0;
        }
//assign a value to the Output variable:
        ctx.set("ShippingPrice", price);
        ctx.set("Price", price);
        nextStep(ctx);
    }
}
