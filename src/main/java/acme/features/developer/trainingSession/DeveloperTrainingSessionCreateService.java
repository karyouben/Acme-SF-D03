
package acme.features.developer.trainingSession;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingModule.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionCreateService extends AbstractService<Developer, TrainingSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected DeveloperTrainingSessionRepository repository;

	// AbstractService<Authenticated, TrainingModule> ---------------------------


	@Override
	public void authorise() {
		final Principal principal = super.getRequest().getPrincipal();

		final boolean authorise = principal.hasRole(Developer.class);

		super.getResponse().setAuthorised(authorise);
	}

	@Override
	public void load() {

		final int trainingModuleId = super.getRequest().getData("trainingModuleId", int.class);
		TrainingModule trainingModule = this.repository.findTrainingModuleById(trainingModuleId);

		TrainingSession trainingSession = new TrainingSession();
		trainingSession.setTrainingModule(trainingModule);
		trainingSession.setDraftMode(true);

		super.getBuffer().addData(trainingSession);
	}

	@Override
	public void bind(final TrainingSession object) {
		assert object != null;

		super.bind(object, "code", "startPeriod", "endPeriod", "location", "instructor", "contactEmail", "link");
	}

	@Override
	public void validate(final TrainingSession object) {
		assert object != null;

		final String PERIOD_START = "startPeriod";
		final String PERIOD_END = "endPeriod";

		if (!super.getBuffer().getErrors().hasErrors(PERIOD_START) && !super.getBuffer().getErrors().hasErrors(PERIOD_END)) {
			final boolean startBeforeEnd = MomentHelper.isAfter(object.getEndPeriod(), object.getStartPeriod());
			super.state(startBeforeEnd, PERIOD_END, "developer.trainingSession.form.error.end-before-start");

			if (startBeforeEnd) {
				final boolean startOneWeekBeforeEndMinimum = MomentHelper.isLongEnough(object.getStartPeriod(), object.getEndPeriod(), 7, ChronoUnit.DAYS);

				super.state(startOneWeekBeforeEndMinimum, PERIOD_END, "developer.trainingSession.form.error.small-display-period");
			}
		}

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			final boolean duplicatedCode = this.repository.findAllTrainingSessions().stream().anyMatch(e -> e.getCode().equals(object.getCode()));

			super.state(!duplicatedCode, "code", "developer.trainingSession.form.error.duplicated-code");
		}
	}

	@Override
	public void perform(final TrainingSession object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "code", "startPeriod", "endPeriod", "location", "instructor", "contactEmail", "link", "draftMode");

		super.getResponse().addData(dataset);
	}

}
