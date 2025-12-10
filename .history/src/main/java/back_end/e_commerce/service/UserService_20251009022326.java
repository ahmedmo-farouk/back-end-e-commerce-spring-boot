package back_end.e_commerce.service;
@Service
public class UserService {
    private  LocalUserDAO localUserDAO;
    public UserService(LocalUserDAO localUserDAO) {
        this.localUserDAO = localUserDAO;
    }
    public LocalUser createUser(LocalUser user) {
        return localUserDAO.save(user);
    }

   
}