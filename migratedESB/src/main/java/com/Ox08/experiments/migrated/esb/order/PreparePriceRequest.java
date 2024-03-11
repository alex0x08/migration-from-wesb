package com.Ox08.experiments.migrated.esb.order;
import bpc.samples.order.ProductData;
import bpc.samples.shipping.ObjectFactory;
import bpc.samples.shipping.PriceRequest;
import com.Ox08.experiments.migrated.esb.AbstractStep;
import com.Ox08.experiments.migrated.esb.ProcessContext;
import com.Ox08.experiments.migrated.esb.ProcessStep;
import org.springframework.stereotype.Component;
@Component
public class PreparePriceRequest extends AbstractStep implements ProcessStep {
    public void exec(ProcessContext ctx) {
        ProductData productData = ctx.get("ProductData", ProductData.class);
        int number = productData.getNumber();//.getInt("number");
        String size = "S";
        if (number > 16) size = "XXL";
        else if (number > 8) size = "XL";
        else if (number > 4) size = "L";
        else if (number > 2) size = "M";
        final PriceRequest pr;
        if (!ctx.contains("PriceRequest")) {
            pr = new ObjectFactory().createPriceRequest();
        } else {
            pr = ctx.get("PriceRequest", PriceRequest.class);
        }
        pr.setReferenceNo(productData.getOrderNo());
        pr.setKind(productData.getKind());
        pr.setSize(size);
        pr.setNumber(number);
        ctx.set("PriceRequest", pr);
        LOGGER.info("PreparePriceRequest. EXIT");
        nextStep(ctx);
    }
}


