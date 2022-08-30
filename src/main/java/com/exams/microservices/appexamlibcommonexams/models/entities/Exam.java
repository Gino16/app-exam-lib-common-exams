package com.exams.microservices.appexamlibcommonexams.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "exams")
public class Exam {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(name = "create_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createAt;

  @JsonIgnoreProperties(value = {"exam"}, allowSetters = true)
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "exam")
  private List<Question> questions;

  public Exam() {
    this.questions = new ArrayList<>();
  }

  @PrePersist
  public void prePersist() {
    this.createAt = new Date();
  }

  public void setQuestions(List<Question> questions) {
    this.questions.clear();
    questions.forEach(this::addQuestion);
  }

  public void addQuestion(Question question) {
    this.questions.add(question);
    question.setExam(this);
  }

  public void removeQuestion(Question question) {
    this.questions.remove(question);
    question.setExam(null);
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Exam exam)) return false;
    return this.id != null && this.id.equals(exam.getId());
  }
}
