package ru.piano.test.rooms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "doors")
@AllArgsConstructor
@NoArgsConstructor
public class Door implements Serializable {
    @Id
    private Long id;
}
