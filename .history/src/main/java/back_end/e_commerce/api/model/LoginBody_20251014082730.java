package back_end.e_commerce.api.model;

public class LoginBody {
    @NotNull
    @NotBlank
    private String username;
      @NotNull
    @NotBlank
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
  
} 