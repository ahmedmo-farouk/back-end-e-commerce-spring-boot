package back_end.e_commerce.service;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import jakarta.annotation.PostConstruct;
import org.mindrot.jbcrypt.BCrypt;



@Service
public class EncryptionService {
    @Value("${encryption.salt.rounds}")
    private int saltRounds;
    private String salt;
    @PostConstruct
    public void postConstruct(){
        salt = BCrypt.gensalt(saltRounds);
    }
    public String encryptPassword(String password){
        return BCrypt.hashpw(password, salt);
    }
    public boolean VerifyPassword(String password, String hashed){
        return BCrypt.checkpw(password, hashed);
    }

}