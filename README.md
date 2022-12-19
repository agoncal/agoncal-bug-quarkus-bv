# Bean Validation bug in Quarkus 3.0.0.Alpha2 but not in 3.0.0.Alpha1

There is a strange behaviour in Quarkus 3.0.0.Alpha2 when using Bean Validation `ValidatorFactory` and injecting `Validator` in the same test, which succeeds using 3.0.0.Alpha1. 

I have a CD object with some constraints:

```java
import jakarta.validation.constraints.*;

public class CD {

  @NotNull @Size(min = 4, max = 50)
  public String title;
  @NotNull @Positive
  public Float price;
  @Max(value = 5)
  public Integer numberOfCDs;
```

And I have two tests that succeed if run separately, but fail if run together.
The first test builds a `ValidatorFactory` to validate the CD object (and closes the factory at the end of the test).
And the second test injects a `Validator` to validate the CD object.
When run together, the second test fails (`assertEquals(1, violations.size());` fails).

If both tests use the injected `validator` it succeeds.
If both tests use the `ValidatorFactory` it fails.

And no matter which combination of tests you use, the tests always succeed with `3.0.0.Alpha1`.

```java
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
```