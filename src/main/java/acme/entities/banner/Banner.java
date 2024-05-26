
package acme.entities.banner;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Banner extends AbstractEntity {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@NotNull
	protected Date				instantiation;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date				startDisplayPeriod;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date				endDisplayPeriod;

	@NotBlank
	@Length(max = 75)
	protected String			slogan;

	@URL
	@NotBlank
	@Length(max = 255)
	protected String			linkPicture;

	@URL
	@NotBlank
	@Length(max = 255)
	protected String			linkWebDoc;
}
