package com.Ox08.experiments.migrated.esb.order;
import bpc.samples.charging.charging.ChargeResponse;
import bpc.samples.order.CheckCustomerResponse;
import bpc.samples.shipping.ShippingAcknowledgement;
import bpc.samples.shipping.ShippingReport;
import com.Ox08.experiments.migrated.esb.AbstractStep;
import com.Ox08.experiments.migrated.esb.ProcessContext;
import com.Ox08.experiments.migrated.esb.ProcessStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
@Component
public class FileOrder extends AbstractStep implements ProcessStep {
    public void exec(ProcessContext ctx) {
        StringBuilder sb = new StringBuilder(256);
        sb.append("Order no.: ");
        sb.append(ctx.get("OrderNo", String.class));
        sb.append(".");
        if (!ctx.get("CheckCustomerResponse", CheckCustomerResponse.class).isIsCustomerOk()) {
            sb.append(" Customer check failed.");
        }
        if (!ctx.contains("IsProductAvailable")) {
            sb.append("Availability check failed.");
        }
        if (ctx.contains("ChargeResponse")) {
            sb.append("Charging response: ");
            sb.append(ctx.get("ChargeResponse", ChargeResponse.class).getChargingResponse());
            sb.append(".");
        }
        if (ctx.contains("ShippingAcknowledgement")) {
            sb.append("Shipping acknowledgement: ");
            sb.append(ctx.get("ShippingAcknowledgement", ShippingAcknowledgement.class).getMessage());
            sb.append(".");
        }
        if (ctx.contains("ShippingReport")) {
            sb.append("Shipping report: ");
            sb.append(ctx.get("ShippingReport", ShippingReport.class).getReport());
            sb.append(".");
        }
        LOGGER.info(sb.toString());
    }
}
