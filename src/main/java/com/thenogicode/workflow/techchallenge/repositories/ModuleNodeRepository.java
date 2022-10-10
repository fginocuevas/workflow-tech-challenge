package com.thenogicode.workflow.techchallenge.repositories;

import com.thenogicode.workflow.techchallenge.entities.ModuleNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModuleNodeRepository extends JpaRepository<ModuleNode, Long> {

    Optional<ModuleNode> findModuleNodeByModuleKey(String key);
}
