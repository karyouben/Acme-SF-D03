/*
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.entities.sponsorships;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.entities.project.Project;
import acme.roles.Sponsor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "id"), @Index(columnList = "sponsor_id"), @Index(columnList = "code"), @Index(columnList = "sponsor_id, published")
})
public class Sponsorship extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------------------------

	@Column(unique = true)
	@Pattern(regexp = "^[A-Z]{1,3}-[0-9]{3}$", message = "{validation.sponsorship.code}")
	@NotBlank
	private String				code;

	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	@NotNull
	private Date				moment;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				endDate;

	@NotNull
	private Money				amount;

	@NotNull
	private SponsorshipType		type;

	@Email
	private String				email;

	@URL
	@Length(max = 255)
	private String				link;

	private boolean				published;

	// Relationships  ------------------------------------------------------------
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Project				project;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Sponsor				sponsor;
}
