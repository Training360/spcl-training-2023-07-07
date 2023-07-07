package training.basicsecuritydemo;

import lombok.Data;

@Data
public class CreateUserCommand {

    private String name;

    private String password;

    private String role;
}
