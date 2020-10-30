package alex.app.rest.validation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.validation.ConstraintValidatorContext;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NumberValidatorTest {

    @Mock
    ConstraintValidatorContext constraintValidatorContext;

    //@Spy
    NumberValidator validator = new NumberValidator();

    @Before
    public void setUp() throws Exception {
        ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        when(constraintValidatorContext.buildConstraintViolationWithTemplate(anyString())).thenReturn(constraintViolationBuilder);
        ReflectionTestUtils.setField(validator, "notProvidedValidationMessage", "Value must be provided");
        ReflectionTestUtils.setField(validator, "numberPrefixIsInvalidMessage", "The number must starts with \"+\" or 00");
        ReflectionTestUtils.setField(validator, "onlyDigitsMessage", "The number must contain only digits");
    }

    @Test
    public void testIsValueProvided() {
        assertTrue(validator.isValueProvided("Something", constraintValidatorContext));
    }

    @Test
    public void testIsValueNotProvided() {
        assertFalse(validator.isValid("", constraintValidatorContext));
    }

    @Test
    public void isNumberStartsWithPlusOrDoubleZero() {
        assertTrue(validator.isValid("003711234", constraintValidatorContext));
        assertTrue(validator.isValid("+3711234", constraintValidatorContext));
        assertFalse(validator.isValid("3711234", constraintValidatorContext));
    }

    @Test
    public void isNumberContainsOnlyDigits() {
        assertTrue(validator.isValid("+37112342", constraintValidatorContext));
        assertFalse(validator.isValid("37asd112sd34", constraintValidatorContext));
        assertFalse(validator.isValid("37%#@112%%!d34", constraintValidatorContext));
    }
}

