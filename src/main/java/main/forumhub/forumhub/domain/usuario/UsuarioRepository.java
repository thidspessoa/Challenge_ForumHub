package main.forumhub.forumhub.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Usuario getReferenceByEmail(@Param("email") String email);



    boolean existsByEmail(String email);
}
