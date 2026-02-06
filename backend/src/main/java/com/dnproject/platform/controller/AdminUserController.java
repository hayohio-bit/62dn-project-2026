package com.dnproject.platform.controller;

import com.dnproject.platform.domain.constant.Role;
import com.dnproject.platform.dto.response.AdminUserResponse;
import com.dnproject.platform.dto.response.ApiResponse;
import com.dnproject.platform.dto.response.PageResponse;
import com.dnproject.platform.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<AdminUserResponse>>> getUsers(Pageable pageable) {
        Page<AdminUserResponse> response = adminUserService.getUsers(pageable);
        return ResponseEntity.ok(ApiResponse.success(PageResponse.from(response)));
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<ApiResponse<Void>> updateUserRole(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        Role role = Role.valueOf(body.get("role"));
        adminUserService.updateUserRole(id, role);
        return ResponseEntity.ok(ApiResponse.success("사용자 권한이 수정되었습니다.", null));
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<ApiResponse<Void>> updateUserRolePut(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        Role role = Role.valueOf(body.get("role"));
        adminUserService.updateUserRole(id, role);
        return ResponseEntity.ok(ApiResponse.success("사용자 권한이 수정되었습니다.", null));
    }

    @PatchMapping("/{id}/suspend")
    public ResponseEntity<ApiResponse<Void>> suspendUser(@PathVariable Long id) {
        adminUserService.suspendUser(id, true);
        return ResponseEntity.ok(ApiResponse.success("해당 계정이 정지되었습니다.", null));
    }

    @PatchMapping("/{id}/unsuspend")
    public ResponseEntity<ApiResponse<Void>> unsuspendUser(@PathVariable Long id) {
        adminUserService.suspendUser(id, false);
        return ResponseEntity.ok(ApiResponse.success("해당 계정의 정지가 해제되었습니다.", null));
    }
}
