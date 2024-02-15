package team.a5.gachigayu.security.oauth2.account;

import java.io.Serializable;

public interface AccountAttributes extends Serializable {

    String id();

    String email();
}
