
package acme.entities.invoices;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.entities.sponsorships.Sponsorship;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Invoice extends AbstractEntity {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotNull
	@Pattern(regexp = "IN-[0-9]{4}-[0-9]{4}")
	@Column(unique = true)
	protected String			code;

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				registrationTime;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				dueDate;

	@Valid
	@NotNull
	protected Money				quantity;

	@Valid
	@NotNull
	protected double			tax;

	@URL
	@Length(max = 255)
	protected String			link;

	protected boolean			draftMode;


	@Transient
	public Money totalAmount() {
		double total;
		total = this.quantity.getAmount() + this.tax / 100 * this.quantity.getAmount();
		Money totalAmount = new Money();
		totalAmount.setAmount(total);
		totalAmount.setCurrency(this.quantity.getCurrency());
		return totalAmount;
	}

	// Relationships ----------------------------------------------------------


	@ManyToOne(optional = false)
	@NotNull
	@Valid
	private Sponsorship sponsorship;
}
