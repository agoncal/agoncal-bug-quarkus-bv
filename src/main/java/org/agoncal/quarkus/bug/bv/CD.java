package org.agoncal.quarkus.bug.bv;

import jakarta.validation.constraints.*;

/**
 * @author Antonio Goncalves
 * http://www.antoniogoncalves.org
 * --
 */
public class CD {

  @NotNull @Size(min = 4, max = 50)
  public String title;
  @NotNull @Positive
  public Float price;
  @Max(value = 5)
  public Integer numberOfCDs;

  // ======================================
  // =          Getters & Setters         =
  // ======================================

  public CD title(String title) {
    this.title = title;
    return this;
  }

  public CD price(Float price) {
    this.price = price;
    return this;
  }

  public CD numberOfCDs(Integer numberOfCDs) {
    this.numberOfCDs = numberOfCDs;
    return this;
  }
}
