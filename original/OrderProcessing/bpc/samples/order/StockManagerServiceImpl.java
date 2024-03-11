/* 
"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use or for redistribution by customer, as part of such an application, in customer's own products."

Product 5655-FLW,  (C) COPYRIGHT International Business Machines Corp., 2005
All Rights Reserved * Licensed Materials - Property of IBM
*/
package bpc.samples.order;

import commonj.sdo.DataObject;

import com.ibm.websphere.sca.ServiceManager;

public class StockManagerServiceImpl {
	/**
	 * Default constructor.
	 */
	public StockManagerServiceImpl() {
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
	 * Method generated to support implemention of operation "checkAvailability" defined for WSDL port type 
	 * named "interface.StockManagerService".
	 * 
	 * The presence of commonj.sdo.DataObject as the return type and/or as a parameter 
	 * type conveys that its a complex type. Please refer to the WSDL Definition for more information 
	 * on the type of input, output and fault(s).
	 */
	public Boolean checkAvailability(DataObject productData) {
		
		System.out.println("checkAvailability(DataObject) - ENTRY.");
		
		String kind = "";
		int number = 0;
		
		if(productData != null)
		{
			System.out.println("productData != null");
			kind = productData.getString("kind");
			number = productData.getInt("number");
			System.out.println("requested number of kind "  + kind + ": " + number);
		}
		
		if(number > 0)
		{
			if(!"Pizza Funghi".equals(kind))
			{
				System.out.println("checkAvailability(DataObject) - EXIT. true");
				return Boolean.TRUE;
			}			
		}
		System.out.println("checkAvailability(DataObject) - EXIT. false");		
		return Boolean.FALSE;
	}

}