package com.Ox08.experiments.migrated.esb;

import bpc.samples.order.PersonalData;
import bpc.samples.order.ProductData;
import bpc.samples.order.order.EnterPersonalData;
import bpc.samples.order.order.EnterProductData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.util.ClassUtils;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@SpringBootTest
class MigratedEsbApplicationTests {

	private final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

	@BeforeEach
	public void init() throws Exception {
		marshaller.setPackagesToScan(ClassUtils.getPackageName(EnterProductData.class));
		marshaller.afterPropertiesSet();
	}

	@Test
	public void testSendAndReceive() {



		/*try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/

		long orderId = new Random().nextInt();

		WebServiceTemplate ws = new WebServiceTemplate(marshaller);
		EnterProductData ed = new EnterProductData();
		ProductData pd=  new ProductData();
		pd.setCity("Moscow");
		pd.setStreet("Red Square 1");
		pd.setKind("222Pizza Funghi");
		pd.setSize("XS");
		pd.setOrderNo("ORDER-"+orderId);
		pd.setZipcode("34612");
		pd.setNumber(2);

		ed.setProductData(pd);

		assertThat(ws.marshalSendAndReceive("http://localhost:8080/ws/order", ed) != null);


		System.out.println("called 1");
		try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

		EnterPersonalData ep = new EnterPersonalData();
		PersonalData pd2 = new PersonalData();
		pd2.setBirthday("22/4/1942");
		pd2.setOrderNo("ORDER-"+orderId);
		pd2.setFamilyName("Chernyshev");
		pd2.setFirstName("Alex");
		ep.setPersonalData(pd2);
		assertThat(ws.marshalSendAndReceive("http://localhost:8080/ws/order", ep) != null);


		System.out.println("called 2");

		/*try {
            TimeUnit.MINUTES.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

}
