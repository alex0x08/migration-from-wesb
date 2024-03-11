package com.Ox08.experiments.migrated.esb.order;
import bpc.samples.order.CheckCustomerResponse;
import bpc.samples.order.ObjectFactory;
import bpc.samples.order.PersonalData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
@Service
public class CustomerVerificationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerVerificationService.class);

    /**
     * Method generated to support the implementing WSDL port type named "interface.CustomerVerificationService".
     */
    public CheckCustomerResponse checkCustomer(PersonalData personalData) {
        LOGGER.info("CustomerVerificationService.checkCustomer(DataObject) - ENTRY.");
        CheckCustomerResponse response = new ObjectFactory().createCheckCustomerResponse();
        boolean ok = false;
        String creditCardNumber = "";
        String creditCardCompany = "";
        if (personalData != null) {
            LOGGER.info("personalData != null");
            String familyName = personalData.getFamilyName();//.getString("familyName");
            String firstName = personalData.getFirstName(); //.getString("firstName");
            String birthday = personalData.getBirthday(); //.getString("birthday");
            if (familyName != null && !familyName.isEmpty() &&
                    firstName != null && !firstName.isEmpty() &&
                    birthday != null && !birthday.isEmpty()) {
                LOGGER.info("input values != null and not empty");
                ok = true;
                long random = System.currentTimeMillis();
                int modulo = (int) random % 5;
                creditCardCompany = switch (modulo) {
                    case 0 -> "SuperCard";
                    case 1 -> "MegaCard";
                    case 2 -> "HomeCard";
                    case 3 -> "GoldCard";
                    case 4 -> "Cardissimo";
                    default -> creditCardCompany;
                };
                creditCardNumber = Long.toString(random);
            }
        }
        response.setIsCustomerOk(ok); //.setBoolean("isCustomerOk",ok);
        response.setCreditCardNumber(creditCardNumber);
        response.setCreditCardCompany(creditCardCompany);
        LOGGER.info("CustomerVerificationService.checkCustomer(DataObject) - Exit. '{}' , '{}' , '{}'" , ok , creditCardNumber , creditCardCompany);
        return response;
    }
}
