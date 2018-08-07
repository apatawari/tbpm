package com.textile.tbpm.bootfaces.controller;

import com.textile.tbpm.bootfaces.model.OrderEntry;
import com.textile.tbpm.bootfaces.persistence.OrderRepository;

import java.util.List;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(value = "session")
@Component(value = "orderController")
@ELBeanName(value = "orderController")
@Join(path = "/order", to = "/order/order-form.jsf")
public class OrderController {

    @Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderEntry orderEntry;
	
	@Autowired
	private List<OrderEntry> orderEntries;
	
	public void loadData() {
		orderEntries = orderRepository.findAll();
	}
	
	public int getLastVoucherNumber() {
		int n = orderRepository.findAll().size() - 1;
		return orderEntries.get(n).getVoucherNumber();
	}
	
	public String save() {
		orderRepository.save(orderEntry);
		orderEntry = new OrderEntry();
		return "/order/order-list.xhtml?faces-redirect=true";
	}

	public OrderEntry getOrderEntry() {
		return orderEntry;
	}
	
	
	public void readOrderData() {
		
	}
}
