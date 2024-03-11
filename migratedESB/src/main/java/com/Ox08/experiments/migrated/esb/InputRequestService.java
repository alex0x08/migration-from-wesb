package com.Ox08.experiments.migrated.esb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
/**
 * This service processes collect input requests and wait till both parts (enterProductData & enterPersonalData) received,
 * then service fires processing
 */
@Service
public class InputRequestService {
    private static final int EXPIRATION_TIME = 1000 * 60 * 5; // input request will be expired after 5 minutes

    private static final Logger LOGGER = LoggerFactory.getLogger(InputRequestService.class);

    @Autowired
    private ProcessService ps;

    // in-memory request storage
    private final Map<String, InputRequest> requests = new HashMap<>();
    /**
     * Add part of request
     * @param orderNo
     *          order number
     * @param ctx
     */
    public void checkAdd(String orderNo, ProcessContext ctx) {
        final InputRequest r;
        if (requests.containsKey(orderNo)) {
            r = requests.get(orderNo);
            r.ctx.fillFrom(ctx);
        } else {
            r = new InputRequest(ctx);
            requests.put(orderNo, r);
        }
        LOGGER.info("add part for order: {}  info: {}" , orderNo, ctx.dumpInfo());
    }
    /**
     * Background processing, that fires actual process if both parts of request received
     */
    @Scheduled(initialDelay = 0, fixedDelay = 5000)
    void checkProcess() {
        if (requests.isEmpty()) {
            return;
        }
        LOGGER.info("found requests: {}" , requests.size());
        for (String orderNo : requests.keySet()) {
            InputRequest request = requests.get(orderNo);
            if (System.currentTimeMillis() - request.createdAt > EXPIRATION_TIME) {
                requests.remove(orderNo);
                LOGGER.info("order {} expired",orderNo);
                continue;
            }
            if (request.ctx.contains("PersonalData") && request.ctx.contains("ProductData")) {
                LOGGER.info("both personalData and productData has been added, start processing order {}",orderNo);
                ps.call("PreparePriceRequest",request.ctx);
                requests.remove(orderNo);
            }
        }
    }
    /**
     * DTO to store input request
     */
    static class InputRequest {
        private final ProcessContext ctx; // processing context, similar to BPM process environment
        private final long createdAt; // timestamp when created
        public InputRequest(ProcessContext ctx) {
            this.ctx = ctx;
            this.createdAt = System.currentTimeMillis();
        }
    }
}
