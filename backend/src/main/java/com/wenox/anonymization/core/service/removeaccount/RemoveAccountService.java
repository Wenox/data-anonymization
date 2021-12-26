package com.wenox.anonymization.core.service.removeaccount;

import com.wenox.anonymization.core.domain.UndoRemoveAccountToken;
import com.wenox.anonymization.core.domain.User;
import com.wenox.anonymization.core.domain.UserStatus;
import com.wenox.anonymization.core.dto.ApiResponse;
import com.wenox.anonymization.core.dto.RemoveMyAccountDto;
import com.wenox.anonymization.core.repository.UserRepository;
import com.wenox.anonymization.core.service.AuthService;
import com.wenox.anonymization.core.service.mail.MailDescription;
import com.wenox.anonymization.core.service.mail.MailService;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RemoveAccountService {

  private final PasswordEncoder passwordEncoder;
  private final AuthService authService;
  private final UserRepository userRepository;
  private final MailService mailService;
  private final UndoRemoveAccountTokenService undoRemoveAccountTokenService;

  public RemoveAccountService(PasswordEncoder passwordEncoder,
                              AuthService authService,
                              UserRepository userRepository,
                              MailService mailService,
                              UndoRemoveAccountTokenService undoRemoveAccountTokenService) {
    this.passwordEncoder = passwordEncoder;
    this.authService = authService;
    this.userRepository = userRepository;
    this.mailService = mailService;
    this.undoRemoveAccountTokenService = undoRemoveAccountTokenService;
  }

  @Transactional
  public ApiResponse removeMyAccount(RemoveMyAccountDto dto, Authentication auth) {
    User me = authService.getMe(auth);

    if (!passwordEncoder.matches(dto.getPassword(), me.getPassword())) {
      return ApiResponse.ofError("Invalid password entered.");
    }
    if (me.isMarkedForRemoval()) {
      return ApiResponse.ofError("Your account has already been marked for removal.");
    }
    if (me.getStatus() == UserStatus.REMOVED) {
      return ApiResponse.ofError("This account had been removed.");
    }

    UndoRemoveAccountToken token = undoRemoveAccountTokenService.generateTokenForUser(me);

    me.setMarkedForRemoval(true);
    me.setRemovalRequestedDate(LocalDateTime.now());
    userRepository.save(me);

    System.out.println("Restore your account until " + token.getExpirationDate() +
        " by following: http://localhost:3000/change-password?token=" + token.getToken());

    Executors.newSingleThreadExecutor()
        .execute(() -> mailService.sendMail(new MailDescription(me.getEmail(), "Your account was marked for removal",
            "Hello " + me.getFirstName() + ",\n\n" +
                "This e-mail is a confirmation that you have deleted your account.\n" +
                "We hope you will return to us in the future.\n" +
                "You can still cancel the removal of your account until " + token.getExpirationDate() + "." +
                "Follow the link to restore your account: http://localhost:3000/restore-account?token=" +
                token.getUuid().toString())));

    return ApiResponse.ofSuccess("Successfully marked the account for removal. Thanks for using the platform. Bye!");
  }

  public String restoreMyAccount(String token) {
    UndoRemoveAccountToken tokenEntity = undoRemoveAccountTokenService.findByToken(token);
    if (tokenEntity == null || !tokenEntity.getUser().isMarkedForRemoval()) {
      return "invalid-token";
    }
    if (tokenEntity.isExpired()) {
      return "token-expired";
    }
    User user = tokenEntity.getUser();
    if (user.isForceRemoval() || user.getStatus() == UserStatus.REMOVED) {
      return "already-removed";
    }
    user.setMarkedForRemoval(false);
    user.setRemovalRequestedDate(null);
    userRepository.save(user);
    return "success";
  }
}
