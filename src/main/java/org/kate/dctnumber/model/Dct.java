package org.kate.dctnumber.model;

import org.kate.dctnumber.Constants;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Dct {

	@Id
	@GeneratedValue(generator = Constants.ID_GENERATOR)
	private Long id;

	private Integer number;

	private Integer year;

	@ManyToOne(fetch = FetchType.LAZY)
	private Employee signatory;

	@ManyToOne(fetch = FetchType.LAZY)
	private Employee performer;

	@ManyToOne(fetch = FetchType.LAZY)
	private Employee addressee;

	public Long getId() {
		return id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Employee getSignatory() {
		return signatory;
	}
	public void setSignatory(Employee signatory) {
		this.signatory = signatory;
	}

	public Employee getPerformer() {
		return performer;
	}
	public void setPerformer(Employee performer) {
		this.performer = performer;
	}

	public Employee getAddressee() {
		return addressee;
	}
	public void setAddressee(Employee addressee) {
		this.addressee = addressee;
	}
};
