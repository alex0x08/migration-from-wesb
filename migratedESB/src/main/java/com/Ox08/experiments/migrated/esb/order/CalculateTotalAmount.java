package com.Ox08.experiments.migrated.esb.order;
import bpc.samples.order.ProductData;
import com.Ox08.experiments.migrated.esb.AbstractStep;
import com.Ox08.experiments.migrated.esb.ProcessContext;
import com.Ox08.experiments.migrated.esb.ProcessStep;
import org.springframework.stereotype.Component;
@Component
public class CalculateTotalAmount extends AbstractStep implements ProcessStep {
    public void exec(ProcessContext ctx) {
        ProductData productData = ctx.get("ProductData", ProductData.class);
        String kind = productData.getKind();
        int number = productData.getNumber();
        double price = 25.0;
        if ("mercedes".equals(kind)) price = 35000.0;
        else if ("porsche".equals(kind)) price = 55000.0;
        Double shippingPrice = ctx.get("ShippingPrice", Double.class);
        Double totalAmount = shippingPrice + price * number;
        ctx.set("TotalAmount", totalAmount);
        LOGGER.info("CalculateTotalAmount: {}" , totalAmount);
        nextStep(ctx);
    }
}
