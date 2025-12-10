package back_end.e_commerce.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import back_end.e_commerce.model.LocalUser;
import back_end.e_commerce.model.dao.LocalUserDAO;
import back_end.e_commerce.exception.UserAlreadyExistsException;

import back_end.e_commerce.api.model.RegistrationBody;

@Service
public class UserService {

    private final LocalUserDAO localUserDAO;
    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    public UserService(LocalUserDAO localUserDAO) {
        this.localUserDAO = localUserDAO;
    }

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException {
        boolean emailExists = localUserDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent();
        boolean usernameExists = localUserDAO.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent();

        if (emailExists || usernameExists) {
            throw new UserAlreadyExistsException("User already exists with this email or username");
        }

        LocalUser user = new LocalUser();
        user.setEmail(registrationBody.getEmail());
        user.setUsername(registrationBody.getUsername());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword())); 

        
        return localUserDAO.save(user);
    }

    public String loginUser(LoginBody loginBody) {
        // Implement login logic here
        return "Login functionality not yet implemented.";
    }
}
