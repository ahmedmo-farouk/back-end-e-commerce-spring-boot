package back_end.e_commerce.service;
import org.springframework.stereotype.Service;
import back_end.e_commerce.model.LocalUser;
import back_end.e_commerce.model.dao.LocalUserDAO;
import back_end.e_commerce.payload.RegistrationBody;
import back_end.e_commerce.exception.UserAlreadyExistsException;




@Service
public class UserService {
    private  LocalUserDAO localUserDAO;
    public UserService(LocalUserDAO localUserDAO) {
        this.localUserDAO = localUserDAO;
    }
    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException {
    if (localUserDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()
        || localUserDAO.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()) {
      throw new UserAlreadyExistsException();
    }
    LocalUser user = new LocalUser();
    user.setEmail(registrationBody.getEmail());
    user.setUsername(registrationBody.getUsername());
    user.setFirstName(registrationBody.getFirstName());
    user.setLastName(registrationBody.getLastName());
    user.setPassword(registrationBody.getPassword());
    return localUserDAO.save(user);
  }

   
}