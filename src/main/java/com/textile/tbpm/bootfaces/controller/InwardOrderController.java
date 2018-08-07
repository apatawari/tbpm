package com.textile.tbpm.bootfaces.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.textile.tbpm.bootfaces.bean.OrderInwardEntryModel;
import com.textile.tbpm.bootfaces.model.OrderInwardEntry;
import com.textile.tbpm.bootfaces.persistence.OrderInwardRepository;

@RestController
@RequestMapping("/api")
public class InwardOrderController {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(InwardOrderController.class.getName());
	
	@Autowired
	private OrderInwardRepository orderInwardRepository;
	
	@Autowired
	OrderInwardEntry orderEntryInward;
	
	/**
	 * API to evaluate patient against rulesets. Key API of CDSHooks.
	 *
	 * @param request the request
	 * @param json the json
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/service/import/ordersInward", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	public ResponseEntity<String> importOrders(HttpServletRequest request,
	        @RequestBody List<OrderInwardEntryModel> readOrders) throws Exception {
		
		LOGGER.info("Reading orders...");

		for(int i=0;i<readOrders.size();i++) {
			
			OrderInwardEntryModel readOrder = readOrders.get(i);
			orderEntryInward = new OrderInwardEntry();
			
			orderEntryInward.setBaleQuantity(Integer.parseInt(readOrder.getBaleQuantity()));
			orderEntryInward.setBillNumber(Integer.parseInt(readOrder.getBillNumber()));
			orderEntryInward.setBillAmount(new Double(readOrder.getBillAmount()));
			
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			Date date = sdf.parse(readOrder.getBillDate());
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			
			orderEntryInward.setBillDate(cal);
			orderEntryInward.setLotNumber(Integer.parseInt(readOrder.getLotNumber()));	
			orderEntryInward.setLrNumber(Integer.parseInt(readOrder.getLrNumber()));
			orderEntryInward.setMeters(new Double(readOrder.getMeters()));
			orderEntryInward.setOrderNumber(Integer.parseInt(readOrder.getOrderNumber()));
			orderEntryInward.setPartyName(readOrder.getPartyName());
			orderEntryInward.setRate(new Double(readOrder.getRate()));
			orderEntryInward.setThanQuantity(Integer.parseInt(readOrder.getThanQuantity()));
			orderEntryInward.setId(Integer.parseInt(readOrder.getId()));
			String qualityMarka = readOrder.getQuality();
			String [] arrOfStr = qualityMarka.split(" ");
			orderEntryInward.setQuality(arrOfStr[1]);
			orderEntryInward.setMarka(arrOfStr[0]);
			
			orderInwardRepository.save(orderEntryInward);
			
		}
	
		return new ResponseEntity<>("Order reading complete", HttpStatus.OK);
	}
		
	

}
