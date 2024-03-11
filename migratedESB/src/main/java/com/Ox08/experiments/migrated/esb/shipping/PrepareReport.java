package com.Ox08.experiments.migrated.esb.shipping;
import bpc.samples.shipping.ObjectFactory;
import bpc.samples.shipping.ShippingOrder;
import bpc.samples.shipping.ShippingReport;
import com.Ox08.experiments.migrated.esb.AbstractStep;
import com.Ox08.experiments.migrated.esb.ProcessStep;
import com.Ox08.experiments.migrated.esb.order.FileOrder;
import com.Ox08.experiments.migrated.esb.ProcessContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class PrepareReport extends AbstractStep implements ProcessStep {

    public void exec(ProcessContext ctx) {

        ShippingOrder shippingOrder = ctx.get("ShippingOrder",ShippingOrder.class);
        String sb = "Shipment report. Reference No.: %s. Package to: %s %s, %s, %s, %s was delivered successfully."
                .formatted(shippingOrder.getReferenceNo(),
                        shippingOrder.getFirstName(),
                        shippingOrder.getFamilyName(),
                        shippingOrder.getZipCode(), shippingOrder.getCity(), shippingOrder.getStreet());

        ShippingReport shippingReport = new ObjectFactory().createShippingReport();

        //Create and prepare DataObject
        //DataObject shippingReport = factory.create("http://bpc/samples/shipping","ShippingReport");
        shippingReport.setReferenceNo(shippingOrder.getReferenceNo());
        shippingReport.setReport(sb);

        ctx.set("ShippingReport",shippingReport);

        nextStep(ctx);
    }
}
