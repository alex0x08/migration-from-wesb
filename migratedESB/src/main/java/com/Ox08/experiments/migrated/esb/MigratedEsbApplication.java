package com.Ox08.experiments.migrated.esb;

import com.Ox08.experiments.migrated.esb.order.*;
import com.Ox08.experiments.migrated.esb.shipping.Calculate;
import com.Ox08.experiments.migrated.esb.shipping.PrepareAcknowledgement;
import com.Ox08.experiments.migrated.esb.shipping.PrepareReport;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import java.util.Collections;
import java.util.List;
/**
 * Sample integration app, based on Websphere ESB sample project
 */
@SpringBootApplication
public class MigratedEsbApplication {

	@Autowired
	private ProcessService ps;

	@Autowired
	private ApplicationContext applicationContext;

	@PostConstruct
	void onInit() {
		/*
		 * Here we statically define the 'process', similar to BPM, with exact steps.
		 */
		ps.addStep(applicationContext.getBean(FileOrder.class), false, Collections.emptyList());
		ps.addStep(applicationContext.getBean(PreparePriceRequest.class), false, List.of("Calculate"));
		ps.addStep(applicationContext.getBean(Calculate.class), false, List.of("CalculateTotalAmount"));
		ps.addStep(applicationContext.getBean(CalculateTotalAmount.class), true, List.of("PrepareCharging", "PrepareShippingOrder"));
		ps.addStep(applicationContext.getBean(PrepareCharging.class), false, List.of("ChargeService"));
		ps.addStep(applicationContext.getBean(PrepareShippingOrder.class), false, List.of("PrepareAcknowledgement"));
		ps.addStep(applicationContext.getBean(PrepareAcknowledgement.class), false, List.of("PrepareReport"));
		ps.addStep(applicationContext.getBean(PrepareReport.class), false, List.of("FileOrder"));
	}

	public static void main(String[] args) {
		SpringApplication.run(MigratedEsbApplication.class, args);
	}

}
