package com.potevio.bigdataboard.pojo;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "school")
@Data
public class MainBean {
    @Id
    private Integer id;
}
