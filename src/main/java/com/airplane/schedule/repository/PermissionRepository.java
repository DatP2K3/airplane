package com.airplane.schedule.repository;

import com.airplane.schedule.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    Permission findByResourceIdAndScope(String resourceId, String scope);
}
