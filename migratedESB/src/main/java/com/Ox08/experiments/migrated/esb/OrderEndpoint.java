package com.Ox08.experiments.migrated.esb;
import bpc.samples.order.CheckCustomerResponse;
import bpc.samples.order.PersonalData;
import bpc.samples.order.ProductData;
import bpc.samples.order.order.EnterPersonalData;
import bpc.samples.order.order.EnterProductData;
import com.Ox08.experiments.migrated.esb.order.CustomerVerificationService;
import com.Ox08.experiments.migrated.esb.order.StockManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
@Endpoint
public class OrderEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEndpoint.class);

    @Autowired
    private StockManagerService sms;
    @Autowired
    private CustomerVerificationService cvs;

    @Autowired
    private ProcessService ps;

    @Autowired
    private InputRequestService rs;

    @PayloadRoot(
            namespace = "http://bpc/samples/order/Order",
            localPart = "enterPersonalData")
    @ResponsePayload
    public void enterPersonalData(@RequestPayload EnterPersonalData personalData) {
        LOGGER.info("Entering personal data");

        PersonalData pd = personalData.getPersonalData();
        ProcessContext ctx = new ProcessContext();
        ctx.set("OrderNo",pd.getOrderNo());
        ctx.set("PersonalData", pd);

        CheckCustomerResponse resp = cvs.checkCustomer(pd);
        /**
         *   <bpws:source linkName="Link3">
         *           <bpws:transitionCondition><![CDATA[return CheckCustomerResponse.getBoolean("isCustomerOk");]]></bpws:transitionCondition>
         *         </bpws:source>
         */
        if (resp.isIsCustomerOk()) {
            ctx.set("CheckCustomerResponse",resp);
            rs.checkAdd(pd.getOrderNo(),ctx);
        } else {
            ps.call("FileOrder",ctx);
        }
    }
    @PayloadRoot(
            namespace = "http://bpc/samples/order/Order",
            localPart = "enterProductData")
    @ResponsePayload
    public void enterProductData(@RequestPayload EnterProductData productData) {
        LOGGER.info("Entering product data");
        ProductData pd = productData.getProductData();
        ProcessContext ctx = new ProcessContext();
        ctx.set("OrderNo",pd.getOrderNo());
        ctx.set("ProductData", pd);
        /**
         *   <bpws:source linkName="Link6">
         *           <bpws:transitionCondition><![CDATA[return IsProductAvailable.booleanValue();
         * ]]></bpws:transitionCondition>
         */

        boolean isAvailable = sms.checkAvailability(pd);
        ctx.set("IsProductAvailable",isAvailable);
        if (isAvailable) {
            rs.checkAdd(pd.getOrderNo(),ctx);
        } else {
            ps.call("FileOrder",ctx);
        }
    }
}
