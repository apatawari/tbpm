package com.textile.tbpm.bootfaces.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import org.springframework.stereotype.Component;
import lombok.Data;

import java.util.Calendar;


// TODO: Auto-generated Javadoc
/**
 * The persistent class for the order_inward_entry database table.
 * 
 */
@Entity
@Component
@Data
@Table(name="order_inward_entry")
@NamedQuery(name="OrderInwardEntry.findAll", query="SELECT o FROM OrderInwardEntry o")
public class OrderInwardEntry implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	/** The bale quantity. */
	@Column(name="bale_quantity",nullable = false)
	@XmlElement
	private int baleQuantity;

	/** The bill amount. */
	@Column(name="bill_amount",nullable = false)
	@XmlElement
	private Double billAmount;

	/** The bill date. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="bill_date",nullable = false)
	@XmlElement
	private Calendar billDate;

	/** The bill number. */
	@Column(name="bill_number",nullable = false)
	@XmlElement
	private int billNumber;

	/** The lot number. */
	@Column(name="lot_number",nullable = false)
	@XmlElement
	private int lotNumber;

	/** The lr number. */
	@Column(name="lr_number",nullable = false)
	@XmlElement
	private int lrNumber;

	/** The meters. */
	private Double meters;

	/** The order number. */
	@Column(name="order_number",nullable = false)
	@XmlElement
	private int orderNumber;

	/** The party name. */
	@Column(name="party_name",nullable = false)
	@XmlElement
	private String partyName;

	/** The quality. */
	@Column(name="quality",nullable = false)
	@XmlElement
	private String quality;

	/** The marka. */
	@Column(name="marka",nullable = false)
	@XmlElement
	private String marka;

	
	/** The rate. */
	@Column(name="rate",nullable = false)
	@XmlElement
	private Double rate;

	/** The than quantity. */
	@Column(name="than_quantity",nullable = false)
	@XmlElement
	private int thanQuantity;
	
	

	/**
	 * Gets the marka.
	 *
	 * @return the marka
	 */
	public String getMarka() {
		return marka;
	}

	public void setMarka(String marka) {
		this.marka = marka;
	}

	/**
	 * Instantiates a new order inward entry.
	 */
	public OrderInwardEntry() {
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the bale quantity.
	 *
	 * @return the bale quantity
	 */
	public int getBaleQuantity() {
		return this.baleQuantity;
	}

	/**
	 * Sets the bale quantity.
	 *
	 * @param baleQuantity the new bale quantity
	 */
	public void setBaleQuantity(int baleQuantity) {
		this.baleQuantity = baleQuantity;
	}

	/**
	 * Gets the bill amount.
	 *
	 * @return the bill amount
	 */
	public Double getBillAmount() {
		return this.billAmount;
	}

	/**
	 * Sets the bill amount.
	 *
	 * @param billAmount the new bill amount
	 */
	public void setBillAmount(Double billAmount) {
		this.billAmount = billAmount;
	}

	/**
	 * Gets the bill date.
	 *
	 * @return the bill date
	 */
	public Calendar getBillDate() {
		return this.billDate;
	}

	/**
	 * Sets the bill date.
	 *
	 * @param billDate the new bill date
	 */
	public void setBillDate(Calendar billDate) {
		this.billDate = billDate;
	}

	/**
	 * Gets the bill number.
	 *
	 * @return the bill number
	 */
	public int getBillNumber() {
		return this.billNumber;
	}

	/**
	 * Sets the bill number.
	 *
	 * @param billNumber the new bill number
	 */
	public void setBillNumber(int billNumber) {
		this.billNumber = billNumber;
	}

	/**
	 * Gets the lot number.
	 *
	 * @return the lot number
	 */
	public int getLotNumber() {
		return this.lotNumber;
	}

	/**
	 * Sets the lot number.
	 *
	 * @param lotNumber the new lot number
	 */
	public void setLotNumber(int lotNumber) {
		this.lotNumber = lotNumber;
	}

	/**
	 * Gets the lr number.
	 *
	 * @return the lr number
	 */
	public int getLrNumber() {
		return this.lrNumber;
	}

	/**
	 * Sets the lr number.
	 *
	 * @param lrNumber the new lr number
	 */
	public void setLrNumber(int lrNumber) {
		this.lrNumber = lrNumber;
	}

	/**
	 * Gets the meters.
	 *
	 * @return the meters
	 */
	public Double getMeters() {
		return this.meters;
	}

	/**
	 * Sets the meters.
	 *
	 * @param meters the new meters
	 */
	public void setMeters(Double meters) {
		this.meters = meters;
	}

	/**
	 * Gets the order number.
	 *
	 * @return the order number
	 */
	public int getOrderNumber() {
		return this.orderNumber;
	}

	/**
	 * Sets the order number.
	 *
	 * @param orderNumber the new order number
	 */
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**
	 * Gets the party name.
	 *
	 * @return the party name
	 */
	public String getPartyName() {
		return this.partyName;
	}

	/**
	 * Sets the party name.
	 *
	 * @param partyName the new party name
	 */
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	/**
	 * Gets the quality.
	 *
	 * @return the quality
	 */
	public String getQuality() {
		return this.quality;
	}

	/**
	 * Sets the quality.
	 *
	 * @param quality the new quality
	 */
	public void setQuality(String quality) {
		this.quality = quality;
	}

	/**
	 * Gets the rate.
	 *
	 * @return the rate
	 */
	public Double getRate() {
		return this.rate;
	}

	/**
	 * Sets the rate.
	 *
	 * @param rate the new rate
	 */
	public void setRate(Double rate) {
		this.rate = rate;
	}

	/**
	 * Gets the than quantity.
	 *
	 * @return the than quantity
	 */
	public int getThanQuantity() {
		return this.thanQuantity;
	}

	/**
	 * Sets the than quantity.
	 *
	 * @param thanQuantity the new than quantity
	 */
	public void setThanQuantity(int thanQuantity) {
		this.thanQuantity = thanQuantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + baleQuantity;
		result = prime * result + ((billAmount == null) ? 0 : billAmount.hashCode());
		result = prime * result + ((billDate == null) ? 0 : billDate.hashCode());
		result = prime * result + billNumber;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + lotNumber;
		result = prime * result + lrNumber;
		result = prime * result + ((meters == null) ? 0 : meters.hashCode());
		result = prime * result + orderNumber;
		result = prime * result + ((partyName == null) ? 0 : partyName.hashCode());
		result = prime * result + ((quality == null) ? 0 : quality.hashCode());
		result = prime * result + ((rate == null) ? 0 : rate.hashCode());
		result = prime * result + thanQuantity;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderInwardEntry other = (OrderInwardEntry) obj;
		if (baleQuantity != other.baleQuantity)
			return false;
		if (billAmount == null) {
			if (other.billAmount != null)
				return false;
		} else if (!billAmount.equals(other.billAmount))
			return false;
		if (billDate == null) {
			if (other.billDate != null)
				return false;
		} else if (!billDate.equals(other.billDate))
			return false;
		if (billNumber != other.billNumber)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lotNumber != other.lotNumber)
			return false;
		if (lrNumber != other.lrNumber)
			return false;
		if (meters == null) {
			if (other.meters != null)
				return false;
		} else if (!meters.equals(other.meters))
			return false;
		if (orderNumber != other.orderNumber)
			return false;
		if (partyName == null) {
			if (other.partyName != null)
				return false;
		} else if (!partyName.equals(other.partyName))
			return false;
		if (quality == null) {
			if (other.quality != null)
				return false;
		} else if (!quality.equals(other.quality))
			return false;
		if (rate == null) {
			if (other.rate != null)
				return false;
		} else if (!rate.equals(other.rate))
			return false;
		if (thanQuantity != other.thanQuantity)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderInwardEntry [id=" + id + ", baleQuantity=" + baleQuantity + ", billAmount=" + billAmount
				+ ", billDate=" + billDate + ", billNumber=" + billNumber + ", lotNumber=" + lotNumber + ", lrNumber="
				+ lrNumber + ", meters=" + meters + ", orderNumber=" + orderNumber + ", partyName=" + partyName
				+ ", quality=" + quality + ", rate=" + rate + ", thanQuantity=" + thanQuantity + "]";
	}
	
	

}