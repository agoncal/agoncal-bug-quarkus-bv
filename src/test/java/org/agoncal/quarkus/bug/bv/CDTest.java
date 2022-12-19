package org.agoncal.quarkus.bug.bv;

import io.quarkus.test.junit.QuarkusTest;
import org.hibernate.validator.HibernateValidator;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Antonio Goncalves
 * http://www.antoniogoncalves.org
 * --
 */
@QuarkusTest
public class CDTest {

  @Inject
  Validator validator;

  @Test
  void shouldRaiseNoConstraintViolationWithDefault() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    CD cd = new CD().title("Kind of Blue").price(12.5f);

    Set<ConstraintViolation<CD>> violations = validator.validate(cd);
    assertEquals(0, violations.size());

    factory.close();
  }

  @Test
    //@Ignore("Make sure your local is EN, if not use the following JVM parameters : -Duser.language=en -Duser.country=EN")
  void shouldRaiseConstraintViolationValidatingNumberOfCDsProperty() {

    CD cd = new CD().numberOfCDs(7);

    Set<ConstraintViolation<CD>> violations = validator.validateProperty(cd, "numberOfCDs");

    assertEquals(1, violations.size());
    ConstraintViolation<CD> violation = violations.iterator().next();

    assertEquals("must be less than or equal to 5", violation.getMessage());
    assertEquals(7, violation.getInvalidValue());
    assertEquals("{jakarta.validation.constraints.Max.message}", violation.getMessageTemplate());
  }
}
