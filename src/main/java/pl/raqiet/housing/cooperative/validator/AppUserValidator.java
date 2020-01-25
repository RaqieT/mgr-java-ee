package pl.raqiet.housing.cooperative.validator;

import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.raqiet.housing.cooperative.domain.entity.AppUser;

@RequiredArgsConstructor
@Component
public class AppUserValidator implements Validator {
	private final UserDetailsService userDetailsService;

	EmailValidator emailValidator = EmailValidator.getInstance();

	@Override
	public boolean supports(Class clazz) {
		return AppUser.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "firstName", "error.empty.first.name");
		ValidationUtils.rejectIfEmpty(errors, "lastName", "error.empty.last.name");
		ValidationUtils.rejectIfEmpty(errors, "telephone", "error.empty.telephone");
		ValidationUtils.rejectIfEmpty(errors, "email", "error.empty.email");
		ValidationUtils.rejectIfEmpty(errors, "username", "error.empty.username");
		ValidationUtils.rejectIfEmpty(errors, "password", "error.empty.password");


		if (errors.getErrorCount() == 0) {
			if (StringUtils.hasText(((AppUser)arg0).getEmail()) && !emailValidator.isValid(((AppUser) arg0).getEmail())) {
				errors.rejectValue("email", "error.email.invalid");
			}
			if (((AppUser)arg0).getPassword().length() <= 5) {
				errors.rejectValue("password", "error.password.longer");
			}

			if (userDetailsService.loadUserByUsername(((AppUser)arg0).getUsername()) != null) {
				errors.rejectValue("username", "error.username.taken");
			}
		}


	}

}

