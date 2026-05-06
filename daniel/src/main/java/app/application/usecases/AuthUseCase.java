package app.application.usecases;

import app.domain.Exceptions.BusinessException;
import app.domain.models.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthUseCase {
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Autentica un usuario con documento y contraseña
     * @param document Documento del usuario
     * @param password Contraseña del usuario
     * @return Usuario autenticado
     */
    public User authenticate(String document, String password) throws BusinessException {
        // Esta lógica se debe implementar de acuerdo a tu infraestructura de persistencia
        User user = findByDocument(document);
        
        if (user == null) {
            throw new BusinessException("Usuario no encontrado");
        }
        
        if (!matchesPassword(password, user.getPassword())) {
            throw new BusinessException("Contraseña incorrecta");
        }
        
        return user;
    }
    
    /**
     * Obtiene un usuario por documento
     * @param document Documento del usuario
     * @return Usuario encontrado o null
     */
    public User findByDocument(String document) throws BusinessException {
        // Esta lógica se debe implementar consultando la base de datos
        // Por ahora se retorna null
        return null;
    }
    
    /**
     * Obtiene un usuario por ID
     * @param userId ID del usuario
     * @return Usuario encontrado o null
     */
    public User findById(Long userId) throws BusinessException {
        // Esta lógica se debe implementar consultando la base de datos
        // Por ahora se retorna null
        return null;
    }
    
    /**
     * Verifica si la contraseña coincide con el hash almacenado
     * @param rawPassword Contraseña sin encriptar
     * @param encodedPassword Contraseña encriptada
     * @return true si coincide, false de lo contrario
     */
    public boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
