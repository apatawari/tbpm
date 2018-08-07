package com.textile.tbpm.bootfaces.controller;

import java.util.List;

import javax.faces.event.ValueChangeEvent;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.textile.tbpm.bootfaces.model.OrderInwardEntry;
import com.textile.tbpm.bootfaces.persistence.OrderInwardRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class ListOrdersController.
 */
@Scope (value = "session")
@Component (value = "listOrders")
@ELBeanName(value = "listOrders")
@Join(path = "/", to = "/order/order-list.jsf")
public class ListOrdersController {
	
	/** The order repository. */
	@Autowired
	private OrderInwardRepository orderInwardRepository;

	/** The order entries. */
	@Autowired
	private List<OrderInwardEntry> orderEntries;
	
	/** The search string. */
	String searchString = null;
	
	/** The search on. */
	String searchOn = "PartyName";
	
	
	
	/**
	 * Search by party name.
	 *
	 * @param keyword the keyword
	 */
	public void searchByPartyName(String keyword) {
		
		if(searchOn.equals("PartyName"))
			orderEntries = orderInwardRepository.findByPartyNameIgnoreCaseContaining(keyword);
		else if(searchOn.equals("Quality"))
			orderEntries = orderInwardRepository.findByQualityIgnoreCaseContaining(keyword);
		else
			orderEntries = orderInwardRepository.findByMarkaIgnoreCaseContaining(keyword);
	}
	
	
	/**
	 * Search string changed
	 *
	 * @param vce the vce
	 */
	public void searchStringChanged(ValueChangeEvent vce)
	{
		searchByPartyName((String) vce.getNewValue());
	}

	
	/**
	 * Gets the search string.
	 *
	 * @return the search string
	 */
	public String getSearchString() {
		return searchString;
	}

	/**
	 * Sets the search string.
	 *
	 * @param searchString the new search string
	 */
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	/**
	 * Gets the search on.
	 *
	 * @return the search on
	 */
	public String getSearchOn() {
		return searchOn;
	}

	/**
	 * Sets the search on.
	 *
	 * @param searchOn the new search on
	 */
	public void setSearchOn(String searchOn) {
		this.searchOn = searchOn;
	}

	/**
	 * Load data.
	 */
	@Deferred
	@RequestAction
	@IgnorePostback
	public void loadData() {
		orderEntries = orderInwardRepository.findAllByOrderByLotNumberAsc();
	}
	
	/**
	 * Gets the order inward entries.
	 *
	 * @return the order inward entries
	 */
	public List<OrderInwardEntry> getOrderInwardEntries() {
		return orderEntries;
	}

	/**
	 * Delete.
	 *
	 * @param orderInwardEntry the order inward entry
	 * @return the string
	 */
	public String delete(OrderInwardEntry orderInwardEntry) {
		orderInwardRepository.delete(orderInwardEntry.getId());
		loadData();
		return null;
	}
	
}
