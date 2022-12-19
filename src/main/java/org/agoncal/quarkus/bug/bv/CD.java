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
  @Size(min = 10, max = 5000)
  public String description;
  @Pattern(regexp = "[A-Z][a-z]+")
  public String musicCompany;
  @Max(value = 5)
  public Integer numberOfCDs;
  public Float totalDuration;

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

  public CD description(String description) {
    this.description = description;
    return this;
  }

  public CD musicCompany(String musicCompany) {
    this.musicCompany = musicCompany;
    return this;
  }

  public CD numberOfCDs(Integer numberOfCDs) {
    this.numberOfCDs = numberOfCDs;
    return this;
  }

  public CD totalDuration(Float totalDuration) {
    this.totalDuration = totalDuration;
    return this;
  }
}
