package com.example.util;

import com.example.dto.JwtDTO;
import com.example.enums.ProfileRole;
import com.example.exp.AppMethodNotAllowedException;
import com.example.exp.UnAuthorizedException;
import jakarta.servlet.http.HttpServletRequest;

public class SecurityUtil {

    public static JwtDTO hasRole(HttpServletRequest request, ProfileRole... requiredRoles) {
        Integer id = (Integer) request.getAttribute("id");
        ProfileRole role = (ProfileRole) request.getAttribute("role");
        if (requiredRoles == null) return new JwtDTO(id, role);
        boolean found = false;
        for (ProfileRole r : requiredRoles) {
            if (r.equals(role)) {
                found = true;
                break;
            }
        }
        if (!found) throw new AppMethodNotAllowedException("Method not allowed");
        return new JwtDTO(id, role);
    }
}
