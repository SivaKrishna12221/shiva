package ai.acintyo.ezykle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.acintyo.ezykle.bindings.UserAppointmentForm;
import ai.acintyo.ezykle.entities.EzServiceAppointment;
import ai.acintyo.ezykle.model.ApiResponse;
import ai.acintyo.ezykle.services.UserAppointmentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/ezykle-service")
@Slf4j
public class UserAppointmentController {

	@Autowired
	UserAppointmentService appointmentService;

	@PostMapping("/book-appointment")
	public ResponseEntity<ApiResponse<EzServiceAppointment>> bookAppointment(
			@RequestBody @Valid UserAppointmentForm appointmentForm) {
		log.info("ai.acintyo.ezykle.controller.UserAppointmentController::Attempting to book a service appointment");

		try {
			EzServiceAppointment result = appointmentService.bookAppointment(appointmentForm);
			log.info(
					"ai.acintyo.ezykle.controller.UserAppointmentController::Appointment booked successfully for user: {}",
					appointmentForm.getName()); // Logging user's name for context, ensure this does not breach privacy

			return ResponseEntity.ok(new ApiResponse<>(true, "{user.appointment.savedSuccessfully}", result));
		} catch (Exception e) {
			log.error("ai.acintyo.ezykle.controller.UserAppointmentController::Error booking appointment: {}",
					e.getMessage(), e);

			return new ResponseEntity<>(
					new ApiResponse<>(false, "{user.appointment.saveFailed}" + e.getMessage(), null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
