package back_end.e_commerce.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import back_end.e_commerce.model.LocalUser;
import back_end.e_commerce.model.dao.LocalUserDAO;
import back_end.e_commerce.exception.UserAlreadyExistsException;

import back_end.e_commerce.api.controller.AuthenticationController.RegistrationBody;
@Service
public class UserService {

    private final LocalUserDAO localUserDAO;

    @Autowired
    public UserService(LocalUserDAO localUserDAO) {
        this.localUserDAO = localUserDAO;
    }

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException {
        // ✅ تحقق من وجود مستخدم بنفس البريد أو اسم المستخدم
        boolean emailExists = localUserDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent();
        boolean usernameExists = localUserDAO.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent();

        if (emailExists || usernameExists) {
            throw new UserAlreadyExistsException("User already exists with this email or username");
        }

        // ✅ إنشاء مستخدم جديد
        LocalUser user = new LocalUser();
        user.setEmail(registrationBody.getEmail());
        user.setUsername(registrationBody.getUsername());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        user.setPassword(registrationBody.getPassword()); // لاحقًا لازم تعمل **تشفير** للباسورد!

        // ✅ حفظ المستخدم في قاعدة البيانات
        return localUserDAO.save(user);
    }
}
