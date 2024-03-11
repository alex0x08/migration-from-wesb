package com.Ox08.experiments.migrated.esb.shipping;
import bpc.samples.shipping.ObjectFactory;
import bpc.samples.shipping.ShippingAcknowledgement;
import bpc.samples.shipping.ShippingOrder;
import com.Ox08.experiments.migrated.esb.AbstractStep;
import com.Ox08.experiments.migrated.esb.ProcessContext;
import com.Ox08.experiments.migrated.esb.ProcessStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class PrepareAcknowledgement extends AbstractStep implements ProcessStep {

    public void exec(ProcessContext ctx) {
        String referenceNo = null;


        ShippingOrder shippingOrder;

        if(ctx.contains("ShippingOrder"))
        {
            shippingOrder = ctx.get("ShippingOrder",ShippingOrder.class);
            referenceNo = shippingOrder.getReferenceNo();
        } else {
            shippingOrder = new ObjectFactory().createShippingOrder();
          //  shippingOrder.setReferenceNo(referenceNo);
            ctx.set("ShippingOrder",shippingOrder);
        }
        String ack = "Shipment acknowledged. Reference No.: %s, price: %s.".formatted(referenceNo, ctx.get("Price", Double.class));

//initialize the Output variable:
       // BOFactory factory = (BOFactory)ServiceManager.INSTANCE.locateService("com/ibm/websphere/bo/BOFactory");
        ShippingAcknowledgement shippingAcknowledgement = new ObjectFactory().createShippingAcknowledgement();
        //= factory.createByType( getVariableType( "ShippingAcknowledgement") );

        shippingAcknowledgement.setMessage(ack);
        shippingAcknowledgement.setReferenceNo(referenceNo);

        ctx.set("ShippingAcknowledgement",shippingAcknowledgement);

//initialize wrapped DataObject
     //   DataObject shippingAck = factory.create("http://bpc/samples/shipping","ShippingAcknowledgement");
    //    shippingAck.setString("referenceNo",referenceNo);
     //   shippingAck.setString("message",ack.toString());

//set DataObject to Variable
      //  ShippingAcknowledgement.setDataObject("acknowledgement",shippingAck);
        nextStep(ctx);

    }

}
