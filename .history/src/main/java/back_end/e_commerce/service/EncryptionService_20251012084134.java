package back_end.e_commerce.service;
import org.springframework.stereotype.Service;
@Service
public class EncryptionService {
    @Value("${encryption.salt.rounds}")
    private int saltRounds;
    
}