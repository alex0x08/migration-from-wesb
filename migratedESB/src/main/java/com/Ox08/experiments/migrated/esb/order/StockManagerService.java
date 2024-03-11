package com.Ox08.experiments.migrated.esb.order;
import bpc.samples.order.ProductData;
import com.Ox08.experiments.migrated.esb.charging.ChargeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
@Service
public class StockManagerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockManagerService.class);

    /**
     * Method generated to support implemention of operation "checkAvailability" defined for WSDL port type
     * named "interface.StockManagerService".
     *
     * The presence of commonj.sdo.DataObject as the return type and/or as a parameter
     * type conveys that its a complex type. Please refer to the WSDL Definition for more information
     * on the type of input, output and fault(s).
     */
    public Boolean checkAvailability(ProductData productData) {

        LOGGER.info("checkAvailability(DataObject) - ENTRY.");

        String kind = "";
        int number = 0;

        if(productData != null)
        {
            LOGGER.info("productData != null");
            kind = productData.getKind();//.getString("kind");
            number = productData.getNumber();//.getInt("number");
            LOGGER.info("requested number of kind {} : {}"  , kind , number);
        }

        if(number > 0)
        {
            if(!"Pizza Funghi".equals(kind))
            {
                LOGGER.info("checkAvailability(DataObject) - EXIT. true");
                return Boolean.TRUE;
            }
        }
        LOGGER.info("checkAvailability(DataObject) - EXIT. false");
        return Boolean.FALSE;
    }
}
