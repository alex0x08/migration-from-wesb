/* 
"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use or for redistribution by customer, as part of such an application, in customer's own products."

Product 5655-FLW,  (C) COPYRIGHT International Business Machines Corp., 2005
All Rights Reserved * Licensed Materials - Property of IBM
*/
package bpc.samples.order;

import commonj.sdo.DataObject;

import com.ibm.websphere.bo.BOFactory;
import com.ibm.websphere.sca.ServiceManager;

public class CustomerVerificationServiceImpl {
	/**
	 * Default constructor.
	 */
	public CustomerVerificationServiceImpl() {
		super();
	}

	/**
	 * Return a reference to the component service instance for this implementation
	 * class.  This should be used when passing this service to another reference api
	 * or if you want to invoke yourself asynchonously.
	 *
	 * @generated (com.ibm.wbit.java)
	 */
	private Object getMyService() {
		return (Object) ServiceManager.INSTANCE.locateService("self");
	}

	/**
	 * Method generated to support the implementing WSDL port type named "interface.CustomerVerificationService".
	 */
	public DataObject checkCustomer(DataObject personalData) {
		
		System.out.println("CustomerVerificationService.checkCustomer(DataObject) - ENTRY.");
		
	    BOFactory bofactory = 
	    	(BOFactory) ServiceManager.INSTANCE.locateService("com/ibm/websphere/bo/BOFactory");
		
		DataObject response = bofactory.create("http://bpc/samples/order","CheckCustomerResponse");
		boolean ok = false;
		String creditCardNumber = "";
		String creditCardCompany = "";
		
		if(personalData != null)
		{
			System.out.println("personalData != null");
			String familyName = personalData.getString("familyName");
			String firstName  = personalData.getString("firstName");
			String birthday   = personalData.getString("birthday");
			
			if(familyName != null && familyName != "" &&
			   firstName != null && firstName != "" &&
			   birthday != null && birthday != "")
			{
				System.out.println("input values != null and not empty");
				ok = true;
				
				long random = System.currentTimeMillis();
				int modulo = (int)random % 5;
				switch(modulo)
				{
			    case 0: creditCardCompany = "SuperCard"; break;
			    case 1: creditCardCompany = "MegaCard"; break;
			    case 2: creditCardCompany = "HomeCard"; break;
			    case 3: creditCardCompany = "GoldCard"; break;
			    case 4: creditCardCompany = "Cardissimo"; break;
				}
				creditCardNumber = Long.toString(random);
			}
		}

		response.setBoolean("isCustomerOk",ok);
		response.setString("creditCardNumber",creditCardNumber);
		response.setString("creditCardCompany",creditCardCompany);

		System.out.println("CustomerVerificationService.checkCustomer(DataObject) - Exit." + ok + " " + creditCardNumber + " " + creditCardCompany);

		return response;
	}

}