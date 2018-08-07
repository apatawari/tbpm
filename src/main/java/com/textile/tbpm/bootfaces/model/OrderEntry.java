package com.textile.tbpm.bootfaces.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;

import org.springframework.stereotype.Component;

import lombok.Data;


/**
 * The persistent class for the order_entry database table.
 * 
 */
@Entity
@Component
@Data
@Table(name="order_entry")
public class OrderEntry implements Serializable {
	private static final long serialVersionUID = 1L;

	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@Transient
	private int lastVoucherNumber;
	
	public int getLastVoucherNumber() {
		return lastVoucherNumber;
	}

	public void setLastVoucherNumber(int lastVoucherNumber) {
		this.lastVoucherNumber = lastVoucherNumber;
	}

	@Column(name="voucher_number", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int voucherNumber;

	public OrderEntry() {
	}
	
	
		
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public int getVoucherNumber() {
		return this.voucherNumber;
	}

	public void setVoucherNumber(int voucherNumber) {
		this.voucherNumber = voucherNumber;
	}
	
	
}