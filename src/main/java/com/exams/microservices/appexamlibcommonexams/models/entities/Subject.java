package com.exams.microservices.appexamlibcommonexams.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "subjects")
@Getter
@Setter
public class Subject {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @JsonIgnoreProperties(value = {"subject"})
  @ManyToOne(fetch = FetchType.LAZY)
  private Subject father;

  @JsonIgnoreProperties(value = {"subject"}, allowSetters = true)
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "father", cascade = CascadeType.ALL)
  private List<Subject> children;

  public Subject() {
    this.children = new ArrayList<>();
  }
}
