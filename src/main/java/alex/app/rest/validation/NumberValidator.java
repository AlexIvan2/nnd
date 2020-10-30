package alex.app.rest.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.function.Predicate;
import java.util.regex.Pattern;

@Component
@PropertySource("classpath:/numbervalidator.properties")
public class NumberValidator implements ConstraintValidator<ValidateNumber, String> {

    private static final Pattern numbersOnly = Pattern.compile("[^0-9]");


    @Value("${nnd.validation.error.not.provided}")
    private String notProvidedValidationMessage;
    @Value("${nnd.validation.error.must.start.with}")
    private String numberPrefixIsInvalidMessage;
    @Value("${nnd.validation.error.must.contain.only.digits}")
    private String onlyDigitsMessage;

    @Override
    public void initialize(ValidateNumber constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return isValueProvided(value, context) && isProperlyFormatted(value, context);
    }

    boolean isProperlyFormatted(String value, ConstraintValidatorContext context) {
        return isNumberStartsWithPlusOrDoubleZero(value, context) ||
                isNumberContainsOnlyDigits(value, context);
    }

    boolean isValueProvided(String value, ConstraintValidatorContext context) {
        Predicate<String> predicate = number -> !StringUtils.isEmpty(number) && !number.equals("undefined");
        return evaluate(value, predicate, notProvidedValidationMessage, context);
    }

    boolean isNumberStartsWithPlusOrDoubleZero(String value, ConstraintValidatorContext context) {
        Predicate<String> predicate = number -> StringUtils.startsWith(number, "00") || StringUtils.startsWith(number, "+");
        return evaluate(value, predicate, numberPrefixIsInvalidMessage, context);
    }

    boolean isNumberContainsOnlyDigits(String value, ConstraintValidatorContext context) {
        Predicate<String> predicate = number -> numbersOnly.matcher(number).matches();
        return evaluate(value, predicate, onlyDigitsMessage, context);
    }

    boolean evaluate(String number, Predicate<String> predicate, String message, ConstraintValidatorContext context) {
        if (predicate.test(number)) {
            return true;
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
    }
}
