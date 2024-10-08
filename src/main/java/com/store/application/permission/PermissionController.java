package com.store.application.permission;

import com.store.application.exceptions.PermissionNotFoundException;
import com.store.application.utils.LogMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/permissions")
@AllArgsConstructor
@Slf4j
public class PermissionController {
    private PermissionService permissionService;

    @Operation(summary = "Fetching all permissions", tags = { "permission", "get" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Fetched all permissions successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PermissionDTO.class))}
            )
    })
    @GetMapping
    public ResponseEntity<List<PermissionDTO>> getAllPermissions() {
        log.info(LogMessages.FETCH_ALL_PERMISSIONS + "{}");
        List<PermissionDTO> permissions = permissionService.getAllPermissions();
        return new ResponseEntity<>(permissions, HttpStatus.OK);
    }

    @Operation(summary = "Fetching permission with id", tags = { "permission", "get" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched permission successfully"),
            @ApiResponse(responseCode = "404", description = "Permission not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PermissionDTO> getPermissionById(@Parameter(description = "Permission id to get data for", required = true) @PathVariable UUID id) {
        log.info(LogMessages.FETCH_PERMISSION_BY_ID + "{}", id);
        PermissionDTO permission = permissionService.getPermissionById(id);
        return new ResponseEntity<>(permission, HttpStatus.OK);
    }

    @Operation(summary = "Creating new permission", tags = { "permission", "post" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created new permission successfully")
    })
    @PostMapping
    public ResponseEntity<PermissionDTO> createPermission(@Parameter(description = "Permission data to create", required = true) @RequestBody PermissionDTO permissionDTO) {
        log.info(LogMessages.CREATE_NEW_PERMISSION + "{}", permissionDTO.getName());
        PermissionDTO createdPermission = permissionService.createPermission(permissionDTO);
        return new ResponseEntity<>(createdPermission, HttpStatus.CREATED);
    }

    @Operation(summary = "Updating permission", tags = { "permission", "put" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated permission successfully"),
            @ApiResponse(responseCode = "404", description = "Permission not found")
    })
    @PutMapping
    public ResponseEntity<PermissionDTO> updatePermission(@Parameter(description = "Permission with updated data", required = true) @RequestBody PermissionDTO updatedPermissionDTO) {
        log.info(LogMessages.UPDATE_PERMISSION + "{}", updatedPermissionDTO.getId());
        PermissionDTO permission = permissionService.updatePermission(updatedPermissionDTO);
        log.info(LogMessages.UPDATED_PERMISSION + "{}", permission);
        return new ResponseEntity<>(permission, HttpStatus.OK);
    }

    @Operation(summary = "Deleting permission with id", tags = { "permission", "delete" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted permission successfully"),
            @ApiResponse(responseCode = "404", description = "Permission not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermission(@Parameter(description = "Permission id to delete", required = true) @PathVariable UUID id) {
        log.info(LogMessages.DELETE_PERMISSION + "{}", id);
        permissionService.deletePermission(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
