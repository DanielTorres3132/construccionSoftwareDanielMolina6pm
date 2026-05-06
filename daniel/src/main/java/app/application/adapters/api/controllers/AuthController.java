package app.application.adapters.api.controllers;

import app.application.adapters.api.request.LoginRequest;
import app.application.adapters.api.request.RefreshTokenRequest;
import app.application.adapters.api.response.AuthTokenResponse;
import app.application.adapters.api.response.UserAuthResponse;
import app.application.usecases.AuthUseCase;
import app.domain.Exceptions.BusinessException;
import app.domain.models.User.User;
import app.infrastructure.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {
    
    @Autowired
    private AuthUseCase authUseCase;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    /**
     * Endpoint para login de usuario
     * @param loginRequest Datos del login (documento y contraseña)
     * @return Token JWT y datos del usuario
     */
    @PostMapping("/login")
    public ResponseEntity<AuthTokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) throws BusinessException {
        // Autenticar usuario
        User user = authUseCase.authenticate(loginRequest.getDocument(), loginRequest.getPassword());
        
        // Generar token JWT
        String accessToken = jwtUtil.generateToken(
                user.getIdentificationId(), 
                user.getUsername(), 
                user.getRole().toString()
        );
        
        // Generar refresh token (puede ser un segundo token con mayor expiración)
        String refreshToken = jwtUtil.generateToken(
                user.getIdentificationId(), 
                user.getUsername(), 
                user.getRole().toString()
        );
        
        // Preparar respuesta
        UserAuthResponse userResponse = new UserAuthResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole().toString(),
                user.getIdentificationId()
        );
        
        AuthTokenResponse response = new AuthTokenResponse(
                accessToken,
                refreshToken,
                "Bearer",
                3600, // 1 hora en segundos
                userResponse
        );
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Endpoint para refrescar el token JWT
     * @param refreshTokenRequest Token de actualización
     * @return Nuevo token JWT
     */
    @PostMapping("/refresh")
    public ResponseEntity<AuthTokenResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) throws BusinessException {
        try {
            // Validar token
            if (!jwtUtil.isTokenValid(refreshTokenRequest.getRefreshToken())) {
                throw new BusinessException("Token de actualización inválido o expirado");
            }
            
            // Extraer información del token
            String username = jwtUtil.extractUsername(refreshTokenRequest.getRefreshToken());
            String document = jwtUtil.extractDocument(refreshTokenRequest.getRefreshToken());
            String role = jwtUtil.extractRole(refreshTokenRequest.getRefreshToken());
            
            // Obtener usuario
            User user = authUseCase.findByDocument(document);
            
            if (user == null) {
                throw new BusinessException("Usuario no encontrado");
            }
            
            // Generar nuevo token
            String newAccessToken = jwtUtil.generateToken(document, username, role);
            String newRefreshToken = jwtUtil.generateToken(document, username, role);
            
            // Preparar respuesta
            UserAuthResponse userResponse = new UserAuthResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getRole().toString(),
                    user.getIdentificationId()
            );
            
            AuthTokenResponse response = new AuthTokenResponse(
                    newAccessToken,
                    newRefreshToken,
                    "Bearer",
                    3600, // 1 hora en segundos
                    userResponse
            );
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            throw new BusinessException("Error al refrescar el token: " + e.getMessage());
        }
    }
    
    /**
     * Endpoint para validar token JWT
     * @param token Token a validar
     * @return Estado de validación
     */
    @PostMapping("/validate")
    public ResponseEntity<ValidateTokenResponse> validateToken(@RequestParam String token) {
        boolean isValid = jwtUtil.isTokenValid(token);
        
        ValidateTokenResponse response = new ValidateTokenResponse(
                isValid,
                isValid ? "Token válido" : "Token inválido o expirado"
        );
        
        return isValid 
                ? ResponseEntity.ok(response) 
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
    
    /**
     * DTO interno para respuesta de validación de token
     */
    public static class ValidateTokenResponse {
        private boolean valid;
        private String message;
        
        public ValidateTokenResponse(boolean valid, String message) {
            this.valid = valid;
            this.message = message;
        }
        
        public boolean isValid() {
            return valid;
        }
        
        public String getMessage() {
            return message;
        }
    }
}
