package com.thenogicode.workflow.techchallenge.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "module")
public class ModuleNode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String moduleKey;
    @ManyToOne(cascade={ CascadeType.ALL})
    @JoinColumn(name="parent_id")
    private ModuleNode parent;

    @OneToMany(mappedBy="parent", cascade = CascadeType.ALL)
    private List<ModuleNode> children = new ArrayList<>();

}
