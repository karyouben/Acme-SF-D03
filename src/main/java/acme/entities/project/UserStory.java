
package acme.entities.project;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.roles.Manager;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserStory extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			description;

	@Positive
	@Max(value = 1000000000)
	protected int				cost;

	@NotBlank
	@Length(max = 100)
	protected String			acceptanceCriteria;

	@NotNull
	protected Priority			priority;

	@URL
	@Length(max = 255)
	protected String			link;

	protected boolean			draftMode;

	// Relationships -------------------------------------------------------------

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	protected Manager			manager;

}
