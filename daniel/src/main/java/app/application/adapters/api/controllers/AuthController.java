package app.application.adapters.api.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import app.application.adapters.api.request.LoginRequest;
import app.application.adapters.api.request.RefreshTokenRequest;
import app.application.adapters.api.response.AuthTokenResponse;
import app.application.adapters.api.response.UserAuthResponse;
import app.application.usecases.AuthUseCase;
import app.domain.Exceptions.BusinessException;
import app.domain.models.User.User;
import app.infrastructure.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    @Autowired
    private AuthUseCase authUseCase;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthController(AuthUseCase authUseCase, JwtUtil jwtUtil) {
        this.authUseCase = authUseCase;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthTokenResponse> login(@Valid @RequestBody LoginRequest request) throws BusinessException {
        User user = authUseCase.authenticate(request.getDocument(), request.getPassword());

        String accessToken = jwtUtil.generateToken(
                user.getIdentificationId(),
                user.getUserName(),
                user.getSystemRole().toString()
        );

        String refreshToken = jwtUtil.generateToken(
                user.getIdentificationId(),
                user.getUserName(),
                user.getSystemRole().toString()
        );

        AuthTokenResponse response = new AuthTokenResponse(
                accessToken,
                refreshToken,
                "Bearer",
                3600,
                toUserAuthResponse(user)
        );

        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<app.application.adapters.api.response.ErrorResponse> handleLoginMessageNotReadable(HttpMessageNotReadableException ex) {
        var error = new app.application.adapters.api.response.ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Cuerpo de la petición inválido o ausente. Los campos 'document' y 'password' son obligatorios"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<app.application.adapters.api.response.ErrorResponse> handleLoginMissingParam(MissingServletRequestParameterException ex) {
        var error = new app.application.adapters.api.response.ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Falta parámetro requerido: " + ex.getParameterName()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthTokenResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) throws BusinessException {
        if (!jwtUtil.isTokenValid(request.getRefreshToken()))
            throw new BusinessException("Token de actualización inválido o expirado");

        String identificationId = jwtUtil.extractDocument(request.getRefreshToken());
        String userName = jwtUtil.extractUsername(request.getRefreshToken());
        String role = jwtUtil.extractRole(request.getRefreshToken());

        User user = authUseCase.findByIdentificationId(identificationId);

        String newAccessToken = jwtUtil.generateToken(identificationId, userName, role);
        String newRefreshToken = jwtUtil.generateToken(identificationId, userName, role);

        AuthTokenResponse response = new AuthTokenResponse(
                newAccessToken,
                newRefreshToken,
                "Bearer",
                3600,
                toUserAuthResponse(user)
        );

        return ResponseEntity.ok(response);
    }

    // ─── Mapper ───────────────────────────────────────────────────────────────

    private static UserAuthResponse toUserAuthResponse(User user) {
        return new UserAuthResponse(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getFullName(),
                user.getSystemRole().toString(),
                user.getIdentificationId()
        );
    }
}